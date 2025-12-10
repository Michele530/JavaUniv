package TP2;

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
    }
}
