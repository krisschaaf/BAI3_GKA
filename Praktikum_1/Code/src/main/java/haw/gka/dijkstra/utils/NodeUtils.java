package haw.gka.dijkstra.utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeUtils {

    public static List<MultiNode> getAdjacentNodes(MultiNode source){
        List<Node> allAdjacentNodes = source.neighborNodes().collect(Collectors.toList());
        List<MultiNode> reachableNeighbors = new LinkedList<>();

        for(Node node : allAdjacentNodes){

            MultiNode adjacentNode = castToAdjacentNode(node);

            if(adjacentNode.getEdgeBetween(source) == null) {
                continue;
            }
            if (adjacentNode.getEdgeBetween(source).isDirected()) {

                // Inzidente Kante muss vom Startknoten zum Endknoten zeigen
                if (adjacentNode.getEdgeBetween(source).equals(source.getEdgeToward(adjacentNode))) {
                    reachableNeighbors.add(adjacentNode);
                }

            } else {
                reachableNeighbors.add(adjacentNode);
            }
        }
        return reachableNeighbors;
    }

    private static MultiNode castToAdjacentNode(Node node) {
        if(!(node instanceof MultiNode)) {
            throw new ClassCastException("No Multinode provided!");
        }
        MultiNode adjacentNode = (MultiNode) node;
        return adjacentNode;
    }

    public static boolean graphContainsNode(MultiNode node, MultiGraph graph) {
        for (Edge edge: graph.edges().collect(Collectors.toList())) {
            if (edge.getSourceNode() == node || edge.getTargetNode() == node) {
                return true;
            }
        }
        return false;
    }
}
