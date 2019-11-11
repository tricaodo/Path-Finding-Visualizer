public class Vertex {
    private int x;
    private int y;
    private int style;

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
}
