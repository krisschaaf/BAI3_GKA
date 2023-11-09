package haw.gka.exceptions;

public class MultiEdgeWithSameDirectionException extends Exception{
    private static final String DEFAULT_MESSAGE = "Due to a found non inverse Multiedge between two nodes this result might be falsified.";
    public MultiEdgeWithSameDirectionException(String errorMessage) {
        super(errorMessage);
    }
    public MultiEdgeWithSameDirectionException() {
        super(DEFAULT_MESSAGE);
    }
}
