package Projet.graphe.node;

public class Client extends LocationNode {

    private String address;

    public Client(int id, String name, double x, double y, String address) {
        super(id, name, "Client", x, y);
        this.address = address == null ? "" : address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getLocationType() {
        return "Client";
    }
}
