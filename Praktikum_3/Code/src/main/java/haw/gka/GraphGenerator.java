package haw.gka;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {

    public static Graph createEulerGraph(int nodesAmount, int edgesAmount, String id) throws Exception {
        if (edgesAmount < nodesAmount-1){
            throw new Exception ("Amount of Edges have to be more then " + (nodesAmount-1));
            }

        MultiGraph graph = new MultiGraph(id);
        Random random = new Random();
        //Knoten im Grapph hinzufügen
        IntStream.range(0, nodesAmount).forEach(x -> {
            String nodeName = "node" + x;
            MultiNode node = new MultiNode(graph, nodeName);
            graph.addNode(String.valueOf(node));
        });
        //pos : V -> {1; :::; m }  injektive Abb der n Knoten auf die m Position
        Map<Node, Integer> posHashMap = new HashMap<>();
        //Erstellen Liste mit Nodes  V = {1; :::; n}
        List<Node> nodes = graph.nodes().collect(Collectors.toList());
        //Positionen im Eulerkreis
        List<Integer> positions = IntStream.rangeClosed(0, edgesAmount - 1)
                .boxed().collect(Collectors.toList());
        //Collections.shuffle(positions);
       Collections.shuffle(nodes);
        for (int i = 0; i <nodesAmount; i++) { //*
            int nextPos = random.nextInt(edgesAmount-1);
            //i-tem Element aus der Liste von Nodes random Position zuweisen
            posHashMap.put(nodes.get(i), nextPos);
            positions.remove((Object) nextPos);
        }

        Node start = null;

        start = getNextNode(posHashMap, nodes, 0);
        Node actualStart = start;

        for (int i = 1; i < edgesAmount; i++) {
            Node actualTarget = getNextNode(posHashMap, nodes, i);
            String edgeName = String.format("edge%s", i);
            if ((i < nodesAmount) &&(!actualStart.equals(nodes.get(i)))){
                actualTarget = nodes.get(i);
            } else {
//                actualTarget = getNextNode(posHashMap, nodes, i);
//            Ohne Schlinge:
                do {
                    actualTarget = getNextNode(posHashMap, nodes, i);
                }while (actualStart.equals(actualTarget));


            }
//

            graph.addEdge(edgeName, actualStart, actualTarget);
            actualStart = actualTarget;
            if (i == nodesAmount - 1) {
                graph.addEdge(String.format("edge%s", edgesAmount), actualTarget, start);

            }

        }

        return graph;
    }

    private static Node getNextNode(Map<Node, Integer> posHashMap, List<Node> nodes, int posNr) {
        Node node = null;
        Random random = new Random();
        if (!posHashMap.containsKey(posNr)) {
            node = nodes.get(random.nextInt(nodes.size()));
            posHashMap.put(node, posNr);
        } else {
            for (Map.Entry<Node, Integer> entry : posHashMap.entrySet()) {

                if (Objects.equals(posNr, entry.getValue())) {
                    node = entry.getKey();
                }
            }
        }

        return node;
    }
}
