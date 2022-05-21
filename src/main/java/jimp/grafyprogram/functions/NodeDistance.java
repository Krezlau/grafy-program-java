package jimp.grafyprogram.functions;

public class NodeDistance implements Comparable<NodeDistance>{

    private final int nodeId;
    private final double distance;
    private final int precedingNodeId;

    public NodeDistance(int nodeId, double distance, int precedingNodeId){
        this.nodeId = nodeId;
        this.distance = distance;
        this.precedingNodeId = precedingNodeId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public double getDistance() {
        return distance;
    }

    public int getPrecedingNodeId() {
        return precedingNodeId;
    }

    @Override
    public int compareTo(NodeDistance o) {
        return Double.compare(this.distance, o.getDistance());
    }
}
