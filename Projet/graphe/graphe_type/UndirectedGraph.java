package Projet.graphe.graphe_type;

import java.util.*;

import Projet.graphe.Edge;
import Projet.graphe.Graph;
import Projet.graphe.node.Node;

public class UndirectedGraph extends Graph {

    @Override
    public String getType() {
        return "Undirected Graph";
    }

    @Override
    public boolean addEdge(Node n1, Node n2, int weight, String name, boolean oriented) {
        if (n1 == null || n2 == null || !nodesById.containsKey(n1.getId()) || !nodesById.containsKey(n2.getId()))
            throw new IllegalArgumentException("Both nodes must exist in the graph");

        if (oriented) throw new IllegalArgumentException("UndirectedGraph cannot have oriented edges");

        // Vérifier si l'arête existe déjà
        Edge existing = edgeMap.getOrDefault(n1.getId(), Map.of()).get(n2.getId());
        if (existing != null) {
            existing.addWeight(weight);
            return true;
        }

        Edge e = new Edge(n1, n2, weight, false, name);

        edges.add(e);

        adjacency.putIfAbsent(n1.getId(), new HashSet<>());
        adjacency.putIfAbsent(n2.getId(), new HashSet<>());
        adjacency.get(n1.getId()).add(e);
        adjacency.get(n2.getId()).add(e);

        edgeMap.putIfAbsent(n1.getId(), new HashMap<>());
        edgeMap.get(n1.getId()).put(n2.getId(), e);
        edgeMap.putIfAbsent(n2.getId(), new HashMap<>());
        edgeMap.get(n2.getId()).put(n1.getId(), e);

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
            neighbours.add(e.getNode1().equals(n) ? e.getNode2() : e.getNode1());
        }
        return new ArrayList<>(neighbours);
    }

    @Override
    public int getDegree(Node n) {
        return adjacency.getOrDefault(n.getId(), Collections.emptySet()).size();
    }

    @Override
    public boolean addEdge(Edge e) {
        if (e == null) throw new NullPointerException("Edge cannot be null");
        if (e.isOriented()) throw new IllegalArgumentException("UndirectedGraph n'accepte que des arêtes non orientées");
        return addEdge(e.getNode1(), e.getNode2(), e.getWeight(), e.getDesc(), false);
    }
}