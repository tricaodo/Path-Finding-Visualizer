public class Edge {
    private int weight;
    private Vertex destination;

    public Edge(int weight, Vertex destination) {
        this.weight = weight;
        this.destination = destination;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return the destination vertex
     */
    public Vertex getDestination() {
        return destination;
    }
}
