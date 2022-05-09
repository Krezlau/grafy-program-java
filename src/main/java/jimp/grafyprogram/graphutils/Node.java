package jimp.grafyprogram.graphutils;

import java.util.ArrayList;

public class Node {

    private final int nodeId;
    private final ArrayList<Edge> edges;

    public Node(int nodeId, ArrayList<Edge> edges){
        this.nodeId = nodeId;
        this.edges = edges;
    }

    public int getNodeId() {
        return nodeId;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}
