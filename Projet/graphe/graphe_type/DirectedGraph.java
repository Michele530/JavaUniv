package Projet.graphe.graphe_type;

import java.util.*;

import Projet.graphe.Edge;
import Projet.graphe.Graph;
import Projet.graphe.node.Node;

public class DirectedGraph extends Graph {

    @Override
    public String getType() {
        return "Directed Graph";
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight, String name, boolean oriented) {
        if (!oriented) throw new IllegalArgumentException("DirectedGraph ne peut contenir que des arêtes orientées");

        Edge e = new Edge(n1, n2, weight, true, name);
        edges.add(e);

        adjacency.putIfAbsent(n1.getId(), new HashSet<>());
        adjacency.putIfAbsent(n2.getId(), new HashSet<>());
        adjacency.get(n1.getId()).add(e);
        adjacency.get(n2.getId()).add(e);

        edgeMap.putIfAbsent(n1.getId(), new HashMap<>());
        edgeMap.get(n1.getId()).put(n2.getId(), e);

        return true;
    }

    // Versions courtes appellent la version complète
    @Override
    public boolean addEdge(Node n1, Node n2) {
        return addEdge(n1, n2, 1, "", true);
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight) {
        return addEdge(n1, n2, weight, "", true);
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight, String name) {
        return addEdge(n1, n2, weight, name, true);
    }

    @Override
    public List<Node> getNeighbours(Node n) {
        Set<Node> neighbours = new HashSet<>();
        for (Edge e : adjacency.getOrDefault(n.getId(), Collections.emptySet())) {
            if (e.getNode1().equals(n)) {
                neighbours.add(e.getNode2());
            }
        }
        return new ArrayList<>(neighbours);
    }

    @Override
    public int getDegree(Node n) {
        int in = 0, out = 0;
        for (Edge e : adjacency.getOrDefault(n.getId(), Collections.emptySet())) {
            if (e.getNode1().equals(n)) out++;
            else if (e.getNode2().equals(n)) in++;
        }
        return in + out;
    }

    @Override
    public boolean addEdge(Edge e) {
        if (e == null) throw new NullPointerException("Edge cannot be null");
        if (!e.isOriented()) throw new IllegalArgumentException("DirectedGraph n'accepte que des arêtes orientées");
        return addEdge(e.getNode1(), e.getNode2(), e.getWeight(), e.getDesc(), true);
    }
}
