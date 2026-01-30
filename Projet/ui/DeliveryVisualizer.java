package Projet.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import Projet.graphe.node.LocationNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryVisualizer extends Application {

    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50; // pixels
    private static List<LocationNode> pathToAnimate; // Chemin de livraison

    private Map<LocationNode, Rectangle> nodeRectangles = new HashMap<>();
    private Rectangle deliveryMarker;

    public static void setPath(List<LocationNode> path) {
        pathToAnimate = path;
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        // Créer la grille visuelle
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.BLACK);
                grid.add(cell, x, y);
            }
        }

        // Dessiner les nodes
        if (pathToAnimate != null) {
            for (LocationNode node : pathToAnimate) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(getColorForNode(node));
                rect.setStroke(Color.BLACK);
                grid.add(rect, (int) node.getX(), (int) node.getY());
                nodeRectangles.put(node, rect);
            }
        }

        // Marker pour la pizza
        deliveryMarker = new Rectangle(CELL_SIZE / 2.0, CELL_SIZE / 2.0, Color.ORANGE);
        if (pathToAnimate != null && !pathToAnimate.isEmpty()) {
            LocationNode start = pathToAnimate.get(0);
            deliveryMarker.setTranslateX(start.getX() * CELL_SIZE + CELL_SIZE / 4.0);
            deliveryMarker.setTranslateY(start.getY() * CELL_SIZE + CELL_SIZE / 4.0);
        }
        grid.getChildren().add(deliveryMarker);

        Scene scene = new Scene(grid, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        primaryStage.setTitle("SafeLogistic Delivery Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        animateDelivery();
    }

    private void animateDelivery() {
        if (pathToAnimate == null || pathToAnimate.size() < 2) return;

        Timeline timeline = new Timeline();
        int stepCount = pathToAnimate.size();

        for (int i = 1; i < stepCount; i++) {
            LocationNode node = pathToAnimate.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i), e -> {
                deliveryMarker.setTranslateX(node.getX() * CELL_SIZE + CELL_SIZE / 4.0);
                deliveryMarker.setTranslateY(node.getY() * CELL_SIZE + CELL_SIZE / 4.0);
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(1);
        timeline.play();
    }

    private Color getColorForNode(LocationNode node) {
        switch (node.getLocationType()) {
            case "Depot": return Color.BLUE;
            case "Restaurant": return Color.RED;
            case "Client": return Color.GREEN;
            default: return Color.GRAY;
        }
    }
}
