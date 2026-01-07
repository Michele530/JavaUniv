package TP2;

import java.util.List;
import java.util.Map;

// Crash tests (normalement tout se passe bien)
public class CrashTestGraph {
    public static void main(String[] args) {
        Graph g = new Graph(OrientationType.MIXED);
        boolean result;

        // --- Test 1 : Ajouter un nœud null ---
        try {
            g.addNode(null);
            System.out.println("Test 1 : FAILED (ajout d'un nœud null accepté)");
        } catch (NullPointerException e) {
            System.out.println("Test 1 : PASSED (ajout d'un nœud null rejeté)");
        }

        // --- Test 2 : Ajouter des nœuds normaux ---
        Node n1 = new Node(1, "A");
        Node n2 = new Node(2, "B");
        Node n3 = new Node(3, "C");
        result = g.addNode(n1) && g.addNode(n2) && g.addNode(n3);
        System.out.println("Test 2 : " + (result ? "PASSED" : "FAILED") + " (ajout de nœuds normaux)");

        // --- Test 3 : Ajouter une arête avec un nœud absent ---
        try {
            Node nX = new Node(99, "X");
            g.addEdge(n1, nX);
            System.out.println("Test 3 : FAILED (arête avec nœud absent acceptée)");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 3 : PASSED (arête avec nœud absent rejetée)");
        }

        // --- Test 4 : Ajouter une arête normale ---
        Edge e1 = new Edge(n1, n2, 1, false, "");
        result = g.addEdge(e1);
        System.out.println("Test 4 : " + (result ? "PASSED" : "FAILED") + " (arête A-B ajoutée)");

        // --- Test 5 : Ajouter la même arête encore une fois (poids incrémenté) ---
        Edge e1dup = new Edge(n1, n2, 2, false, "");
        g.addEdge(e1dup);
        result = e1.getWeight() == 3;
        System.out.println("Test 5 : " + (result ? "PASSED" : "FAILED") + " (poids de l'arête A-B = " + e1.getWeight() + ")");

        // --- Test 6 : Supprimer une arête inexistante ---
        Node nX = new Node(99, "X");
        result = !g.delEdge(n1, nX);
        System.out.println("Test 6 : " + (result ? "PASSED" : "FAILED") + " (suppression d'arête inexistante)");

        // --- Test 7 : Supprimer un nœud inexistant ---
        result = !g.delNode(nX);
        System.out.println("Test 7 : " + (result ? "PASSED" : "FAILED") + " (suppression de nœud inexistant)");

        // --- Test 8 : Vérifier voisins et degré après manipulations ---
        System.out.println("Test 8 : Vérification voisins et degrés");
        g.printNodes();
        g.printEdges();
        for (Node n : g.getNodes()) {
            g.printNeighbours(n);
            g.printDegree(n);
        }

        // --- Test 9 : Ajouter arêtes orientées et non orientées ---
        g.addEdge(n2, n3, true);
        g.addEdge(n1, n3, false);

        result = g.getEdges().stream().anyMatch(e -> e.connects(n2, n3) && e.isOriented()) &&
                 g.getEdges().stream().anyMatch(e -> e.connects(n1, n3) && !e.isOriented());
        System.out.println("Test 9 : " + (result ? "PASSED" : "FAILED") + " (arêtes orientées et non orientées ajoutées)");
        g.printEdges();

        // =======================
        // --- Test 10 : Grand graphe pour DFS / BFS ---
        // =======================
        System.out.println("\nTest 10 : Grand graphe DFS / BFS");

        Graph g2 = new Graph(OrientationType.MIXED);

        // Création des nœuds
        Node a = new Node(1, "A");
        Node b = new Node(2, "B");
        Node c = new Node(3, "C");
        Node d = new Node(4, "D");
        Node e = new Node(5, "E");
        Node f = new Node(6, "F");
        Node gNode = new Node(7, "G");
        Node h = new Node(8, "H");

        // Ajout des nœuds
        g2.addNode(a);
        g2.addNode(b);
        g2.addNode(c);
        g2.addNode(d);
        g2.addNode(e);
        g2.addNode(f);
        g2.addNode(gNode);
        g2.addNode(h);

        // Connexions (graphe avec branches + cycle)
        g2.addEdge(a, b, false, 10);
        g2.addEdge(a, c, false, 5);
        g2.addEdge(b, d, false, 50);
        g2.addEdge(b, e, false, 150);
        g2.addEdge(c, f, false, 999);
        g2.addEdge(a, f, false, 30);
        g2.addEdge(e, f, false, 65); 
        g2.addEdge(e, c, false, 1); 
        g2.addEdge(f, gNode, false, 70);
        g2.addEdge(gNode, h, false, 1);

        // Affichage
        g2.printNodes();
        g2.printEdges();
        
        // --- DFS ---
        DFS dfs = new DFS(g2, a);
        dfs.execute();
        System.out.println("DFS ordre de visite :");
        for (Node n : dfs.getVisitOrder()) {
            System.out.print(n.getId() + " ");
        }
        System.out.println();

        // --- BFS ---
        BFS bfs = new BFS(g2, a);
        bfs.execute();
        System.out.println("BFS ordre de visite :");
        for (Node n : bfs.getVisitOrder()) {
            System.out.print(n.getId() + " ");
        }
        System.out.println();

        // --- Dijkstra ---
        Dijkstra dijkstra = new Dijkstra(g2, a);
        dijkstra.execute();

        System.out.println("Distances depuis A :");
        for (Map.Entry<Node, Integer> entry : dijkstra.getDistances().entrySet()) {
            System.out.println(entry.getKey().getId()+ " -> " + entry.getValue());
        }

        List<Node> path = dijkstra.getShortestPath(h);
        System.out.println("Chemin le plus court A -> H :");

        int total = 0;

        for (int i = 0; i < path.size(); i++) {
            Node current = path.get(i);
            System.out.print(current.getId());

            if (i < path.size() - 1) {
                Node next = path.get(i + 1);

                // retrouver l'arête entre current et next
                Edge edgeUsed = null;
                for (Edge ed : g2.getEdges()) {
                    if (ed.connects(current, next)) {
                        edgeUsed = ed;
                        break;
                    }
                }

                if (edgeUsed != null) {
                    int w = edgeUsed.getWeight();
                    total += w;
                    System.out.print(" --" + w + "--> ");
                } else {
                    System.out.print(" --> ");
                }
            }
        }

        System.out.println("\nPoids total = " + total);

    }
}
