package jimp.grafyprogram.functions;

import jimp.grafyprogram.graphutils.*;

import java.util.*;

public class BfsSolver {

    protected Graph graph;

    protected boolean [] visited;

    public BfsSolver(Graph graph) {
        this.graph = graph;
    }



    public boolean solve() {
        visited = new boolean[graph.getRows() * graph.getCollumns()];
        Queue<Integer> queue = new LinkedList<>();
        int currentNodeId = 0;
        Arrays.fill(visited, false);
        visited[0] = true; //zaczynamy od 0 wezla

        for (int i = 0; i < graph.getNodeFromGraph(currentNodeId).getSize(); i++)
            queue.add(graph.getNodeFromGraph(currentNodeId).getEdgeFromNode(i).getNodeId());

        while(!queue.isEmpty()) {
            currentNodeId = queue.poll();
            if(!visited[currentNodeId]) {
                visited[currentNodeId] = true;
                for (int i = 0; i < graph.getNodeFromGraph(currentNodeId).getSize(); i++) {
                    if(!visited[graph.getNodeFromGraph(currentNodeId).getEdgeFromNode(i).getNodeId()] )
                        queue.add(graph.getNodeFromGraph(currentNodeId).getEdgeFromNode(i).getNodeId());
                }
            }
        }

        for (int i = 0; i < graph.getCollumns() * graph.getRows(); i++) {
            if(visited[i] == false) {
                return false;
            }
        }
        return true;
    }

}
