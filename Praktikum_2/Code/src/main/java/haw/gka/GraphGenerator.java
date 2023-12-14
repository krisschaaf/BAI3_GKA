package haw.gka;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {

    public static MultiGraph generateGraph(int nodesAmount, int edgesAmount, int maxWeight, boolean directed) {
        MultiGraph graph = new MultiGraph("Graph");
        System.setProperty("org.graphstream.ui", "swing");

        IntStream.range(0, nodesAmount).forEach(x -> {
            String nodeName = "node" + x;
            MultiNode node = new MultiNode(graph, nodeName);
            graph.addNode(String.valueOf(node));
        });
        List<Node> nodes = graph.nodes().collect(Collectors.toList());
        Collections.shuffle(nodes); // TODO: kann weg?
        Random random = new Random();
        List<Edge> edges = new ArrayList<>();
        IntStream.range(0, edgesAmount).forEach(i -> {

            Node firstNode ;
            Node secondNode;

            int randomNumber1 = random.nextInt(nodesAmount - 1);
            int randomNumber2 = random.nextInt(nodesAmount - 1);
            if (randomNumber1 == randomNumber2) {
                randomNumber2++;
            }
            firstNode = nodes.get(randomNumber1);
            secondNode = nodes.get(randomNumber2);
            String edgeName = "edge" + i;
            graph.addEdge(edgeName, firstNode, secondNode, directed).setAttribute("weight", random.nextInt(maxWeight - 1) + 1);
            edges.add(graph.getEdge(edgeName));
        });
        System.out.println("Amount of nodes: " + nodes.size());
        System.out.println("Amount of Edges: " + edges.size());
        return graph;
    }
}
