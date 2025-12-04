package TP2;

import java.util.*;

public class Graph {
    private OrientationType type;
    private final Map<Integer, Node> nodesById = new HashMap<>(); // key, Node
    private final Set<Edge> edges = new HashSet<>();
    private final Map<Integer, Set<Edge>> adjacency = new HashMap<>();

    // Constructeur
    public Graph() { this(OrientationType.UNDIRECTED); } // Crée un graphe non orienté par défaut
    public Graph(OrientationType type) { this.type = type; } // 3 types : DIRECTED, UNDIRECTED, MIXED


    public OrientationType getType() { return type; }

    // Warning si jamais on change un graphe qui contient un truc qu'il est pas sensé contenir
    // Exemple : On change le type en orienté alors qu'il y a une arête non orienté = warning (mais fonctionne)
    public void setType(OrientationType newType) {
        if (this.type == newType) return;

        if (newType == OrientationType.UNDIRECTED) {
            boolean hasOriented = edges.stream().anyMatch(Edge::isOriented); // Pour chaque edge, on check si elle est orientée
            if (hasOriented) {
                System.err.println("Warning: le graphe contient des arêtes orientées, mais vous passez en UNDIRECTED (non orientée). Aucune conversion automatique ne sera faite.");
            }
        } else if (newType == OrientationType.DIRECTED) {
            boolean hasNonOriented = edges.stream().anyMatch(e -> !e.isOriented()); // Pour chaque edge, on check si elle est non orientée
            if (hasNonOriented) {
                System.err.println("Warning: le graphe contient des arêtes non orientées, mais vous passez en DIRECTED (orientée). Aucune conversion automatique ne sera faite.");
            }
        }

        /* Un système de conversion pourrait être mis en place,
           mais je ne juge pas cela nécessaire puisqu'on est rarerement amené à changé le type global d'un graphe.*/

        // Assignation du nouveau type
        this.type = newType;
    }



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
        if (n == null) return false;
        if (!nodesById.containsKey(n.getId())) return false;
        // enlève les arêtes incidentes
        Set<Edge> incident = new HashSet<>(adjacency.getOrDefault(n.getId(), Collections.emptySet()));
        for (Edge e : incident) {
            delEdge(e);
        }
        nodesById.remove(n.getId());
        adjacency.remove(n.getId());
        return true;
    }

    public boolean addEdge(Edge e) {
        if (e == null) throw new NullPointerException("Edge cannot be null");
        if (!nodesById.containsKey(e.getNode1().getId()) || !nodesById.containsKey(e.getNode2().getId())) {
            throw new IllegalArgumentException("Both nodes must be present in the graph before adding an edge");
        }

        // Chercher une arête existante qui correspond
        for (Edge existing : edges) {
            if (existing.connects(e.getNode1(), e.getNode2()) && existing.isOriented() == e.isOriented()) {
                existing.addWeight(e.getWeight()); // incrémente le poids
                return true; // on ne crée pas de nouvelle arête
            }
        }

        edges.add(e);
        adjacency.get(e.getNode1().getId()).add(e);
        adjacency.get(e.getNode2().getId()).add(e);
        return true;
    }

    public boolean addEdge(Node n1, Node n2) {
        return addEdge(n1, n2, false);
    }

    public boolean addEdge(Node n1, Node n2, boolean oriented) {
        Edge e = new Edge(n1, n2, 1, oriented, "");
        return addEdge(e);
    }

    public boolean delEdge(Edge e) {
        if (e == null) return false;
        if (!edges.remove(e)) return false;
        Set<Edge> s1 = adjacency.get(e.getNode1().getId());
        if (s1 != null) s1.remove(e);
        Set<Edge> s2 = adjacency.get(e.getNode2().getId());
        if (s2 != null) s2.remove(e);
        return true;
    }

    public boolean delEdge(Node n1, Node n2) {
        if (n1 == null || n2 == null) return false;
        Edge found = null;
        for (Edge e : edges) {
            if (e.connects(n1, n2)) {
                found = e;
                break;
            }
        }
        return found != null && delEdge(found);
    }

    public List<Edge> getIncidentEdges(Node n) {
        if (n == null) return Collections.emptyList();
        Set<Edge> s = adjacency.get(n.getId());
        if (s == null) return Collections.emptyList();
        return new ArrayList<>(s);
    }

    public List<Node> getNeighbours(Node n) {
        if (n == null) return Collections.emptyList();
        Set<Node> neighbours = new HashSet<>();
        for (Edge e : adjacency.getOrDefault(n.getId(), Collections.emptySet())) {
            if (!e.isOriented()) {
                neighbours.add(e.getNode1().equals(n) ? e.getNode2() : e.getNode1());
            } else {
                if (e.getNode1().equals(n)) {
                    neighbours.add(e.getNode2());
                } else if (type != OrientationType.DIRECTED) {
                    neighbours.add(e.getNode1()); 
                }
            }
        }
        return new ArrayList<>(neighbours);
    }

    public int getDegree(Node n) {
        if (n == null) return 0;
        if (type == OrientationType.UNDIRECTED) {
            return adjacency.getOrDefault(n.getId(), Collections.emptySet()).size();
        } else {
            int total = 0;
            for (Edge e : edges) {
                if (e.isOriented()) {
                    if (e.getNode1().equals(n) || e.getNode2().equals(n)) total++;
                } else {
                    if (e.getNode1().equals(n) || e.getNode2().equals(n)) total++;
                }
            }
            return total;
        }
    }

    public void clear() {
        edges.clear();
        adjacency.clear();
        nodesById.clear();
    }

    private void rebuildAdjacency() {
        adjacency.clear();
        for (Integer id : nodesById.keySet()) adjacency.put(id, new HashSet<>());
        for (Edge e : edges) {
            adjacency.get(e.getNode1().getId()).add(e);
            adjacency.get(e.getNode2().getId()).add(e);
        }
    }

    // utilitaires
    public int getNbNodes() { return nodesById.size(); }
    public int getNbEdges() { return edges.size(); }
}
