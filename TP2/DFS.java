package TP2;

import java.util.*;

public class DFS extends GraphAlgorithm {
    private Node startNode;

    public DFS(Graph g, Node start) {
        super(g);
        this.startNode = start;
    }

    @Override
    public void execute() {
        if (startNode == null) return;
        Set<Node> visited = new HashSet<>();
        List<Node> visitOrder = new ArrayList<>();
        dfsHelper(startNode, visited, visitOrder);
        
        result = visitOrder; // stockage dans result
    }

    private void dfsHelper(Node current, Set<Node> visited, List<Node> visitOrder) {
        visited.add(current);
        visitOrder.add(current);
        for (Node neighbour : graph.getNeighbours(current)) {
            if (!visited.contains(neighbour)) {
                dfsHelper(neighbour, visited, visitOrder);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Node> getVisitOrder() {
        return (List<Node>) result;
    }
}
