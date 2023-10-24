package kris.schaaf;

import org.graphstream.graph.implementations.MultiNode;

import java.util.LinkedList;
import java.util.List;


public class PriorityQueueItem {
    private int distance = Integer.MAX_VALUE;
    private List<MultiNode> nodes = new LinkedList<>();

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    public void addNode(MultiNode node) {
        this.nodes.add(node);
    }

    public MultiNode getLastNode() {
        return this.nodes.get(this.nodes.size() - 1);
    }

    public List<MultiNode> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<MultiNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Distance: " + this.getDistance() +
                " via Nodes: " + this.getNodes().toString() +
                ".";
    }
}
