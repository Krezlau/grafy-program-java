package jimp.grafyprogram.graphutils;

public class Edge {

    private final int nodeId;
    private final double weight;

    public Edge(int nodeId, double weight){
        this.nodeId = nodeId;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getNodeId() {
        return nodeId;
    }
}
