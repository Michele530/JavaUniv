package Projet.algo;

import java.util.*;

import Projet.graph.Graph;
import Projet.graph.Node.Node;

public class BFS extends GraphAlgorithm {
    private Node startNode;

    public BFS(Graph g, Node start) {
        super(g);
        this.startNode = start;
    }
    
    @Override
    public void execute() {
        if (startNode == null) return;
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        List<Node> visitOrder = new ArrayList<>();

        visited.add(startNode);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visitOrder.add(current);
            for (Node neighbour : graph.getNeighbours(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        result = visitOrder;
    }

    @SuppressWarnings("unchecked")
    public List<Node> getVisitOrder() {
        return (List<Node>) result;
    }
}
