import java.util.ArrayList;
import java.util.List;

public class Vertex {
    /*
        g: cost between start vertex to current vertex.
        h: heuristic cost between current vertex to end vertex.
        f: cost between start vertex to end vertex => f(n) = g(n) + h(n).
     */
    private int f, h, g;
    private int x, y, style;

    private Vertex previous;
    private List<Edge> edges;
    private boolean isVisited;
    public boolean isMaze;

    /**
     * x: at col ... in the grid.
     * y: at row ... in the grid.
     * style: set color to the node
     * + default: -1
     */
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.previous = null;
        this.edges = new ArrayList<>();
        this.style = -1;
        this.g = Integer.MAX_VALUE;
        this.f = Integer.MAX_VALUE;
    }

    /**
     * @return x coordinate of the grid
     */
    public int getX() {
        return x;
    }

    /**
     * @return y coordinate of the grid
     */
    public int getY() {
        return y;
    }

    /**
     * @return the color of the grid
     */
    public int getStyle() {
        return style;
    }

    /**
     * Specify the color of the grid.
     *
     * @param style
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * @return the previous vertex.
     */
    public Vertex getPrevious() {
        return previous;
    }

    /**
     * Set the previous vertex.
     *
     * @param previous
     */
    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    /**
     * @return the list of edges of this vertex.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Check whether the vertex is visited or not.
     *
     * @return boolean
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Set the vertex be visited
     *
     * @param visited
     */
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    /**
     * @return G cost
     */
    public int getG() {
        return g;
    }

    /**
     * Set G cost for the vertex.
     *
     * @param g
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * @return F cost
     */
    public int getF() {
        return f;
    }

    /**
     * Set F cost for the vertex.
     *
     * @param f
     */
    public void setF(int f) {
        this.f = f;
    }

    /**
     * @return H cost
     */
    public int getH() {
        return h;
    }

    /**
     * Set H cost for the vertex.
     *
     * @param h
     */
    public void setH(int h) {
        this.h = h;
    }

}
