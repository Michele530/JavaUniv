package Projet.test;

import Projet.app.DeliveryManager;
import Projet.graphe.Edge;
import Projet.graphe.graphe_type.MixedGraph;
import Projet.graphe.node.Client;
import Projet.graphe.node.Depot;
import Projet.graphe.node.GenericLocation;
import Projet.graphe.node.Restaurant;

public class DeliveryManagerTest {

    public static void main(String[] args) {

        // --- Création d’un graphe mixte pour simuler une vraie circulation (double voie + voie à sens unique) ---
        MixedGraph graph = new MixedGraph();

        // --- Création des nodes principaux ---
        Depot depot = new Depot(1, "Depot Central", 0, 0, 50);
        Restaurant resto = new Restaurant(2, "PizzaTown", 2, 3, 15);
        Client client = new Client(3, "Alice", 5, 1, "12 rue du Mans");

        // --- Nodes urbaines intermédiaires ---
        GenericLocation rondPoint = new GenericLocation(4, "Rond-Point", 3, 2);
        GenericLocation lotissement = new GenericLocation(5, "Lotissement", 4, 4);
        GenericLocation carrefour = new GenericLocation(6, "Carrefour", 1, 5);


        // --- Ajouter tous les nodes dans le graphe ---
        graph.addNode(depot);
        graph.addNode(resto);
        graph.addNode(client);
        graph.addNode(rondPoint);
        graph.addNode(lotissement);
        graph.addNode(carrefour);

        // --- Ajouter des edges et la distance (poids) ---
        graph.addEdge(new Edge(depot, carrefour, 2, false));
        graph.addEdge(new Edge(carrefour, resto, 3, false));
        graph.addEdge(new Edge(resto, lotissement, 2, false));
        graph.addEdge(new Edge(lotissement, client, 4, false));
        graph.addEdge(new Edge(depot, rondPoint, 3, false));
        graph.addEdge(new Edge(rondPoint, lotissement, 2, false));
        graph.addEdge(new Edge(carrefour, lotissement, 2, true)); // sens unique

        // --- Test de la livraison ---
        DeliveryManager manager = new DeliveryManager(graph);
        manager.planDelivery(depot, resto, client);
    }
}

/*Résultats attendus :

=== Livraison planifiée ===

Etape 1 : Depot -> Restaurant
  - Depot{id=1, name=Depot Central, x=0.0, y=0.0}
  - GenericLocation{id=6, name=Carrefour, x=1.0, y=5.0}
  - Restaurant{id=2, name=PizzaTown, x=2.0, y=3.0}
Cout : 5

Etape 2 : Restaurant -> Client
  - Restaurant{id=2, name=PizzaTown, x=2.0, y=3.0}
  - GenericLocation{id=5, name=Lotissement, x=4.0, y=4.0}
  - Client{id=3, name=Alice, x=5.0, y=1.0}
Cout : 6

Cout total : 11

*/