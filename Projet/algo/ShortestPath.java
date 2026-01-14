package Projet.algo;

import java.util.*;

import Projet.graphe.Graph;
import Projet.graphe.node.Node;

public class ShortestPath extends GraphAlgorithm {
    private Node startNode;
    private Node endNode;

    public ShortestPath(Graph g, Node start, Node end) {
        super(g);
        this.startNode = start;
        this.endNode = end;
    }

    @Override
    public void execute() {
        if (startNode == null || endNode == null) {
            result = Collections.emptyList();
            return;
        }

        Map<Node, Node> prev = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        visited.add(startNode);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(endNode)) break;

            for (Node neighbour : graph.getNeighbours(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    prev.put(neighbour, current);
                }
            }
        }

        // reconstruire le chemin
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (current != null && prev.containsKey(current)) {
            path.add(0, current);
            current = prev.get(current);
        }
        if (current != null && current.equals(startNode)) path.add(0, startNode);

        result = path;
    }

    @SuppressWarnings("unchecked")
    public List<Node> getPath() {
        return (List<Node>) result;
    }
}
