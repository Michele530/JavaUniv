package Projet.graphe.node;

public abstract class LocationNode extends Node {

    // Coordonnées (pour un usage 2D)
    private double x;
    private double y;

    /* ===== CONSTRUCTEURS ===== */

    public LocationNode(NodeData data, double x, double y) {
        super(data);
        this.x = x;
        this.y = y;
    }

    public LocationNode(int id, String name, String desc, double x, double y) {
        super(id, name, desc);
        this.x = x;
        this.y = y;
    }

    /* ===== GETTERS / SETTERS ===== */

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setDesc(String desc) {
        this.getData().setDesc(desc);
    }

    public String getDesc() {
        return this.getData().getDesc();
    }
    
    public String getName() {
        return this.getData().getName();
    }



    /* ===== POLYMORPHISME ===== */

    /**
     * Chaque lieu doit expliciter son type
     */
    public abstract String getLocationType();

    @Override
    public String toString(){
        return getLocationType() + "{id=" + getId()
                + ", name=" + getData().getName()
                + ", x=" + x
                + ", y=" + y + "}";
    }
}
