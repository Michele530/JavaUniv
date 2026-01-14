package Projet.graph.Node;

import java.util.Objects;

public class NodeData {
    private final int id;
    private String name;
    private String desc;

    public NodeData(int id, String name, String desc) {
        this.id = id;
        this.name = name == null ? "" : name;
        this.desc = desc == null ? "" : desc;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDesc() { return desc; }

    public void setName(String name) { this.name = name == null ? "" : name; }
    public void setDesc(String desc) { this.desc = desc == null ? "" : desc; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeData)) return false;
        NodeData nodeData = (NodeData) o;
        return id == nodeData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NodeData{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
