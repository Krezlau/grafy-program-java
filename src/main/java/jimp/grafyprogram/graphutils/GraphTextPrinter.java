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
    public GraphTextPrinter(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void print() throws IOException {
        FileWriter output = new FileWriter(filePath);
        output.write(String.format("%d %d\n",graph.getRows(),graph.getCollumns()));

        for (int i = 0; i < graph.getSize(); i++) {
            output.write("     ");

            for (int j = 0; j < graph.getNodeFromGraph(i).getSize(); j++) {
                output.write(String.format("%d :%.16f  ", graph.getNodeFromGraph(i).getEdgeFromNode(j).getNodeId(), graph.getNodeFromGraph(i).getEdgeFromNode(j).getWeight()));
            }
            output.write("\n");
        }
        output.close();
    }

    //probne wypisywanie zawartosci grafu do konsoli
    public void printToConsoll() {
        System.out.printf("%d %d\n%n",graph.getRows(),graph.getCollumns());
        for (int i = 0; i < graph.getSize(); i++) {
            System.out.println("     ");
            for (int j = 0; j < graph.getNodeFromGraph(i).getSize(); j++) {
                System.out.printf("%d :%.2",graph.getNodeFromGraph(i).getEdgeFromNode(j).getNodeId(), graph.getNodeFromGraph(i).getEdgeFromNode(j).getWeight());
            }
            System.out.println();
        }
    }

}
