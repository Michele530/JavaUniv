package TP2;

public abstract class GraphAlgorithm {
    protected Graph graph;   // Le graphe sur lequel l'algorithme va s'exécuter
    protected Object result; // Résultat générique, chaque algo peut l'utiliser à sa façon

    public GraphAlgorithm(Graph g) {
        if (g == null) throw new IllegalArgumentException("Graph cannot be null");
        this.graph = g;
    }

    // Méthode principale à implémenter pour chaque algorithme
    public abstract void execute();

    // Permet de récupérer le résultat de l'algorithme
    public Object getResult() {
        return result;
    }
}
