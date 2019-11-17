import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int x, y, style, cost;
    /*
        g: cost between start vertex to current vertex.
        h: heuristic cost between current vertex to end vertex.
        f: cost between start vertex to end vertex => f(n) = g(n) + h(n).
     */
    private int f, h, g;

    private Vertex previous;
    private List<Edge> edges;
    private boolean isVisited;

    /**
     * x: at col ... in the grid.
     * y: at row ... in the grid.
     * style: set color to the node
     *  + default: -1
     *  +
     */
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.previous = null;
        this.edges = new ArrayList<>();
        this.style = -1;
        this.cost = Integer.MAX_VALUE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
