package Projet.algo;

import java.util.*;

import Projet.graphe.Edge;
import Projet.graphe.Graph;
import Projet.graphe.node.Node;

public class Dijkstra extends GraphAlgorithm {

    private Node startNode;
    private Map<Node, Integer> distances;
    private Map<Node, Node> previous;

    public Dijkstra(Graph g, Node start) {
        super(g);
        this.startNode = start;
    }

    @Override
    public void execute() {
        if (startNode == null) return;

        distances = new HashMap<>();
        previous = new HashMap<>();

        // Initialisation
        for (Node n : graph.getNodes()) {
            distances.put(n, Integer.MAX_VALUE);
            previous.put(n, null);
        }
        distances.put(startNode, 0);

        // File de priorité (min distance)
        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(distances::get)
        );
        pq.add(startNode);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            int currentDist = distances.get(current);

            for (Edge e : graph.getEdges()) {

                Node neighbour = null;

                // Respect orientation
                if (e.isOriented()) {
                    if (e.getNode1().equals(current)) {
                        neighbour = e.getNode2();
                    }
                } else {
                    if (e.getNode1().equals(current)) {
                        neighbour = e.getNode2();
                    } else if (e.getNode2().equals(current)) {
                        neighbour = e.getNode1();
                    }
                }

                if (neighbour == null) continue;

                int newDist = currentDist + e.getWeight();

                if (newDist < distances.get(neighbour)) {
                    distances.put(neighbour, newDist);
                    previous.put(neighbour, current);
                    pq.add(neighbour);
                }
            }
        }

        result = distances;
    }

    @SuppressWarnings("unchecked")
    public Map<Node, Integer> getDistances() {
        return (Map<Node, Integer>) result;
    }

    public List<Node> getShortestPath(Node target) {
        List<Node> path = new ArrayList<>();
        if (!distances.containsKey(target)
                || distances.get(target) == Integer.MAX_VALUE) {
            return path;
        }

        for (Node at = target; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
