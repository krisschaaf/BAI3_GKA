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

    private static List<Node> findEulerCircle(Node startNode){
        // Der zu untersuchende Knoten ist zu Beginn der Startknoten
        Node currentNode = startNode;
        // Initialisiere List, in die die Knoten des Kreises geschrieben werden
        List<Node> circleNodes = new ArrayList<Node>();
        visitedEdges = new ArrayList<Edge>();
        // Führe folgenden Block so lange aus, bis der Kreis geschlossen ist
        do {
            circleNodes.add(currentNode);
            // Ist diese schon Teil des bisherigen Kreises, suche nächste Kante
            List<Edge> edges = currentNode.edges().collect(Collectors.toList());
            Edge nextEdge = edges.get(0);
            for (int i = 1; visitedEdges.contains(nextEdge); i++) {
                nextEdge = edges.get(i);
            }
            // Füge gefundene Kante dem Kreis hinzu
            visitedEdges.add(nextEdge);
            // Betrachte nächsten Knoten
            currentNode = nextEdge.getOpposite(currentNode);
        } while(!startNode.getId().equals(currentNode.getId()));
        return circleNodes;
    }

    public static List<Graph> findEulerGraphs(Graph graph) {

        List<Graph> result = new LinkedList<Graph>();
        List<Edge> cachedEdges = new LinkedList<Edge>();
        List<Node> circleNodes = new ArrayList<Node>();
        // Führe folgenden Block solange aus, bis keine Kanten mehr im Graph vorhanden sind
        while(graph.getEdgeCount() > 0) {
            Node startNode;

            if(circleNodes.isEmpty()){
                // Beginne mit dem ersten Knoten im Graphen als Startknoten
                startNode = graph.getNode(0);
            } else {
                // Sonst nehme den ersten Knoten des zuletzt gebauten Eulerkreises, der noch Nachbarn hat
                startNode = circleNodes.stream().filter(node -> node.getDegree() > 0).findAny().orElse(null);
            }
            // Suche nächsten Eulerkreis
            circleNodes.addAll(findEulerCircle(startNode));
            // Füge den Eulerkreis der Ergebnisliste als Graph hinzu
            result.add(getGraphFromEdges(visitedEdges));

            for (Edge e: visitedEdges
            ) {
                try{
                    // Cache und Entferne die Kanten des Kreises aus dem Graphen
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

    private static boolean isValidEulerGraph(Graph g){
        for(int i = 0; i < g.getNodeCount(); i++){
            if(!g.getNode(i).edges().findAny().isPresent() || (g.getNode(i).getDegree()%2) != 0){
                return false;
            }
        }
        return true;
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
