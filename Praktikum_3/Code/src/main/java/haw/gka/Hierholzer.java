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
    // Wegen der hohen Anzahl an Prüfungen, ob eine Kante in den besuchten schon vorhanden ist, bietet das Hashset die beste Performance
    private static HashSet<Edge> visitedEdges;

    public static List<Graph> findEulerGraphs(Graph graph) {

        // Da der Ergebnisliste, sowie der gecacheten Kanten nur Elemente hinzugefügt werden, und iterativ gelesen werden, ist die LinkedList optimal geeignet
        List<Graph> result = new LinkedList<Graph>();
        List<Edge> cachedEdges = new LinkedList<Edge>();
        // Anders als bei den cached Kanten, werden der Liste der Kreisknoten im Vergleich kaum Elemente hinzugefügt, daher lohnt sich der Einsatz einer ArrayList
        List<Node> circleNodes = new ArrayList<Node>();
        // Hole so lange Eulerkreise aus dem Graph, bis keine Kanten mehr im Graph vorhanden sind
        while(graph.getEdgeCount() > 0) {
            Node startNode;

            if(circleNodes.isEmpty()){
                // Beginne mit dem ersten Knoten im Graphen als Startknoten
                startNode = graph.getNode(0);
            } else {
                // Sonst nehme den ersten Knoten des zuletzt gebauten Eulerkreises, der noch Nachbarn hat
                startNode = circleNodes.stream().filter(node -> node.getDegree() > 0).findAny().orElseThrow();
            }
            // Suche nächsten Eulerkreis und füge ihn zu den bisher gefundenen hinzu
            circleNodes.addAll(findEulerCircle(startNode));

            // Füge den Eulerkreis der Ergebnisliste als Graph hinzu
            result.add(getGraphFromEdges(visitedEdges));
            // Cache besuchte Kanten und lösche sie aus dem Graph
            visitedEdges.forEach(e -> {
                cachedEdges.add(e);
                graph.removeEdge(e.getId());
            });
        }
        // Zur weiteren Verwendung des übergeben Graphens, füge die gecacheten Kanten dem Graph wieder hinzu
        cachedEdges.forEach(e -> graph.addEdge(e.getId(), e.getSourceNode(), e.getTargetNode()));
        return result;
    }
    private static List<Node> findEulerCircle(Node startNode){
        // Der zu untersuchende Knoten ist zu Beginn der Startknoten
        Node currentNode = startNode;
        // Initialisiere List, in die die Knoten des Kreises geschrieben werden
        List<Node> circleNodes = new ArrayList<Node>();

        // Die besuchten Kanten neu initialisieren
        visitedEdges = new HashSet<Edge>();
        // Führe folgenden Block so lange aus, bis der Kreis geschlossen ist
        do {
            circleNodes.add(currentNode);
            // Ist diese schon Teil des bisherigen Kreises, suche nächste Kante
            Edge nextEdge = currentNode.edges().filter(edge -> !visitedEdges.contains(edge)).findAny().orElseThrow();

            // Füge gefundene Kante dem Kreis hinzu
            visitedEdges.add(nextEdge);
            // Betrachte nächsten Knoten
            currentNode = nextEdge.getOpposite(currentNode);
        } while(!startNode.getId().equals(currentNode.getId()));
        return circleNodes;
    }

    private static boolean isValidEulerGraph(Graph g){
        for(int i = 0; i < g.getNodeCount(); i++){
            if(!g.getNode(i).edges().findAny().isPresent() || (g.getNode(i).getDegree()%2) != 0){
                return false;
            }
        }
        return true;
    }

    private static Graph getGraphFromEdges(HashSet<Edge> edges){
        // Erstelle Teilgraph
        Graph newGraph = new MultiGraph("Teilgraph_"+edges.stream().findFirst().orElseThrow().getId());
        newGraph.setAutoCreate(true);
        newGraph.setStrict(false);
        edges.forEach(e -> newGraph.addEdge(e.getId(),e.getSourceNode().getId(), e.getTargetNode().getId()));

        return newGraph;
    }

    private static List<Node> getDublicates(List<Node> list) {
        Set<Node> elements = new HashSet<Node>();
        return list.stream()
                .filter(n -> !elements.add(n))
                .collect(Collectors.toList());
    }
}
