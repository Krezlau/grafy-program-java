package jimp.grafyprogram.graphutils;

import java.util.ArrayList;

public class Node {

    private final int nodeId;
    private final ArrayList<Edge> edges;

    public Node(int nodeId, ArrayList<Edge> edges){
        this.nodeId = nodeId;
        this.edges = edges;
    }

    public Node(int id) {

        edges = new ArrayList<>();
        nodeId = id;
    }

    public int getNodeId() {
        return nodeId;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdgeToNode(int nodeId, Edge item) {
        if(nodeId <= 3) {
            edges.add(nodeId, item);
        }
        else {
            throw new RuntimeException("Wierzcholek ma za duzo krawedzi!");
        }

    }

    public Edge getEdgeFromNode(int nodeId) {
        return edges.get(nodeId);
    }
    public int getSize() {
        return edges.size();
    }

}
