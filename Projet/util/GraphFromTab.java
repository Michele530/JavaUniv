package Projet.util;

import Projet.graphe.Edge;
import Projet.graphe.graphe_type.MixedGraph;
import Projet.graphe.node.*;

public class GraphFromTab {

    public static MixedGraph createGraphFromTab(int[][] tab) {
        int rows = tab.length;
        int cols = tab[0].length;
        MixedGraph graph = new MixedGraph();

        // --- Première passe : créer tous les nodes ---
        Node[][] nodes = new Node[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int val = tab[y][x];
                Node node;
                switch (val) {
                    case -1: node = new Depot(y*cols + x, "Depot", x, y, 0); break;
                    case -2: node = new Restaurant(y*cols + x, "Restaurant", x, y, 0); break;
                    case -3: node = new Client(y*cols + x, "Client", x, y, "Adresse"); break;
                    default: 
                        if (val > 0) node = new GenericLocation(y*cols + x, "Route", x, y);
                        else node = null; // 0 = rien
                }
                nodes[y][x] = node;
                if (node != null) graph.addNode(node);
            }
        }

        // --- Deuxième passe : créer les edges 4-voisins ---
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Node node = nodes[y][x];
                if (node == null) continue;
                for (int d = 0; d < 4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];
                    if (nx < 0 || nx >= cols || ny < 0 || ny >= rows) continue;
                    Node neighbor = nodes[ny][nx];
                    if (neighbor == null) continue;
                    int weight = Math.max(1, tab[ny][nx]); // poids minimal 1
                    graph.addEdge(new Edge(node, neighbor, weight, false)); // sens bidirectionnel
                }
            }
        }

        return graph;
    }
}
