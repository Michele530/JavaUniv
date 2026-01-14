package Projet.test;

import java.util.List;

import Projet.algo.BFS;
import Projet.algo.DFS;
import Projet.algo.ShortestPath;
import Projet.graphe.Graph;
import Projet.graphe.graphe_type.UndirectedGraph; 
import Projet.graphe.node.Node;

// Première phase de tests
public class TestGraphAlgo {
    public static void main(String[] args) {
        // Au lieu de "new Graph(...)", on utilise la sous-classe
        Graph g = new UndirectedGraph();

        // --- Création des nœuds ---
        Node[] nodes = new Node[15];
        for (int i = 0; i < 15; i++) {
            nodes[i] = new Node(i + 1, "N" + (i + 1));
            g.addNode(nodes[i]);
        }

        // --- Ajout d'arêtes pour former un graphe complexe ---
        int[][] edges = {
            {1,2},{1,3},{2,4},{2,5},{3,6},{3,7},{4,8},{5,8},{6,9},{7,9},
            {8,10},{9,10},{10,11},{11,12},{11,13},{12,14},{13,15},{14,15},
            {5,6},{7,12},{2,13},{3,14},{4,15}
        };

        for (int[] e : edges) {
            g.addEdge(nodes[e[0]-1], nodes[e[1]-1]);
        }

        // --- Affichage du graphe ---
        System.out.println("=== Graphe non orienté complexe ===");
        g.printNodes();
        g.printEdges();

        // --- Voisins et degré de chaque nœud ---
        for (Node n : g.getNodes()) {
            g.printNeighbours(n);
            g.printDegree(n);
        }

        // --- DFS à partir de N1 ---
        DFS dfs = new DFS(g, nodes[0]);
        dfs.execute();
        List<Node> dfsOrder = dfs.getVisitOrder();
        System.out.print("DFS visit order: ");
        for (Node n : dfsOrder) System.out.print(n.getId() + " ");
        System.out.println();

        // --- BFS à partir de N1 ---
        BFS bfs = new BFS(g, nodes[0]);
        bfs.execute();
        List<Node> bfsOrder = bfs.getVisitOrder();
        System.out.print("BFS visit order: ");
        for (Node n : bfsOrder) System.out.print(n.getId() + " ");
        System.out.println();

        // --- Plus court chemin entre N1 et N15 ---
        ShortestPath sp = new ShortestPath(g, nodes[0], nodes[14]);
        sp.execute();
        List<Node> path = sp.getPath();
        System.out.print("Shortest path from N1 to N15: ");
        for (Node n : path) System.out.print(n.getId() + " ");
        System.out.println();
    }
}
