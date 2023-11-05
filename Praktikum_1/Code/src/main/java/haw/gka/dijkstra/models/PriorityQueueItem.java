package haw.gka.dijkstra.models;

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

    public void markUp(){
        nodes.get(0).setAttribute("ui.style", "fill-color: rgb(0,100,255);");
        for (int i = 0; i < nodes.size()-1; i++){
            nodes.get(i+1).setAttribute("ui.style", "fill-color: rgb(0,100,255);");
            nodes.get(i).getEdgeToward(nodes.get((i+1))).setAttribute("ui.style", "fill-color: rgb(0,100,255);");
        }

    }

    @Override
    public String toString() {
        return "Distance: " + this.getDistance() +
                " via Nodes: " + this.getNodes().toString() +
                ".";
    }


}
