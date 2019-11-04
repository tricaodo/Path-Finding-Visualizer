public class Node {
    private int x;
    private int y;
    private int row;
    private int col;
    private int color;

    /**
     *  color: -1 = white
     *          0 = green
     *          1 = red
     */
    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        color = -1;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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


}
