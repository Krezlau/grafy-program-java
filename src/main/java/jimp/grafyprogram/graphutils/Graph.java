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

    public double [] determineEdgeRange(){
        double [] edgeRange = new double[2];
        edgeRange[0] = 999999999;
        edgeRange[1] = 0;

        for (Node node : this.getNodes()){
            for (Edge edge : node.getEdges()){
                if (edge.getWeight() > edgeRange[1]){
                    edgeRange[1] = edge.getWeight();
                }
                if (edge.getWeight() < edgeRange[0]){
                    edgeRange[0] = edge.getWeight();
                }
            }
        }
        return edgeRange;
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
