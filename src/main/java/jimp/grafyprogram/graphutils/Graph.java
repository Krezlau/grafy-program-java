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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (!(object instanceof Graph)) {
            return false;
        }

        Graph other = (Graph) object;

        if(collumns != other.collumns || rows != other.rows) {
            return false;
        }
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getNodeFromGraph(i).getSize(); j++) {
                if(this.getNodeFromGraph(i).getEdgeFromNode(j).getNodeId() != other.getNodeFromGraph(i).getEdgeFromNode(j).getNodeId()  || this.getNodeFromGraph(i).getEdgeFromNode(j).getWeight() != other.getNodeFromGraph(i).getEdgeFromNode(j).getWeight()) {
                    return false;
                }
            }
        }

        return true;
    }
}
