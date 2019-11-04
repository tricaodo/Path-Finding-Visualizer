public class Node {
    private int x;
    private int y;
    private int color;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        // color: -1 = white
        //         0 = green
        //         1 = red
        color = -1;
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
