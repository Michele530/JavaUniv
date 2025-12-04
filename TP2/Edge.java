package TP2;

import java.util.Objects;


public class Edge {
    private final Node node1;
    private final Node node2;
    private int weight;
    private boolean oriented;
    private String desc; // facultatif 

    public Edge(Node n1, Node n2) {
        this(n1, n2, 1, false, "");
    }

    public Edge(Node n1, Node n2, Boolean orient) {
        this(n1, n2, 1, orient, "");
    }

    public Edge(Node n1, Node n2, int weight, boolean oriented, String desc) {
        if (n1 == null || n2 == null) throw new NullPointerException("Nodes cannot be null");
        if (n1.getId() == n2.getId()) throw new IllegalArgumentException("Self-loop not allowed (node1 == node2)");
        this.node1 = n1;
        this.node2 = n2;
        this.weight = weight;
        this.oriented = oriented;
        this.desc = desc == null ? "" : desc;
    }

    public Node getNode1() { return node1; }
    public Node getNode2() { return node2; }
    public int getWeight() { return weight; }
    public void addWeight(int poids) { this.weight += poids; }
    public void setWeight(int weight) { this.weight = weight; }
    public boolean isOriented() { return oriented; }
    public void setOrientation(boolean oriented) { this.oriented = oriented; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc == null ? "" : desc; }

    /**
     * True si l'arête relie a et b (respecte l'orientation de cette Edge).
     */
    public boolean connects(Node a, Node b) {
        if (a == null || b == null) return false;
        if (oriented) {
            return node1.equals(a) && node2.equals(b);
        } else {
            // unordered
            return (node1.equals(a) && node2.equals(b)) || (node1.equals(b) && node2.equals(a));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge other = (Edge) o;
        if (this.oriented != other.oriented) return false;
        if (this.oriented) {
            return this.node1.equals(other.node1) && this.node2.equals(other.node2);
        } else {
            return (this.node1.equals(other.node1) && this.node2.equals(other.node2))
                    || (this.node1.equals(other.node2) && this.node2.equals(other.node1));
        }
    }

    @Override
    public int hashCode() {
        if (oriented) {
            return Objects.hash(node1.getId(), node2.getId(), oriented);
        } else {
            int a = Math.min(node1.getId(), node2.getId());
            int b = Math.max(node1.getId(), node2.getId());
            return Objects.hash(a, b, oriented);
        }
    }

    @Override
    public String toString() {
        return "Edge{" + node1.getId() + (oriented ? "->" : "--") + node2.getId() + ", w=" + weight + "}";
    }
}
