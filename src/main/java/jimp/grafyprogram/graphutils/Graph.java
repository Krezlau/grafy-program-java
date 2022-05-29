package jimp.grafyprogram.graphutils;

import java.util.ArrayList;
import java.util.Random;

public class Graph {

    private final int collumns;
    private final int rows;
    private final ArrayList<Node> nodes;

    public Graph(int c, int r){
        this.collumns = c;
        this.rows = r;
        nodes = new ArrayList<>(r*c);
    }

    public Graph(int c, int r, double weightStart, double weightEnd){
        this.collumns = c;
        this.rows = r;
        nodes = new ArrayList<>(r*c);

        Random rd = new Random();
        for (int i = 0; i < r*c; i++){
            ArrayList<Edge> tempEdges = new ArrayList<>();
            if ((i % c) != 0){
                tempEdges.add(new Edge(i - 1, rd.nextDouble(0,1)));
            }
            if (((i + 1) % c) != 0){
                tempEdges.add(new Edge(i + 1, rd.nextDouble(0,1)));
            }
            if ((i + c) < (c * r)){
                tempEdges.add(new Edge(i + c, rd.nextDouble(0,1)));
            }
            if ((i - c) >= 0){
                tempEdges.add(new Edge(i - c, rd.nextDouble(0,1)));
            }
            nodes.add(new Node(i, tempEdges));
        }
    }


    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public int getCollumns() {
        return collumns;
    }

    public int getRows() {
        return rows;
    }
    public void addNodeToGraph(int nodeId, Node item) {
        nodes.add(nodeId, item);
    }
    public Node getNodeFromGraph(int nodeId) {
        return nodes.get(nodeId);
    }

    public int getSize() {
        return nodes.size();
    }

}
