package Projet.graphe.graphe_type;

import java.util.*;

import Projet.graphe.Edge;
import Projet.graphe.Graph;
import Projet.graphe.node.Node;

public class MixedGraph extends Graph {

    @Override
    public String getType() {
        return "Mixed Graph";
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight, String name, boolean oriented) {
        Edge e = new Edge(n1, n2, weight, oriented, name);
        edges.add(e);

        adjacency.putIfAbsent(n1.getId(), new HashSet<>());
        adjacency.putIfAbsent(n2.getId(), new HashSet<>());
        adjacency.get(n1.getId()).add(e);
        adjacency.get(n2.getId()).add(e);

        edgeMap.putIfAbsent(n1.getId(), new HashMap<>());
        edgeMap.get(n1.getId()).put(n2.getId(), e);
        if (!oriented) {
            edgeMap.putIfAbsent(n2.getId(), new HashMap<>());
            edgeMap.get(n2.getId()).put(n1.getId(), e);
        }

        return true;
    }

    @Override
    public boolean addEdge(Node n1, Node n2) {
        return addEdge(n1, n2, 1, "", false);
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight) {
        return addEdge(n1, n2, weight, "", false);
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight, String name) {
        return addEdge(n1, n2, weight, name, false);
    }

    @Override
    public List<Node> getNeighbours(Node n) {
        Set<Node> neighbours = new HashSet<>();
        for (Edge e : adjacency.getOrDefault(n.getId(), Collections.emptySet())) {
            if (!e.isOriented()) {
                neighbours.add(e.getNode1().equals(n) ? e.getNode2() : e.getNode1());
            } else if (e.getNode1().equals(n)) {
                neighbours.add(e.getNode2());
            }
        }
        return new ArrayList<>(neighbours);
    }

    @Override
    public int getDegree(Node n) {
        int deg = 0;
        for (Edge e : adjacency.getOrDefault(n.getId(), Collections.emptySet())) {
            if (!e.isOriented() || e.getNode1().equals(n) || e.getNode2().equals(n)) deg++;
        }
        return deg;
    }

    @Override
    public boolean addEdge(Edge e) {
        if (e == null) throw new NullPointerException("Edge cannot be null");
        return addEdge(e.getNode1(), e.getNode2(), e.getWeight(), e.getDesc(), e.isOriented());
    }
}
