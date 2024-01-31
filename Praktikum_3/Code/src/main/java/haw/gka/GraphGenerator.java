package haw.gka;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {

    public static Graph createEulerGraph(int nodesAmount, int edgesAmount, String id) {
        //Anzahl von Edges prüfen damit Graph zusammenhängen bleibt
        if (edgesAmount < nodesAmount) {
            throw new IllegalArgumentException("Amount of Edges have to be more then " + (nodesAmount - 1));
        }

        MultiGraph graph = new MultiGraph(id);
        Random random = new Random();
        //Knoten im Grapph hinzufügen
        IntStream.range(0, nodesAmount).forEach(x -> {
            String nodeName = "node" + x;
            MultiNode node = new MultiNode(graph, nodeName);
            graph.addNode(String.valueOf(node));
        });

        //Erstellen Liste mit Nodes  V = {1; :::; n}
        List<Node> nodes = graph.nodes().collect(Collectors.toList());
        //Positionen im Eulerkreis
        List<Integer> positions = IntStream.rangeClosed(0, edgesAmount - 1)
                .boxed().collect(Collectors.toList());


        //pos : V -> {1; :::; m }  injektive Abb der n Knoten auf die m Position
        Map<Node, Integer> posHashMap = new HashMap<>();
        for (int i = 0; i < nodesAmount; i++) {
            int nextPos = random.nextInt(edgesAmount );
            //i-tem Element aus der Liste von Nodes random Position zuweisen
            posHashMap.put(nodes.get(i), nextPos);
            positions.remove((Object) nextPos);
        }


        Node start = getNextNode(posHashMap, nodes, 0);
        Node actualSource = start;

        //Damit Graph zudammenhängend bleibt zuerst fügen wir alle Knoten aus der geshuffelten Klotenliste hinzu
        Collections.shuffle(nodes);
        for (int i = 0; i < edgesAmount; i++) {
            Node actualTarget;
            String edgeName = String.format("edge%s", i);
            if ((i < nodesAmount) && (!actualSource.equals(nodes.get(i)))) {
                actualTarget = nodes.get(i);
            } else {
                do {
                    actualTarget = getNextNode(posHashMap, nodes, i);
                } while (actualSource.equals(actualTarget));
            }

            graph.addEdge(edgeName, actualSource, actualTarget);
            actualSource = actualTarget;
            if ((i == edgesAmount-1)&& (!actualSource.equals(start))) {
                graph.addEdge(String.format("edge%s", edgesAmount), actualTarget, start);
            } else if ((i == edgesAmount-1)&& (actualSource.equals(start))){
                do {
                    actualTarget = getNextNode(posHashMap, nodes, i);
                } while (actualSource.equals(actualTarget));
            }
        }
        checkGraphNodes(graph);
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

    private static void checkGraphNodes(MultiGraph graph){
        graph.nodes().forEach(node -> {
            int grad = node.enteringEdges().collect(Collectors.toSet()).size();
            if (grad % 2 == 1)
                System.out.println("!!!Wrang   " +(grad));
        });
    }
}