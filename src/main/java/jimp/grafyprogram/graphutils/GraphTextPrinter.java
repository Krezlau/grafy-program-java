package jimp.grafyprogram.graphutils;

import java.io.FileWriter;
import java.io.IOException;

public class GraphTextPrinter extends GraphPrinter{

    protected String filePath;

    protected Graph graph;

    public GraphTextPrinter(String filePath, Graph graph) {
        this.filePath = filePath;
        this.graph = graph;
    }

    @Override
    public void print() throws IOException {
        FileWriter output = new FileWriter(filePath);
        output.write(String.format("%d %d\n",graph.getRows(),graph.getCollumns()));

        for (int i = 0; i < graph.getSize(); i++) {
            output.write(String.format("     "));
            for (int j = 0; j < graph.getNodeToGraph(i).getSize(); j++) {
                output.write(String.format("%d :%.16f  ", graph.getNodeToGraph(i).getEdgeToNode(j).getNodeId(), graph.getNodeToGraph(i).getEdgeToNode(j).getWeight()));
            }
            output.write(String.format("\n"));
        }
        output.close();
    }

    //probne wypisywanie zawartosci grafu do konsoli
    public void printToConsol() {
        System.out.println(graph.getRows() + " " + graph.getCollumns());
        for (int i = 0; i < graph.getSize(); i++) {

            for (int j = 0; j < graph.getNodeToGraph(i).getSize(); j++) {
                System.out.print(graph.getNodeToGraph(i).getEdgeToNode(j).getNodeId() + " :" + graph.getNodeToGraph(i).getEdgeToNode(j).getWeight());
            }
            System.out.println();
        }
    }
}
