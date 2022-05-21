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
    //poczatek 0,255,0
    //srodek 0,255,255
    //koniec 0,0,255
    @Override
    public void print(){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);

        int nodeIndex = 0;

        double [] edgeRange = determineEdgeRange();

        // variables to use for gc.fillOval()
        double nodeX;
        double nodeY = 10;

        //variables determining actual circle center
        double nodeCenterX;
        double nodeCenterY = 10 + ovalSize/2;

        for (int row = 0; row < graph.getRows(); row++) {
            nodeX = 10;
            nodeCenterX = 10 + ovalSize/2;
            for (int col = 0; col < graph.getCollumns(); col++) {
                for (Edge edge : graph.getNodes().get(nodeIndex).getEdges()){
                    if (edge.getNodeId() == nodeIndex - 1){
                        gc.setFill(determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX - nodeSize, nodeCenterY - edgeSize/2, nodeSize, edgeSize);

                        // filling previous node oval in order for it to be on top
                        gc.setFill(Color.WHITE);
                        gc.fillOval(nodeX - nodeSize, nodeY, ovalSize, ovalSize);
                    }
                    if (edge.getNodeId() == nodeIndex + 1){
                        gc.setFill(determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX, nodeCenterY - edgeSize/2, nodeSize, edgeSize);
                    }
                    if (edge.getNodeId() == nodeIndex + graph.getCollumns()){
                        gc.setFill(determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX - edgeSize/2, nodeCenterY, edgeSize, nodeSize);
                    }
                    if (edge.getNodeId() == nodeIndex - graph.getCollumns()){
                        gc.setFill(determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX - edgeSize/2, nodeCenterY - nodeSize , edgeSize, nodeSize);

                        // filling previous node oval in order for it to be on top
                        gc.setFill(Color.WHITE);
                        gc.fillOval(nodeX, nodeY - nodeSize, ovalSize, ovalSize);
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

    private double [] determineEdgeRange(){
        double [] edgeRange = new double[2];
        edgeRange[0] = 999999999;
        edgeRange[1] = 0;

        for (Node node : graph.getNodes()){
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

    public Color determineColor(double value, double rangeStart, double rangeEnd){
        // start(255,0,0) -> stop1(255,255,0) -> stop2(0,255,0) -> stop3(0,255,255) -> end(0,0,255)
        double stop1 = (rangeEnd - rangeStart) / 4 + rangeStart;
        double stop2 = (rangeEnd - rangeStart) / 4 + stop1;
        double stop3 = (rangeEnd - rangeStart) / 4 + stop2;
        double ratio = 255 / ((rangeEnd - rangeStart) / 4);

        if (value <= stop1){
            return Color.rgb(255,
                            (int) ((value - rangeStart) * ratio),
                            0);
        }
        if (value <= stop2){
            return Color.rgb((int) ((value - stop2) * (-ratio)),
                            255,
                            0);
        }
        if (value <= stop3){
            return Color.rgb(0,
                            255,
                            (int) ((value - stop2) * ratio));
        }
        else{
            return Color.rgb(0,
                            (int) ((value - rangeEnd) * (-ratio)),
                            255);
        }
    }
}
