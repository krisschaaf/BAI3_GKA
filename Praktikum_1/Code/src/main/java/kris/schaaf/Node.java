package kris.schaaf;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Node {
    private String name;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node( String name) {
        this.name = name;
    }

    public void addDestination (Node destination , int distance ) {
        adjacentNodes.put(destination , distance);
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return this.adjacentNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) {
            return false;
        }
        return this.getName().equals(((Node) o).getName());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
