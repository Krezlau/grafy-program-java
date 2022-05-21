package jimp.grafyprogram.functions;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import jimp.grafyprogram.graphutils.Graph;
import jimp.grafyprogram.graphutils.GraphCanvasPrinter;
import jimp.grafyprogram.graphutils.Node;

public class DijkstraCanvasPrinter extends Dijkstra{

    private final Canvas graphCanvas;

    public DijkstraCanvasPrinter(Graph graph, Canvas graphCanvas, Node start){
        this.graph = graph;
        this.graphCanvas = graphCanvas;
        this.start = start;
        solve();
    }

    @Override
    public void print(Node end) {
        GraphCanvasPrinter gcp = new GraphCanvasPrinter(graph, graphCanvas);
        Node current = end;
        Node next;

        double pathSize = gcp.getNodeSize() / 2;
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.setFill(Color.PURPLE);

        gc.fillOval(gcp.getNodeCenterX(current) - pathSize/2, gcp.getNodeCenterY(current) - pathSize/2, pathSize, pathSize);

        while (current != start){
            next = graph.getNodes().get(distances[current.getNodeId()].getPrecedingNodeId());

            if (next.getNodeId() == current.getNodeId() + 1){
                gc.fillRect(gcp.getNodeCenterX(current), gcp.getNodeCenterY(current) - pathSize/2, gcp.getNodeSize(), pathSize);
            }
            if (next.getNodeId() == current.getNodeId() - 1){
                gc.fillRect(gcp.getNodeCenterX(next), gcp.getNodeCenterY(next) - pathSize/2, gcp.getNodeSize(), pathSize);
            }
            if (next.getNodeId() == current.getNodeId() + graph.getCollumns()){
                gc.fillRect(gcp.getNodeCenterX(current) - pathSize/2, gcp.getNodeCenterY(current), pathSize, gcp.getNodeSize());
            }
            if (next.getNodeId() == current.getNodeId() - graph.getCollumns()){
                gc.fillRect(gcp.getNodeCenterX(next) - pathSize/2, gcp.getNodeCenterY(next), pathSize, gcp.getNodeSize());
            }

            gc.fillOval(gcp.getNodeCenterX(next) - pathSize/2, gcp.getNodeCenterY(next) - pathSize/2, pathSize, pathSize);


            current = graph.getNodes().get(distances[current.getNodeId()].getPrecedingNodeId());
        }
    }

    public void makeGradient(){
        GraphCanvasPrinter gcp = new GraphCanvasPrinter(graph, graphCanvas);
        double max = findMaxDistance();

        for (NodeDistance nodeDistance : distances){
                gcp.paintNode(graph.getNodes().get(nodeDistance.getNodeId()),
                        gcp.determineColor(nodeDistance.getDistance(), 0, max));
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
