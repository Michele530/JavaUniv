package Projet.graphe;

import java.util.*;

import Projet.graphe.node.Node;

public abstract class Graph {
    protected final Map<Integer, Node> nodesById = new HashMap<>(); // key, Node
    protected final Set<Edge> edges = new HashSet<>();
    protected final Map<Integer, Map<Integer, Edge>> edgeMap = new HashMap<>();
    protected final Map<Integer, Set<Edge>> adjacency = new HashMap<>();

    // Classes abstraite pour ajouter une arête
    public abstract boolean addEdge(Node n1, Node n2, int weight, String name, boolean oriented);
    public abstract boolean addEdge(Node n1, Node n2, int weightn, String name);
    public abstract boolean addEdge(Node n1, Node n2, int weight);
    public abstract boolean addEdge(Node n1, Node n2);
    public abstract boolean addEdge(Edge e);

    public abstract List<Node> getNeighbours(Node n);
    
    public abstract int getDegree(Node n);

    public abstract String getType();
    public Collection<Node> getNodes() { return Collections.unmodifiableCollection(nodesById.values()); }
    public Collection<Edge> getEdges() { return Collections.unmodifiableCollection(edges); }
    public Optional<Node> getNodeById(int id) { return Optional.ofNullable(nodesById.get(id)); }

    public boolean addNode(Node n) {
        if (n == null) throw new NullPointerException("Node cannot be null");
        if (nodesById.containsKey(n.getId())) return false;
        nodesById.put(n.getId(), n);
        adjacency.putIfAbsent(n.getId(), new HashSet<>());
        return true;
    }

    public boolean delNode(Node n) {
        if (n == null || !nodesById.containsKey(n.getId())) return false;

        Set<Edge> incident = new HashSet<>(adjacency.getOrDefault(n.getId(), Collections.emptySet()));
        for (Edge e : incident) {
            delEdge(e);
        }

        nodesById.remove(n.getId());
        adjacency.remove(n.getId());
        return true;
    }

    public boolean delEdge(Edge e) {
        if (e == null) return false;
        if (!edges.remove(e)) return false;

        int id1 = e.getNode1().getId();
        int id2 = e.getNode2().getId();

        // Supprimer de adjacency
        Set<Edge> s1 = adjacency.get(id1);
        if (s1 != null) s1.remove(e);
        Set<Edge> s2 = adjacency.get(id2);
        if (s2 != null) s2.remove(e);

        // Supprimer de edgeMap
        Map<Integer, Edge> map1 = edgeMap.get(id1);
        if (map1 != null) map1.remove(id2);
        if (!e.isOriented()) {
            Map<Integer, Edge> map2 = edgeMap.get(id2);
            if (map2 != null) map2.remove(id1);
        }

        return true;
    }

    public boolean delEdge(Node n1, Node n2) {
        if (n1 == null || n2 == null) return false;

        Map<Integer, Edge> map1 = edgeMap.get(n1.getId());
        if (map1 != null) {
            Edge e = map1.get(n2.getId());
            if (e != null) return delEdge(e);
        }

        // fallback si edgeMap ne contient pas l'arête
        for (Edge e : new HashSet<>(edges)) {
            if (e.connects(n1, n2)) {
                return delEdge(e);
            }
        }
        return false;
    }

    public List<Edge> getIncidentEdges(Node n) {
        if (n == null) return Collections.emptyList();
        Set<Edge> s = adjacency.get(n.getId());
        if (s == null) return Collections.emptyList();
        return new ArrayList<>(s);
    }

    public void clear() {
        edges.clear();
        adjacency.clear();
        nodesById.clear();
        edgeMap.clear();
    }

    public void rebuildAdjacency() {
        adjacency.clear();
        edgeMap.clear();
        for (Integer id : nodesById.keySet()) adjacency.put(id, new HashSet<>());

        for (Edge e : edges) {
            int id1 = e.getNode1().getId();
            int id2 = e.getNode2().getId();

            adjacency.get(id1).add(e);
            adjacency.get(id2).add(e);

            edgeMap.putIfAbsent(id1, new HashMap<>());
            edgeMap.get(id1).put(id2, e);
            if (!e.isOriented()) {
                edgeMap.putIfAbsent(id2, new HashMap<>());
                edgeMap.get(id2).put(id1, e);
            }
        }
    }

    // utilitaires
    public int getNbNodes() { return nodesById.size(); }
    public int getNbEdges() { return edges.size(); }

    // Affiche tous les nœuds du graphe
    public void printNodes() {
        System.out.print("Nœuds du graphe: ");
        for (Node n : nodesById.values()) {
            System.out.print(n.getId() + " ");
        }
        System.out.println();
    }

    // Affiche le nombre total de nœuds
    public void printNbNodes() {
        System.out.println("Nombre de nœuds: " + getNbNodes());
    }

    // Affiche le nombre total d'arêtes
    public void printNbEdges() {
        System.out.println("Nombre d'arêtes: " + getNbEdges());
    }

    // Affiche les voisins d'un nœud
    public void printNeighbours(Node n) {
        if (n == null || !nodesById.containsKey(n.getId())) {
            System.out.println("Nœud inexistant.");
            return;
        }
        List<Node> neighbours = getNeighbours(n);
        System.out.print("Voisins de " + n.getId() + ": ");
        for (Node nb : neighbours) {
            System.out.print(nb.getId() + " ");
        }
        System.out.println();
    }

    // Affiche le degré d'un nœud
    public void printDegree(Node n) {
        if (n == null || !nodesById.containsKey(n.getId())) {
            System.out.println("Nœud inexistant.");
            return;
        }
        System.out.println("Degré de " + n.getId() + ": " + getDegree(n));
    }

    // Affiche toutes les arêtes du graphe
    public void printEdges() {
        System.out.println("Arêtes du graphe:");
        for (Edge e : edges) {
            String type = e.isOriented() ? "->" : "--";
            System.out.println(e.getNode1().getId() + " " + type + " " + e.getNode2().getId() + " (poids=" + e.getWeight() + ")");
        }
    }

}
