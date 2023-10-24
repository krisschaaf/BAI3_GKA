package kris.schaaf;

import java.util.LinkedList;
import java.util.List;


public class PriorityQueueItem {
    private int distance = Integer.MAX_VALUE;
    private List<Node> nodes = new LinkedList<>();

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public Node getLastNode() {
        return this.nodes.get(this.nodes.size() - 1);
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Distance: " + this.getDistance() +
                " via Nodes: " + this.getNodes().toString() +
                ".";
    }
}
