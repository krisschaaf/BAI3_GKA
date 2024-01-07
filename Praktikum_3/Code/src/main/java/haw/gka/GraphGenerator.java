package haw.gka;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {

    public static Graph createEulerGraph(int nodesAmount, int edgeCount, String id) {
        int minEdgeAmount = nodesAmount * (nodesAmount - 1) / 2;
        MultiGraph graph = new MultiGraph(id);
        IntStream.range(0, nodesAmount).forEach(x -> {
            String nodeName = "node" + x;
            MultiNode node = new MultiNode(graph, nodeName);
            graph.addNode(String.valueOf(node));
        });
        Random random = new Random();

        Map<Node, Integer> posHashMap = new HashMap<>();
        //Erstellen Liste mit Nodes
        List<Node> nodes = graph.nodes().collect(Collectors.toList());
        //Erstellen Liste mit Positions
        List<Integer> positions = IntStream.rangeClosed(0, nodesAmount - 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(positions);
        Collections.shuffle(nodes);
        for (int i = 0; i < nodesAmount; i++) {
            int nextInd = positions.get(i);
            //i-tem Element aus der Liste von Nodes random Position zuweisen
            posHashMap.put(nodes.get(i), nextInd);
        }

        Node start = null;

        start = getNextNode(posHashMap, nodes, 0);
        Node actualStart = start;

        for (int i = 1; i < nodesAmount; i++) {
            Node actualTarget = getNextNode(posHashMap, nodes, i);
            String edgeName = String.format("edge%s", i);
            graph.addEdge(edgeName, actualStart, actualTarget);
            actualStart = actualTarget;
            if (i == nodesAmount - 1) {
                graph.addEdge(String.format("edge%s", nodesAmount), actualTarget, start);
            }
        }

        return graph;
    }

    private static Node getNextNode(Map<Node, Integer> posHashMap, List<Node> nodes, int posNr) {
        Node node = null;
        if (!posHashMap.containsKey(posNr)) {
            node = nodes.get(posNr);
        } else {
            for (Map.Entry<Node, Integer> entry : posHashMap.entrySet()) {
                if (Objects.equals(0, entry.getValue())) {
                    node = entry.getKey();
                }
            }
        }
        return node;
    }
}
