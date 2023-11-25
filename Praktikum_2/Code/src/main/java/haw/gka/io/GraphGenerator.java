package haw.gka.io;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {

    public static MultiGraph generateGraph(int vertexAmount, int edgesAmount, int maxWeight, boolean direktet ) {
        MultiGraph graph = new MultiGraph("Graph");
        System.setProperty("org.graphstream.ui", "swing");

        IntStream.range(0, vertexAmount).forEach(x -> {
            String nodeName = "node" + x;
            MultiNode node = new MultiNode(graph, nodeName);
            graph.addNode(String.valueOf(node));
        });
        List<Node> nodes = graph.nodes().collect(Collectors.toList());
        Collections.shuffle(nodes);
        Random random = new Random();
       List<Edge> edges = new ArrayList<>();
        IntStream.range(0, edgesAmount).forEach(i -> {

            Node firstNode ;
            Node secondNode;

            int randomNumber1 = random.nextInt(vertexAmount - 1);
            int randomNumber2 = random.nextInt(vertexAmount - 1);
            if (randomNumber1 == randomNumber2) {
                randomNumber2++;
            }
            firstNode = nodes.get(randomNumber1);
            secondNode = nodes.get(randomNumber2);
            String edgeName = "edge" + i;
            graph.addEdge(edgeName, firstNode, secondNode, direktet).setAttribute("weight", random.nextInt(maxWeight));
            edges.add(graph.getEdge(edgeName));
        });
        System.out.println("Amount of nodes: " + nodes.size());
        System.out.println("Amount of Edges: " + edges.size());
        return graph;
    }
}
