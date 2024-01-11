package haw.gka;

import org.graphstream.graph.Edge;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hierholzer {
    private static List<Edge> visitedEdges;
    private static List<Graph> result;
    private static List<Node> findEulerCircle(Node startNode){
        // Der zu untersuchende Knoten ist zu Beginn der Startknoten
        Node currentNode = startNode;
        // Initialisiere List, in die die Knoten des Kreises geschrieben werden
        List<Node> circleNodes = new LinkedList<Node>();
        visitedEdges = new LinkedList<Edge>();
        // Führe folgenden Block so lange aus, bis der Kreis geschlossen ist
        do {
            circleNodes.add(currentNode);
            // Suche nächste Kante
            Edge nextEdge = null;
            // Ist diese schon Teil des bisherigen Kreises, suche nächste Kante
            for (int i = 0; i == 0 || visitedEdges.contains(nextEdge); i++) {
                nextEdge = getNthEdge(currentNode, i);
                if (nextEdge == null) {
                    throw new RuntimeException("Node " + currentNode.getId() + " has no more edges!");
                }
            }

            // Füge gefundene Kante dem Kreis hinzu
            visitedEdges.add(nextEdge);
            // Betrachte nächsten Knoten
            currentNode = nextEdge.getOpposite(currentNode);

        } while(!startNode.equals(currentNode));

        return circleNodes;
    }

    public static List<Graph> findEulerGraphs(Graph graph) {

        // Ueberpruefe ob jeder Knoten eine gerade Anzahl an Kanten besitzt
        if(!isValidEulerGraph(graph)){
            throw new RuntimeException("Graph is not valid!");
        }
        result  = new ArrayList<Graph>();
        List<Edge> cachedEdges = new LinkedList<Edge>();
        List<Node> circleNodes = new LinkedList<Node>();
        // Führe folgenden Block solange aus, bis keine Kanten mehr im Graph vorhanden sind
        while(graph.getEdgeCount() > 0) {
            Node startNode = null;
            if(circleNodes.isEmpty()){
                // Beginne mit dem ersten Knoten im Graphen als Startknoten
                startNode = graph.getNode(0);
            } else {
                // Sonst nehme den ersten Knoten des zuletzt gebauten Eulerkreises, der noch Nachbarn hat
                for(Node n : circleNodes){
                    if(n.getDegree() > 0){
                        startNode = n;
                        break;
                    }
                }
                if (startNode == null){
                    throw new RuntimeException("Somethings wrong, edges left but " +
                            "no further nodes with neighbours found!");
                }
            }
            // Suche nächsten Eulerkreis
            circleNodes.addAll(findEulerCircle(startNode));
            // Füge den Eulerkreis der Ergebnisliste als Graph hinzu
            result.add(getGraphFromEdges(visitedEdges));

            for (Edge e: visitedEdges
            ) {
                try{
                    //Cache und Entferne die Kanten des Kreises aus dem Graphen
                    cachedEdges.add(e);
                    graph.removeEdge(e.getId());
                } catch (ElementNotFoundException enf){
                    System.out.println("Something is wrong, " + e.getId() + " already removed.");
                }
            }
        }
        // Zur weiteren Verwendung des übergeben Graphens, füge die gecacheten Kanten dem Graph wieder hinzu
        for (Edge e : cachedEdges){
            graph.addEdge(e.getId(), e.getSourceNode(), e.getTargetNode());
        }

        return result;
    }

    private static Edge getNthEdge(Node node,int n){
        try{
            return (Edge)node.edges().toArray()[n];
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    private static boolean isValidEulerGraph(Graph g){
        for(int i = 0; i < g.getNodeCount(); i++){
            if(!g.getNode(i).edges().findAny().isPresent() || (g.getNode(i).getDegree()%2) != 0){
                return false;
            }
        }
        return true;
    }

    private static List<Node> getNeighbours(Node n){
        List<Node> localresult = new LinkedList<Node>();
        Collections.addAll(localresult, n.neighborNodes().toArray(Node[]::new));
        return localresult;
    }

    private static int getNeighbourSize(Node n){
        int counter = 0;
        for (Edge e : n.edges().toArray(Edge[]::new)){
            counter += 1;
        }
        return counter;
    }

    private static Graph getGraphFromEdges(List<Edge> edges){
        // Erstelle Teilgraph
        Graph newGraph = new MultiGraph("Teilgraph_"+edges.get(0).getId());
        newGraph.setAutoCreate(true);
        newGraph.setStrict(false);

        for (Edge e: edges
        ) {
            // Füge Kanten des Kreises dem leeren Teilgraph hinzu
            newGraph.addEdge(e.getId(),e.getSourceNode().getId(), e.getTargetNode().getId());
            newGraph.getEdge(e.getId()).setAttribute("ui.label", "marked");
        }
        return newGraph;
    }

    private static List<Node> getDublicates(List<Node> list) {
        Set<Node> elements = new HashSet<Node>();
        return list.stream()
                .filter(n -> !elements.add(n))
                .collect(Collectors.toList());
    }
}
