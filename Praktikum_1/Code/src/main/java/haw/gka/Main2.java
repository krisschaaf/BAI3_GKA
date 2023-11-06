package haw.gka;

import haw.gka.dijkstra.Dijkstra;
import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.exceptions.NodeNotFoundException;
import haw.gka.visual.Visualisation;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.List;

public class Main2 {

    public static void main(String[] args) throws NodeNotFoundException {

        MultiGraph graph = new MultiGraph("GraphWithLoop");
        graph.setAttribute("ui.stylesheet", styleSheet);
        MultiNode node1 = new MultiNode(graph, "node1");
        MultiNode node2 = new MultiNode(graph, "node2");
        MultiNode node3 = new MultiNode(graph, "node3");
        MultiNode node4 = new MultiNode(graph, "node4");
        MultiNode node5 = new MultiNode(graph, "node5");
        MultiNode node6 = new MultiNode(graph, "node6");
        MultiNode node7 = new MultiNode(graph, "node7");
        MultiNode node8 = new MultiNode(graph, "node8");

        int graphToDisplay = 1;
        switch (graphToDisplay){
            case 1:


                node2.setAttribute("ui.label", "Node 2");
                node3.setAttribute("ui.label", "Node 3");
                node4.setAttribute("ui.label", "Node 4");
                node5.setAttribute("ui.label", "Node 5");
                node6.setAttribute("ui.label", "Node 6");
                node6.setAttribute("ui.label", "Node 7");
                node6.setAttribute("ui.label", "Node 8");

                graph.addNode("node1").setAttribute("ui.label", "Node 1");;
                graph.addNode("node2").setAttribute("ui.label", "Node 2");
                graph.addNode("node3").setAttribute("ui.label", "Node 3");
                graph.addNode("node4").setAttribute("ui.label", "Node 4");
                graph.addNode("node5").setAttribute("ui.label", "Node 5");
                graph.addNode("node6").setAttribute("ui.label", "Node 6");
                graph.addNode("node7").setAttribute("ui.label", "Node 7");
                graph.addNode("node8").setAttribute("ui.label", "Node 8");
                graph.addEdge("edge18", node1, node8);
                graph.addEdge("edge12", node1, node2);
                graph.addEdge("edge23", node2, node3);
                graph.addEdge("edge34", node3, node4);
                graph.addEdge("edge45", node4, node5);
                graph.addEdge("edge15", node1, node5);
                graph.addEdge("edge56", node5, node6);
                graph.addEdge("edge67", node6, node7);
                graph.addEdge("edge78", node7, node8);

                graph.getEdge("edge12").setAttribute("weight", 4);
                graph.getEdge("edge12").setAttribute("ui.label", 4);
                graph.getEdge("edge23").setAttribute("weight", 1);
                graph.getEdge("edge23").setAttribute("ui.label", 1);
                graph.getEdge("edge34").setAttribute("weight", 1);
                graph.getEdge("edge34").setAttribute("ui.label", 1);
                graph.getEdge("edge45").setAttribute("weight", 1);
                graph.getEdge("edge45").setAttribute("ui.label", 1);
                graph.getEdge("edge18").setAttribute("weight", 100);
                graph.getEdge("edge18").setAttribute("ui.label", 100);
                graph.getEdge("edge15").setAttribute("weight", 100);
                graph.getEdge("edge15").setAttribute("ui.label", 100);
                graph.getEdge("edge56").setAttribute("weight", 1);
                graph.getEdge("edge56").setAttribute("ui.label", 1);
                graph.getEdge("edge67").setAttribute("weight", 1);
                graph.getEdge("edge67").setAttribute("ui.label", 1);
                graph.getEdge("edge78").setAttribute("weight", 12);
                graph.getEdge("edge78").setAttribute("ui.label", 12);

                PriorityQueueItem dijRes = Dijkstra.calculateFastestPath(node1, node8, graph);


                List<MultiNode> nodeList =  dijRes.getNodes();
                Visualisation.paintEdges(graph, nodeList);
                System.setProperty("org.graphstream.ui", "swing");
                graph.display();
                break;
            case 2:


                node2.setAttribute("ui.label", "Node 2");
                node3.setAttribute("ui.label", "Node 3");
                node4.setAttribute("ui.label", "Node 4");
                node5.setAttribute("ui.label", "Node 5");
                node6.setAttribute("ui.label", "Node 6");

                graph.addNode("node1").setAttribute("ui.label", "Node 1");;
                graph.addNode("node2").setAttribute("ui.label", "Node 2");
                graph.addNode("node3").setAttribute("ui.label", "Node 3");
                graph.addNode("node4").setAttribute("ui.label", "Node 4");
                graph.addNode("node5").setAttribute("ui.label", "Node 5");
                graph.addNode("node6").setAttribute("ui.label", "Node 6");
                graph.addNode("node7").setAttribute("ui.label", "Node 7");
                graph.addNode("node8").setAttribute("ui.label", "Node 8");
                graph.addEdge("edge12", node1, node2);
                graph.addEdge("edge23", node2, node3);
                graph.addEdge("edge34", node3, node4);
                graph.addEdge("edge45", node4, node5);
                graph.addEdge("edge52", node5, node2);
                graph.addEdge("edge36", node3, node6);
                graph.addEdge("edge67", node6, node7);
                graph.addEdge("edge78", node7, node8);

                graph.getEdge("edge12").setAttribute("weight", 1);
                graph.getEdge("edge12").setAttribute("ui.label", 1);
                graph.getEdge("edge23").setAttribute("weight", 1);
                graph.getEdge("edge23").setAttribute("ui.label", 1);
                graph.getEdge("edge34").setAttribute("weight", 1);
                graph.getEdge("edge34").setAttribute("ui.label", 1);
                graph.getEdge("edge45").setAttribute("weight", 1);
                graph.getEdge("edge45").setAttribute("ui.label", 1);
                graph.getEdge("edge52").setAttribute("weight", 1);
                graph.getEdge("edge52").setAttribute("ui.label", 1);
                graph.getEdge("edge36").setAttribute("weight", 100);
                graph.getEdge("edge36").setAttribute("ui.label", 100);
                graph.getEdge("edge67").setAttribute("weight", 1);
                graph.getEdge("edge67").setAttribute("ui.label", 1);
                graph.getEdge("edge78").setAttribute("weight", 1);
                graph.getEdge("edge78").setAttribute("ui.label", 1);

                dijRes = Dijkstra.calculateFastestPath(node1, node8, graph);

                nodeList =  dijRes.getNodes();
                Visualisation.paintEdges(graph, nodeList);
                System.setProperty("org.graphstream.ui", "swing");
                graph.display();
                break;
        }
    }

    protected static String styleSheet =
            "node {" +
                    "   shape: circle;" +
                    "   stroke-mode: plain;" +
                    "   text-mode: normal;" +
                    "   text-alignment: under;" +
                    "}" +
                    "edge {" +
                    "   shape: line;" +

                    "}"; // Закрывающая фигурная скобка для стиля edge.red


}


