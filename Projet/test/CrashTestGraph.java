package Projet.test;

import java.util.List;
import java.util.Map;

import Projet.algo.BFS;
import Projet.algo.DFS;
import Projet.algo.Dijkstra;
import Projet.graphe.Edge;
import Projet.graphe.Graph;
import Projet.graphe.graphe_type.MixedGraph;
import Projet.graphe.node.Node;

// Deuxième phase de tests
public class CrashTestGraph {
    public static void main(String[] args) {
        Graph g = new MixedGraph();  // Graphe MIXED
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
            g.addEdge(n1, nX, 1, "", false);
            System.out.println("Test 3 : FAILED (arête avec nœud absent acceptée)");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 3 : PASSED (arête avec nœud absent rejetée)");
        }

        // --- Test 4 : Ajouter une arête normale via Edge ---
        Edge e1 = new Edge(n1, n2, 1, false, "");
        result = ((MixedGraph) g).addEdge(e1);
        System.out.println("Test 4 : " + (result ? "PASSED" : "FAILED") + " (arête A-B ajoutée)");

        // --- Test 5 : Ajouter la même arête encore une fois (poids incrémenté) ---
        Edge e1dup = new Edge(n1, n2, 2, false, "");
        ((MixedGraph) g).addEdge(e1dup);
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
        g.addEdge(n2, n3, 1, "", true);   // orientée
        g.addEdge(n1, n3, 1, "", false);  // non orientée

        result = g.getEdges().stream().anyMatch(e -> e.connects(n2, n3) && e.isOriented()) &&
                 g.getEdges().stream().anyMatch(e -> e.connects(n1, n3) && !e.isOriented());
        System.out.println("Test 9 : " + (result ? "PASSED" : "FAILED") + " (arêtes orientées et non orientées ajoutées)");
        g.printEdges();

        // =======================
        // --- Test 10 : Grand graphe pour DFS / BFS ---
        // =======================
        System.out.println("\nTest 10 : Grand graphe DFS / BFS");

        Graph g2 = new MixedGraph();

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

        // Connexions avec la bonne surcharge (Node, weight, name, oriented)
        g2.addEdge(a, b, 10, "", false);
        g2.addEdge(a, c, 5, "", false);
        g2.addEdge(b, d, 50, "", false);
        g2.addEdge(b, e, 150, "", false);
        g2.addEdge(c, f, 999, "", false);
        g2.addEdge(a, f, 30, "", false);
        g2.addEdge(e, f, 65, "", false);
        g2.addEdge(e, c, 1, "", false);
        g2.addEdge(f, gNode, 70, "", false);
        g2.addEdge(gNode, h, 1, "", false);

        // Affichage
        g2.printNodes();
        g2.printEdges();

        // --- DFS ---
        DFS dfs = new DFS(g2, a);
        dfs.execute();
        System.out.println("DFS ordre de visite :");
        for (Node n : dfs.getVisitOrder()) System.out.print(n.getId() + " ");
        System.out.println();

        // --- BFS ---
        BFS bfs = new BFS(g2, a);
        bfs.execute();
        System.out.println("BFS ordre de visite :");
        for (Node n : bfs.getVisitOrder()) System.out.print(n.getId() + " ");
        System.out.println();

        // --- Dijkstra ---
        Dijkstra dijkstra = new Dijkstra(g2, a);
        dijkstra.execute();

        System.out.println("Distances depuis A :");
        for (Map.Entry<Node, Integer> entry : dijkstra.getDistances().entrySet()) {
            System.out.println(entry.getKey().getId() + " -> " + entry.getValue());
        }

        List<Node> path = dijkstra.getShortestPath(h);
        System.out.println("Chemin le plus court A -> H :");

        int total = 0;
        for (int i = 0; i < path.size(); i++) {
            Node current = path.get(i);
            System.out.print(current.getId());
            if (i < path.size() - 1) {
                Node next = path.get(i + 1);
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
