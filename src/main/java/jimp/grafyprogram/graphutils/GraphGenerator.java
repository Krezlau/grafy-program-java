package jimp.grafyprogram.graphutils;

import java.util.ArrayList;
import java.util.Random;

public class GraphGenerator {

    public Graph generate(int c, int r, double start, double end){
        Graph graph = new Graph(c, r);

        Random rd = new Random();
        for (int i = 0; i < r*c; i++){
            ArrayList<Edge> tempEdges = new ArrayList<>();
            if ((i % c) != 0){
                tempEdges.add(new Edge(i - 1, rd.nextDouble(start, end)));
            }
            if (((i + 1) % c) != 0){
                tempEdges.add(new Edge(i + 1, rd.nextDouble(start, end)));
            }
            if ((i + c) < (c * r)){
                tempEdges.add(new Edge(i + c, rd.nextDouble(start, end)));
            }
            if ((i - c) >= 0){
                tempEdges.add(new Edge(i - c, rd.nextDouble(start, end)));
            }
            graph.getNodes().add(new Node(i, tempEdges));
        }
        return graph;
    }
}
