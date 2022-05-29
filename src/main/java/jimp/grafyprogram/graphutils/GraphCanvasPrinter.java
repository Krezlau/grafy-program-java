package jimp.grafyprogram.graphutils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class GraphCanvasPrinter {

    private final Graph graph;
    private final Canvas graphCanvas;
    private final CanvasGraphCoordinatesDeterminer coordinatesDeterminer;
    private final ColorPicker colorPicker;


    public GraphCanvasPrinter(Graph graph, Canvas graphCanvas){
        this.graph = graph;
        this.graphCanvas = graphCanvas;

        coordinatesDeterminer = new CanvasGraphCoordinatesDeterminer(graph, graphCanvas);
        colorPicker = new ColorPicker();
    }


    public void print(){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);

        int nodeIndex = 0;

        double nodeSize = coordinatesDeterminer.getNodeSize();
        double ovalSize = coordinatesDeterminer.getOvalSize();
        double edgeSize = coordinatesDeterminer.getEdgeSize();

        double [] edgeRange = graph.determineEdgeRange();

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
                        gc.setFill(colorPicker.determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX - nodeSize, nodeCenterY - edgeSize/2, nodeSize, edgeSize);

                        // filling previous node oval in order for it to be on top
                        gc.setFill(Color.WHITE);
                        gc.fillOval(nodeX - nodeSize, nodeY, ovalSize, ovalSize);
                    }
                    if (edge.getNodeId() == nodeIndex + 1){
                        gc.setFill(colorPicker.determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX, nodeCenterY - edgeSize/2, nodeSize, edgeSize);
                    }
                    if (edge.getNodeId() == nodeIndex + graph.getCollumns()){
                        gc.setFill(colorPicker.determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
                        gc.fillRect(nodeCenterX - edgeSize/2, nodeCenterY, edgeSize, nodeSize);
                    }
                    if (edge.getNodeId() == nodeIndex - graph.getCollumns()){
                        gc.setFill(colorPicker.determineColor(edge.getWeight(), edgeRange[0], edgeRange[1]));
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
        return coordinatesDeterminer.findNode(X, Y);
    }

    public void paintNode(Node node, Color color){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(coordinatesDeterminer.getNodeX(node), coordinatesDeterminer.getNodeY(node),
                coordinatesDeterminer.getOvalSize(), coordinatesDeterminer.getOvalSize());
    }

    public void paintNode(Node node, double value, double rangeStart, double rangeEnd){
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(colorPicker.determineColor(value, rangeStart, rangeEnd));
        gc.fillOval(coordinatesDeterminer.getNodeX(node), coordinatesDeterminer.getNodeY(node),
                coordinatesDeterminer.getOvalSize(), coordinatesDeterminer.getOvalSize());
    }

    public Canvas getGraphCanvas() {
        return graphCanvas;
    }

    public CanvasGraphCoordinatesDeterminer getCoordinatesDeterminer() {
        return coordinatesDeterminer;
    }

    public Graph getGraph() {
        return graph;
    }
}
