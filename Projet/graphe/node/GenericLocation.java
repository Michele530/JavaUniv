package Projet.graphe.node;

public class GenericLocation extends LocationNode {

    public GenericLocation(int id, String name, double x, double y) {
        super(id, name, "", x, y); // <- utilise le constructeur existant
    }

    @Override
    public String getLocationType() {
        return "Generic";
    }

    @Override
    public String toString() {
        return "GenericLocation{id=" + getData().getId() +
               ", name=" + getData().getName() +
               ", x=" + getX() + ", y=" + getY() + "}";
    }
}
