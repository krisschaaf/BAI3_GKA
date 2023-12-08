package haw.gka.kruskal;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashSet;

public class DisjointSet {

    public static HashSet<HashSet<Node>> makeSet(HashSet<Node> initSet) {
        HashSet<HashSet<Node>> disjointSet = new HashSet<>();
        disjointSet.add(initSet);

        return disjointSet;
    }

    public static HashSet<HashSet<Node>> makeSet(MultiGraph graph) {
        HashSet<HashSet<Node>> disjointSet = new HashSet<>();

        graph.nodes().forEach((node) -> {
            HashSet<Node> mstDisjointSet = new HashSet<>();
            mstDisjointSet.add(node);
            disjointSet.add(mstDisjointSet);
        });

        return disjointSet;
    }

    public static void union(HashSet<Node> sourceSet, HashSet<Node> targetSet, HashSet<HashSet<Node>> disjointSet) {
        HashSet<Node> unionSet = new HashSet<>();
        unionSet.addAll(sourceSet);
        unionSet.addAll(targetSet);

        disjointSet.add(unionSet);
        disjointSet.remove(targetSet);
        disjointSet.remove(sourceSet);
    }

    public static HashSet<Node> findSet(HashSet<HashSet<Node>> disjointSet, Node node) {
        HashSet<Node> result = null;

        for (HashSet<Node> set: disjointSet){
            if (set.contains(node)) {
                result = set;
            }
        }

        return result;
    }
}
