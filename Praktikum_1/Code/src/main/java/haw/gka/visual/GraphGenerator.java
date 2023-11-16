package haw.gka.visual;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {
    public static MultiGraph generateGraph(int vertexAmount, int edgesAmount, int maxWeight, boolean direktet ) {
        MultiGraph graph = new MultiGraph("Graph");
        System.setProperty("org.graphstream.ui", "swing");
        IntStream.range(1, vertexAmount).forEach(x -> {
            String nodeName = "node" + x;
            MultiNode node = new MultiNode(graph, nodeName);
            graph.addNode(String.valueOf(node));
        });
        List<Node> nodes = graph.nodes().collect(Collectors.toList());
        Collections.shuffle(nodes);
        Random random = new Random();
        IntStream.range(1, vertexAmount).forEach(i -> {

            Node firstNode = null;
            Node secondNode = null;

            int randomNumber1 = random.nextInt(vertexAmount - 1);
            int randomNumber2 = random.nextInt(vertexAmount - 1);
            if (randomNumber1 == randomNumber2) {
                randomNumber2++;
            }
            firstNode = nodes.get(randomNumber1);
            secondNode = nodes.get(randomNumber2);
            graph.addEdge("edge" + i, firstNode, secondNode, direktet).setAttribute("weight", random.nextInt(maxWeight));
        });
        return graph;
    }
}
