package haw.gka.visual;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Visualisation {
    static public void paintEdges(MultiGraph graph, List<MultiNode> pathNodes){
        List<Edge> edges = IntStream.rangeClosed(0, pathNodes.size() - 2)
                .mapToObj(startIndex -> {
                    List<MultiNode> tupleNode = pathNodes.subList(startIndex, startIndex + 2);
                    return tupleNode.get(0).getEdgeBetween(tupleNode.get(1));
                }).collect(Collectors.toList());

        edges.stream().forEach(x -> x.setAttribute("ui.style", "size: 5px; fill-color: red;"));
        //pathNodes.stream().forEach(x -> x.setAttribute("ui.style","size: 10px;" ));
    }


}
