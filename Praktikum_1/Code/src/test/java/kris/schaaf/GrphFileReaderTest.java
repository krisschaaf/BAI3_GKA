package kris.schaaf;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrphFileReaderTest {

    @Test
    void returnsTrueWhenFileIsDirected() {
        List<String> directedGraph1 = new ArrayList<>(Arrays.asList("#directed:BSP2;", "a:11-b:3 (e1);"));
        List<String> directedGraph2 = new ArrayList<>(Arrays.asList("#directed:BSP4;", "v1- v2;", "v2-v3::44;"));

        assertTrue(GrphFileReader.isDirected(directedGraph1));
        assertTrue(GrphFileReader.isDirected(directedGraph2));
    }

    @Test
    void returnsFalseWhenFileIsUndirected() {
        List<String> undirectedGraph1 = new ArrayList<>(Arrays.asList("#undirected:BSP1;", "v1-v2;", "v2 - v4;", "v3;"));
        List<String> undirectedGraph2 = new ArrayList<>(Arrays.asList("#undirected:BSP3;", "Hamburg-Bremen::123;", "Hamburg- Berlin::289;"));

        assertFalse(GrphFileReader.isDirected(undirectedGraph1));
        assertFalse(GrphFileReader.isDirected(undirectedGraph2));
    }

    @Test
    void returnsTrueWhenFileIsUndirectedAndContainsLength() {
        List<String> undirectedGraphWithLength = new ArrayList<>(Arrays.asList("#undirected:BSP3;", "Hamburg-Bremen::123;", "Hamburg- Berlin::289;"));

        assertTrue(GrphFileReader.isUndirectedAndContainsLength(undirectedGraphWithLength));
    }

    @Test
    void returnsFalseWhenFileIsUndirectedAndDoesNotContainLength() {
        List<String> undirectedGraphNoLength = new ArrayList<>(Arrays.asList("#undirected:BSP3;", "Hamburg-Bremen::123;", "Hamburg- Berlin;"));

        assertFalse(GrphFileReader.isUndirectedAndContainsLength(undirectedGraphNoLength));
    }
}
