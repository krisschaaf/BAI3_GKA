package haw.gka.csv;

import lombok.Getter;

@Getter
public enum GraphType {
    SINGLE_GRAPH("SingleGraph"),
    MULTI_GRAPH("MultiGraph");

    private final String value;

    GraphType(String value) {
        this.value = value;
    }
}
