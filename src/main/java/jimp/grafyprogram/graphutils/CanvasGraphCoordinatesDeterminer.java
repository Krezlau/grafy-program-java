package jimp.grafyprogram.graphutils;

import javafx.scene.canvas.Canvas;

public class CanvasGraphCoordinatesDeterminer {

    private final double nodeSize;
    private final double ovalSize;
    private final double edgeSize;
    private final Graph graph;

    public CanvasGraphCoordinatesDeterminer(Graph graph, Canvas graphCanvas){
        if ((graphCanvas.getHeight() - 20) / (graph.getRows() - 1) < (graphCanvas.getWidth() - 20) / (graph.getCollumns() + 1)){
            this.nodeSize = (graphCanvas.getHeight() - 20) / (graph.getRows() + 1);
        }
        else{
            this.nodeSize = (graphCanvas.getWidth() - 20) / (graph.getCollumns() + 1);
        }
        this.ovalSize = nodeSize / 1.2;
        this.edgeSize = nodeSize / 5;
        this.graph = graph;
    }

    public Node findNode(double X, double Y){
        double nodeX = 10;
        double nodeY = 10;

        int col = -1;
        //finding if x is valid
        for (int i = 0; i < graph.getCollumns(); i++){
            if (X - nodeX <= ovalSize && X - nodeX >= 0){
                col = i;
                break;
            }
            nodeX += nodeSize;
        }
        if (col == -1){
            return null;
        }

        int row = -1;
        //finding if y is valid
        for (int i = 0; i < graph.getRows(); i++){
            if (Y - nodeY <= ovalSize && Y - nodeY >= 0){
                row = i;
                break;
            }
            nodeY += nodeSize;
        }
        if (row == -1){
            return null;
        }

        return graph.getNodes().get(row * graph.getCollumns() + col);
    }

    public double getNodeCenterX(Node node) {
        int row = node.getNodeId() / graph.getCollumns();
        int col = node.getNodeId() - (row * graph.getCollumns());

        return col * nodeSize + 10 + ovalSize / 2;
    }

    public double getNodeCenterY(Node node){
        int row = node.getNodeId() / graph.getCollumns();
        int col = node.getNodeId() - (row * graph.getCollumns());

        return row * nodeSize + 10 + ovalSize/2;
    }

    public double getNodeX(Node node){
        return getNodeCenterX(node) - ovalSize / 2;
    }

    public double getNodeY(Node node){
        return getNodeCenterY(node) - ovalSize / 2;
    }

    public double getOvalSize() {
        return ovalSize;
    }

    public double getNodeSize() {
        return nodeSize;
    }

    public double getEdgeSize() {
        return edgeSize;
    }
}
