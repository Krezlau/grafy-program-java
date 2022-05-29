package jimp.grafyprogram.functions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import jimp.grafyprogram.graphutils.CanvasGraphCoordinatesDeterminer;
import jimp.grafyprogram.graphutils.Graph;
import jimp.grafyprogram.graphutils.GraphCanvasPrinter;
import jimp.grafyprogram.graphutils.Node;

public class DijkstraCanvasPrinter extends Dijkstra{

    private final GraphCanvasPrinter graphCanvasPrinter;

    public DijkstraCanvasPrinter(Node start, GraphCanvasPrinter graphCanvasPrinter){
        this.graphCanvasPrinter = graphCanvasPrinter;
        this.start = start;
        this.graph = graphCanvasPrinter.getGraph();
        solve();
    }

    @Override
    public void print(Node end) {
        Node current = end;
        Node next;

        Canvas graphCanvas = graphCanvasPrinter.getGraphCanvas();
        CanvasGraphCoordinatesDeterminer coordinatesDeterminer = graphCanvasPrinter.getCoordinatesDeterminer();

        double pathSize = coordinatesDeterminer.getNodeSize() / 2;
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.PURPLE);

        gc.fillOval(coordinatesDeterminer.getNodeCenterX(current) - pathSize/2,
                coordinatesDeterminer.getNodeCenterY(current) - pathSize/2,
                pathSize,
                pathSize);

        while (current != start){
            next = graph.getNodes().get(distances[current.getNodeId()].getPrecedingNodeId());

            if (next.getNodeId() == current.getNodeId() + 1){
                gc.fillRect(coordinatesDeterminer.getNodeCenterX(current),
                        coordinatesDeterminer.getNodeCenterY(current) - pathSize/2,
                        coordinatesDeterminer.getNodeSize(),
                        pathSize);
            }
            if (next.getNodeId() == current.getNodeId() - 1){
                gc.fillRect(coordinatesDeterminer.getNodeCenterX(next),
                        coordinatesDeterminer.getNodeCenterY(next) - pathSize/2,
                        coordinatesDeterminer.getNodeSize(),
                        pathSize);
            }
            if (next.getNodeId() == current.getNodeId() + graph.getCollumns()){
                gc.fillRect(coordinatesDeterminer.getNodeCenterX(current) - pathSize/2,
                        coordinatesDeterminer.getNodeCenterY(current),
                        pathSize,
                        coordinatesDeterminer.getNodeSize());
            }
            if (next.getNodeId() == current.getNodeId() - graph.getCollumns()){
                gc.fillRect(coordinatesDeterminer.getNodeCenterX(next) - pathSize/2,
                        coordinatesDeterminer.getNodeCenterY(next),
                        pathSize,
                        coordinatesDeterminer.getNodeSize());
            }

            gc.fillOval(coordinatesDeterminer.getNodeCenterX(next) - pathSize/2,
                    coordinatesDeterminer.getNodeCenterY(next) - pathSize/2,
                    pathSize,
                    pathSize);


            current = graph.getNodes().get(distances[current.getNodeId()].getPrecedingNodeId());
        }
    }

    public void makeGradient(){
        double max = findMaxDistance();

        for (NodeDistance nodeDistance : distances){
                graphCanvasPrinter.paintNode(graph.getNodes().get(nodeDistance.getNodeId()),
                        nodeDistance.getDistance(), 0, max);
        }
    }

    private double findMaxDistance(){
        double max = 0;
        for (NodeDistance nodeDistance : distances){
            if (nodeDistance.getDistance() > max){
                max = nodeDistance.getDistance();
            }
        }
        return max;
    }
}
