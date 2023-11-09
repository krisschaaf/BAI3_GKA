package haw.gka.dijkstra.utils;

import haw.gka.exceptions.MultiEdgeWithSameDirectionException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NodeUtils {

    public static List<MultiNode> getAdjacentNodes(MultiNode source) throws MultiEdgeWithSameDirectionException {
        List<MultiNode> reachableNeighbours = source.neighborNodes()
                .map(node -> castToAdjacentNode(node))
                .filter(node -> edgeHasCorrectDirectionWhenDirected(source, node)) // filtering after mapping because of typecast
                .collect(Collectors.toList());

        if(hasMultiEdgesWithSameDirection(reachableNeighbours)) {
            throw new MultiEdgeWithSameDirectionException();
        }

        return reachableNeighbours;
    }

    private static boolean edgeHasCorrectDirectionWhenDirected(MultiNode source, MultiNode adjacentNode) {
        return source.getEdgeBetween(adjacentNode).isDirected() ? source.hasEdgeToward(adjacentNode): true;
    }

    private static MultiNode castToAdjacentNode(Node node) {
        if(!(node instanceof MultiNode)) {
            throw new ClassCastException("No Multinode provided!");
        }
        MultiNode adjacentNode = (MultiNode) node;
        return adjacentNode;
    }

    private static boolean hasMultiEdgesWithSameDirection(List<MultiNode> multiNodes) {
        for (MultiNode multiNode : multiNodes) {
            List<String> checkedEdges = new ArrayList<>();

            for (Edge edge : multiNode.edges().collect(Collectors.toList())) {
                String sourceNodeId = edge.getSourceNode().getId();
                String targetNodeId = edge.getTargetNode().getId();
                String edgeKey = sourceNodeId + "-" + targetNodeId;

                if (checkedEdges.contains(edgeKey)) {
                    return true;
                }

                checkedEdges.add(edgeKey);
            }
        }

        return false;
    }

    public static boolean graphContainsNode(MultiNode node, MultiGraph graph) {
        return graph.edges()
                .filter(edge -> edge.getSourceNode() == node || edge.getTargetNode() == node)
                .findFirst().isPresent();
    }
}
