package Projet.test;

import Projet.graphe.node.Client;
import Projet.graphe.node.Depot;
import Projet.graphe.node.LocationNode;
import Projet.graphe.node.Restaurant;

public class TestLocationNode {

    public static void main(String[] args) {

        LocationNode a = new Restaurant(
                1,
                "PizzaTown",
                2.0,
                3.0,
                15
        );

        LocationNode b = new Client(
                2,
                "Alice",
                5.0,
                1.0,
                "12 rue du Mans"
        );

        LocationNode c = new Depot(
                3,
                "Depot Central",
                0.0,
                0.0,
                50
        );

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}

/*Résultats attendus :
Restaurant{id=1, name=PizzaTown, x=2.0, y=3.0}
Client{id=2, name=Alice, x=5.0, y=1.0}
Depot{id=3, name=Depot Central, x=0.0, y=0.0}
*/