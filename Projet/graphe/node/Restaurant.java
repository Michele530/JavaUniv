package Projet.graphe.node;

public class Restaurant extends LocationNode {

    private int preparationTime;

    public Restaurant(int id, String name, double x, double y, int preparationTime) {
        super(id, name, "Restaurant", x, y);
        this.preparationTime = preparationTime;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    @Override
    public String getLocationType() {
        return "Restaurant";
    }
}
