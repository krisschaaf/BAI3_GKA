package haw.gka.dijkstra.utils;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.List;
import java.util.stream.Collectors;

public class NodeUtils {

    public static List<MultiNode> getAdjacentNodes(MultiNode source){
        return source.neighborNodes()
                .map(node -> castToAdjacentNode(node))
                .filter(node -> edgeHasCorrectDirectionWhenDirected(source, node)) // filtering after mapping because of typecast
                .collect(Collectors.toList());
    }

    private static boolean edgeHasCorrectDirectionWhenDirected(MultiNode source, MultiNode adjacentNode) {
        if (source.getEdgeBetween(adjacentNode).isDirected()) {
            return source.hasEdgeToward(adjacentNode);
        }
        return true;
    }

    private static MultiNode castToAdjacentNode(Node node) {
        if(!(node instanceof MultiNode)) {
            throw new ClassCastException("No Multinode provided!");
        }
        MultiNode adjacentNode = (MultiNode) node;
        return adjacentNode;
    }

    public static boolean graphContainsNode(MultiNode node, MultiGraph graph) {
        return graph.edges()
                .filter(edge -> edge.getSourceNode() == node || edge.getTargetNode() == node)
                .collect(Collectors.toList())
                .size() > 0;
    }
}
