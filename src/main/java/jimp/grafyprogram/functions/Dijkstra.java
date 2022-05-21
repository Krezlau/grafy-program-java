package jimp.grafyprogram.functions;


import jimp.grafyprogram.graphutils.Edge;
import jimp.grafyprogram.graphutils.Graph;
import jimp.grafyprogram.graphutils.Node;

import java.util.*;

abstract public class Dijkstra {
    protected Graph graph;
    protected NodeDistance [] distances;
    protected boolean [] ifVisited;
    protected Node start;

    protected void solve(){
        distances = new NodeDistance[graph.getCollumns() * graph.getRows()];
        ifVisited = new boolean[graph.getCollumns() * graph.getRows()];
        for (int i = 0; i < graph.getRows() * graph.getCollumns(); i++){
            if (i != start.getNodeId()){
                distances[i] = new NodeDistance(i,99999999, -1);
                ifVisited[i] = false;
            }
            else{
                distances[i] = new NodeDistance(i, 0, -1);
                ifVisited[i] = true;
            }
        }

        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(NodeDistance::compareTo);
        NodeDistance temp;

        for (Edge edge : start.getEdges()){
            temp = new NodeDistance(edge.getNodeId(), edge.getWeight(), start.getNodeId());
            priorityQueue.add(temp);
            distances[temp.getNodeId()] = temp;
        }

        NodeDistance current;

        while (!priorityQueue.isEmpty()){
            current = priorityQueue.poll();
            ifVisited[current.getNodeId()] = true;

            for (Edge edge : graph.getNodes().get(current.getNodeId()).getEdges()){
                if (!ifVisited[edge.getNodeId()]) {
                    temp = new NodeDistance(edge.getNodeId(), edge.getWeight() + current.getDistance(), current.getNodeId());
                    if (temp.getDistance() < distances[temp.getNodeId()].getDistance()) {
                        priorityQueue.add(temp);
                        distances[temp.getNodeId()] = temp;
                    }
                }
            }
        }

    }

    abstract public void print(Node end);
}
