package Projet.app;

import Projet.algo.Dijkstra;
import Projet.graphe.Graph;
import Projet.graphe.node.Client;
import Projet.graphe.node.Depot;
import Projet.graphe.node.Restaurant;
import Projet.graphe.node.Node;

import java.util.List;
import java.util.Map;

public class DeliveryManager {

    private Graph graph;

    public DeliveryManager(Graph graph) {
        this.graph = graph;
    }

    /**
     * Planifie une livraison complète :
     * 1. Depot -> Routes -> Restaurant 
     * 2. Restaurant -> Routes -> Client
     */
    public void planDelivery(Depot start, Restaurant pickup, Client dropoff) {

        // --- Étape 1 : Depot -> Restaurant ---
        Dijkstra dijkstra1 = new Dijkstra(graph, start);
        dijkstra1.execute();
        List<Node> route1 = dijkstra1.getShortestPath(pickup);
        Map<Node, Integer> distances1 = dijkstra1.getDistances();
        int cost1 = distances1.getOrDefault(pickup, Integer.MAX_VALUE);

        // --- Étape 2 : Restaurant -> Client ---
        Dijkstra dijkstra2 = new Dijkstra(graph, pickup);
        dijkstra2.execute();
        List<Node> route2 = dijkstra2.getShortestPath(dropoff);
        Map<Node, Integer> distances2 = dijkstra2.getDistances();
        int cost2 = distances2.getOrDefault(dropoff, Integer.MAX_VALUE);

        // --- Rapport de livraison ---
        System.out.println("=== Livraison planifiée ===\n");

        System.out.println("Etape 1 : Depot -> Restaurant");
        route1.forEach(node -> System.out.println("  - "+node));
        System.out.println("Cout : "+cost1+ "\n");

        System.out.println("Etape 2 : Restaurant -> Client");
        route2.forEach(node -> System.out.println("  - "+node));
        System.out.println("Cout : "+cost2+"\n");

        System.out.println("Cout total : "+(cost1+cost2));
    }
}
