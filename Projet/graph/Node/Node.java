package Projet.graph.Node;

import java.util.Objects;

public class Node {
    private NodeData data;

    // Constructeur à partir des datas 
    public Node(NodeData data) {
        this.data = data;
    }

    // Constructeur en définissant la data
    public Node(int id) {
        this.data = new NodeData(id, "", "");
    }

    // Constructeur en définissant la data
    public Node(int id, String name) {
        this.data = new NodeData(id, name, "");
    }

    // Constructeur en définissant la data
    public Node(int id, String name, String desc) {
        this.data = new NodeData(id, name, desc);
    }

    // Fonctions get et set
    public NodeData getData() { return data; }
    public void setData(NodeData data) { this.data = data; }
    public int getId() { return data.getId(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // si le même objet
        if (!(o instanceof Node)) return false; // Si pas une instance de Node
        Node node = (Node) o; // Transforme l'objet en Node
        return getId() == node.getId(); // Check l'id de la node fournit avec l'ID de la node actuelle 
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId()); // permet de tester l'égalité via l'ID
    }

    @Override
    public String toString() {
        return "Node{" + data.toString() + "}"; // Converti les data en texte
    }
}
