package haw.gka.kruskal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashSet;

@AllArgsConstructor
@Getter
public class KruskalResult {
    private MultiGraph graph;
    private HashSet<Edge> edges;
    private Integer treeWeight;
}
