package jimp.grafyprogram.functions;

import javafx.scene.canvas.Canvas;
import jimp.grafyprogram.graphutils.Graph;
import jimp.grafyprogram.graphutils.Node;

public class DijkstraConsolePrinter extends Dijkstra{

    public DijkstraConsolePrinter(Graph graph, Node start){
        this.graph = graph;
        this.start = start;
        solve();
    }
    @Override
    public void print(Node end) {
        Node current = end;

        while (current != start){
            System.out.print(current.getNodeId() + " <- ");
            current = graph.getNodes().get(distances[current.getNodeId()].getPrecedingNodeId());
        }
        System.out.print(start.getNodeId());
    }
}
