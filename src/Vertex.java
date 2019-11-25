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
     *  + default: -1
     *  +
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

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

}
