package kris.schaaf.dijkstra.utils;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiNode;

public class NodeUtils {

    public static MultiNode castToAdjacentNode(Node node) {
        if(!(node instanceof MultiNode)) {
            throw new ClassCastException("No Multinode provided!");
        }
        MultiNode adjacentNode = (MultiNode) node;
        return adjacentNode;
    }
}
