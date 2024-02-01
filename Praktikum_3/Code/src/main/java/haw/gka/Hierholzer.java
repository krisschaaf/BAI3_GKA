package haw.gka;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Hierholzer {
    // Wegen der hohen Anzahl an Prüfungen, ob eine Kante in den besuchten schon vorhanden ist, bietet das Hashset die beste Performance
    private static HashSet<Edge> visitedEdges;

    public static List<Graph> findEulerGraphs(Graph graph) {

        if(!graphIsValid(graph)) {
            throw new IllegalArgumentException("Provide a valid Graph!");
        }

        // Da der Ergebnisliste, sowie der gecacheten Kanten nur Elemente hinzugefügt werden, und iterativ gelesen werden, ist die LinkedList optimal geeignet
        List<Graph> result = new LinkedList<>();
        List<Edge> cachedEdges = new LinkedList<>();
        // Anders als bei den cached Kanten, werden der Liste der Kreisknoten im Vergleich kaum Elemente hinzugefügt, daher lohnt sich der Einsatz einer ArrayList
        List<Node> circleNodes = new ArrayList<>();
        // Hole so lange Eulerkreise aus dem Graph, bis keine Kanten mehr im Graph vorhanden sind
        while(graph.getEdgeCount() > 0) {
            Node startNode;

            if(circleNodes.isEmpty()){
                // Beginne mit dem ersten Knoten im Graphen als Startknoten
                startNode = graph.getNode(0);
            } else {
                // Sonst nehme den ersten Knoten des zuletzt gebauten Eulerkreises, der noch Nachbarn hat
                startNode = circleNodes.stream().filter(node -> node.getDegree() > 0).findAny().orElseThrow(() -> new RuntimeException("No Node with Degree > 0 found!"));
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
        List<Node> circleNodes = new ArrayList<>();

        // Die besuchten Kanten neu initialisieren
        visitedEdges = new HashSet<>();
        // Führe folgenden Block so lange aus, bis der Kreis geschlossen ist
        do {
            circleNodes.add(currentNode);
            // Ist diese schon Teil des bisherigen Kreises, suche nächste Kante
            Edge nextEdge = currentNode.edges().filter(edge -> !visitedEdges.contains(edge)).findAny().orElseThrow(() -> new RuntimeException(""));

            // Füge gefundene Kante dem Kreis hinzu
            visitedEdges.add(nextEdge);
            // Betrachte nächsten Knoten
            currentNode = nextEdge.getOpposite(currentNode);
        } while(!startNode.getId().equals(currentNode.getId()));
        return circleNodes;
    }

    private static Graph getGraphFromEdges(HashSet<Edge> edges){
        // Erstelle Teilgraph
        Graph newGraph = new MultiGraph("Teilgraph_"+edges.stream().findFirst().orElseThrow(() -> new RuntimeException("")).getId());
        newGraph.setAutoCreate(true);
        newGraph.setStrict(false);
        edges.forEach(e -> newGraph.addEdge(e.getId(),e.getSourceNode().getId(), e.getTargetNode().getId()));

        return newGraph;
    }

    private static boolean graphIsValid(Graph graph) {
        if (graph.edges().anyMatch(Edge::isDirected)) {
            return false;
        }

        List<Node> nodesWithFalseDegree = graph.nodes().filter(node -> node.getDegree() % 2 != 0).collect(Collectors.toList());
        for(Node node: nodesWithFalseDegree) {
            if(node.getEdgeBetween(node.getId()) == null) {
                return false;
            }
        }

        return true;
    }
}
