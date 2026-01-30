package Projet.graphe.node;

public class Depot extends LocationNode {

    private int capacity;

    public Depot(int id, String name, double x, double y, int capacity) {
        super(id, name, "Depot", x, y);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getLocationType() {
        return "Depot";
    }
}
