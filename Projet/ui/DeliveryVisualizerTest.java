package Projet.ui;

import Projet.algo.Dijkstra;
import Projet.graphe.Edge;
import Projet.graphe.graphe_type.MixedGraph;
import Projet.graphe.node.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryVisualizerTest extends Application {

    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;

    // 0 = rien, -1 = dépôt, -2 = resto, -3 = client, >0 = poids route
    private int[][] tab = {
        {-1, 1, 2, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 3, 2, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 4, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 1, 1, 1, 3, 0, 0, 0},
        {1, 0, 0, 1, 0, 0, 2, 0, 0, 0},
        {1, 0, 0, -2, 1, 1, 5, 0, 0, 0},
        {1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
        {1, 1, 1, 1, 0, 0, -3, 0, 0, 0},
        {0, 0, 0, 2, 1, 1, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private Rectangle deliveryMarker;

    @Override
    public void start(Stage primaryStage) {

        // --- Création du graphe avec GenericLocation ---
        MixedGraph graph = new MixedGraph();
        Map<String, Node> nodesMap = new HashMap<>();

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                int val = tab[y][x];
                Node node = null;
                if (val == -1) node = new Depot(x * GRID_SIZE + y, "Depot", x, y, 0);
                else if (val == -2) node = new Restaurant(x * GRID_SIZE + y, "Resto", x, y, 0);
                else if (val == -3) node = new Client(x * GRID_SIZE + y, "Client", x, y, "Adresse");
                else if (val > 0) node = new GenericLocation(x * GRID_SIZE + y, "Route", x, y);

                if (node != null) {
                    graph.addNode(node);
                    nodesMap.put(x + "," + y, node);
                }
            }
        }

        // --- Ajouter les edges vers les voisins (haut, bas, gauche, droite) si poids >0 ---
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Node node = nodesMap.get(x + "," + y);
                if (node == null) continue;

                int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
                for (int[] d : dirs) {
                    int nx = x + d[0];
                    int ny = y + d[1];
                    if (nx >= 0 && nx < GRID_SIZE && ny >= 0 && ny < GRID_SIZE) {
                        Node neighbor = nodesMap.get(nx + "," + ny);
                        if (neighbor != null) {
                            int cost = tab[ny][nx] > 0 ? tab[ny][nx] : 1; // poids minimal 1
                            graph.addEdge(new Edge(node, neighbor, cost, false));
                        }
                    }
                }
            }
        }

        // --- Récupérer depot, resto, client ---
        Depot depot = null;
        Restaurant resto = null;
        Client client = null;
        for (Node node : graph.getNodes()) {
            if (node instanceof Depot) depot = (Depot) node;
            else if (node instanceof Restaurant) resto = (Restaurant) node;
            else if (node instanceof Client) client = (Client) node;
        }

        // --- Calcul du chemin avec Dijkstra ---
        Dijkstra d1 = new Dijkstra(graph, depot);
        d1.execute();
        List<Node> path1 = d1.getShortestPath(resto);

        Dijkstra d2 = new Dijkstra(graph, resto);
        d2.execute();
        List<Node> path2 = d2.getShortestPath(client);

        path1.addAll(path2.subList(1, path2.size()));

        // --- Création de la grille visuelle ---
        GridPane grid = new GridPane();
        Rectangle[][] cells = new Rectangle[GRID_SIZE][GRID_SIZE];
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                int val = tab[y][x];
                if (val == -1) cell.setFill(Color.BLUE);
                else if (val == -2) cell.setFill(Color.RED);
                else if (val == -3) cell.setFill(Color.GREEN);
                else if (val > 0) cell.setFill(Color.gray(Math.max(0.1, 1.0 - (val / 10.0))));
                else cell.setFill(Color.rgb(255, 255, 222));
                cell.setStroke(Color.BLACK);
                grid.add(cell, x, y);
                cells[y][x] = cell;
            }
        }

        // --- Marker livreur ---
        deliveryMarker = new Rectangle(CELL_SIZE*0.6, CELL_SIZE*0.6, Color.ORANGE);
        LocationNode startNode = (LocationNode) path1.get(0);
        deliveryMarker.setTranslateX(startNode.getX() * CELL_SIZE + CELL_SIZE * 0.2);
        deliveryMarker.setTranslateY(startNode.getY() * CELL_SIZE + CELL_SIZE * 0.2);
        grid.getChildren().add(deliveryMarker);

        Scene scene = new Scene(grid, GRID_SIZE*CELL_SIZE, GRID_SIZE*CELL_SIZE);
        primaryStage.setTitle("Delivery Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- Animation ---
        List<LocationNode> pathGL = path1.stream()
                .map(n -> (LocationNode) n)
                .toList();
        new Thread(() -> animateDelivery(pathGL)).start();

    }

    private void animateDelivery(List<LocationNode> path) {
        try {
            Thread.sleep(3000); // Attendre 3 secondes avant de commencer
            for (LocationNode node : path) {
                final double fx = node.getX();
                final double fy = node.getY();
                Platform.runLater(() -> {
                    deliveryMarker.setTranslateX(fx * CELL_SIZE + CELL_SIZE * 0.2);
                    deliveryMarker.setTranslateY(fy * CELL_SIZE + CELL_SIZE * 0.2);
                });
                System.out.println("Livreur sur : " + node);
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
