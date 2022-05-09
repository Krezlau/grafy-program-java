package jimp.grafyprogram.graphutils;

import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class GraphCanvasPrinter extends GraphPrinter{

    private final Canvas graphCanvas;
    private final double nodeSize;
    private final double ovalSize;
    private final double edgeSize;

    public GraphCanvasPrinter(Graph graph, Canvas graphCanvas){
        this.graph = graph;
        this.graphCanvas = graphCanvas;

        if ((graphCanvas.getHeight() - 20) / (graph.getRows() - 1) < (graphCanvas.getWidth() - 20) / (graph.getCollumns() + 1)){
            this.nodeSize = (graphCanvas.getHeight() - 20) / (graph.getRows() + 1);
        }
        else{
            this.nodeSize = (graphCanvas.getWidth() - 20) / (graph.getCollumns() + 1);
        }
        this.ovalSize = nodeSize / 1.2;
        this.edgeSize = nodeSize / 5;
    }

    //TODO gradient
    @Override
    public void print(){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);

        int nodeIndex = 0;

        // variables to use for gc.fillOval()
        double nodeX;
        double nodeY = 10;

        //variables determining acutal circle center
        double nodeCenterX;
        double nodeCenterY = 10 + ovalSize/2;

        for (int row = 0; row < graph.getRows(); row++) {
            nodeX = 10;
            nodeCenterX = 10 + ovalSize/2;
            for (int col = 0; col < graph.getCollumns(); col++) {
                gc.setFill(Color.RED);
                for (Edge edge : graph.getNodes().get(nodeIndex).getEdges()){
                    if (edge.getNodeId() == nodeIndex - 1){
                        gc.fillRect(nodeCenterX - nodeSize, nodeCenterY - edgeSize/2, nodeSize, edgeSize);

                        // filling previous node oval in order for it to be on top
                        gc.setFill(Color.WHITE);
                        gc.fillOval(nodeX - nodeSize, nodeY, ovalSize, ovalSize);
                        gc.setFill(Color.RED);
                    }
                    if (edge.getNodeId() == nodeIndex + 1){
                        gc.fillRect(nodeCenterX, nodeCenterY - edgeSize/2, nodeSize, edgeSize);
                    }
                    if (edge.getNodeId() == nodeIndex + graph.getCollumns()){
                        gc.fillRect(nodeCenterX - edgeSize/2, nodeCenterY, edgeSize, nodeSize);
                    }
                    if (edge.getNodeId() == nodeIndex - graph.getCollumns()){
                        gc.fillRect(nodeCenterX - edgeSize/2, nodeCenterY - nodeSize , edgeSize, nodeSize);

                        // filling previous node oval in order for it to be on top
                        gc.setFill(Color.WHITE);
                        gc.fillOval(nodeX, nodeY - nodeSize, ovalSize, ovalSize);
                        gc.setFill(Color.RED);
                    }
                }

                gc.setFill(Color.WHITE);
                gc.fillOval(nodeX, nodeY, ovalSize, ovalSize);
                nodeIndex++;
                nodeX += nodeSize;
                nodeCenterX += nodeSize;
            }
            nodeY += nodeSize;
            nodeCenterY += nodeSize;
        }
    }

    public Node selectNode(double X, double Y){
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

    public void paintNode(Node node, Color color){
        int row = node.getNodeId() / graph.getCollumns();
        int col = node.getNodeId() - (row * graph.getCollumns());

        double nodeX = col * nodeSize + 10;
        double nodeY = row * nodeSize + 10;

        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(nodeX, nodeY, ovalSize, ovalSize);
    }

    public void bleachSelectedNodes(ObservableList<Node> selectedNodes){
        for (Node node : selectedNodes){
            paintNode(node, Color.WHITE);
        }
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

    public double getNodeSize() {
        return nodeSize;
    }

}
