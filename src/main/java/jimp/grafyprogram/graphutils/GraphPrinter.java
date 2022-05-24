package jimp.grafyprogram.graphutils;

import java.io.IOException;

abstract public class GraphPrinter {

    protected Graph graph;

    abstract public void print() throws IOException;
}
