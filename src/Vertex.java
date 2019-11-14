import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int x;
    private int y;
    private Vertex previous;
    private int style;
    private List<Edge> edges;
    private boolean isVisited;

    /**
     * x: at row ... in the grid.
     * y: at col ... in the grid.
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
}
