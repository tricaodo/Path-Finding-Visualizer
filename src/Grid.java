import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Grid extends JPanel implements MouseListener {
    private Node startNode;
    private Node endNode;

    private final int delay = 30;

    private final int DIMENSION = 30;
    private final int ROWS = 460 / DIMENSION; // height
    private final int COLS = 660 / DIMENSION; // width
    private final Node[][] grids;

    public Grid() {
        grids = new Node[COLS][ROWS];
        init();
        addMouseListener(this);
    }

    public void init() {
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row] = new Node(col, row);
            }
        }
        startNode = grids[0][0];
        startNode.setColor(0);
        endNode = grids[21][14];
        endNode.setColor(1);
    }

    public void start(){
        bfs();
    }

    public void paint(Graphics g) {
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                if (grids[col][row].getColor() == -1) {
                    g.setColor(Color.WHITE);
                } else if (grids[col][row].getColor() == 0) {
                    g.setColor(Color.GREEN);
                } else if (grids[col][row].getColor() == 1) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.orange);
                }
                g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                g.setColor(Color.BLACK);
                g.drawRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
            }
        }
    }

    private void bfs() {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> set = new HashSet<>();
        queue.offer(startNode);
        set.add(startNode);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            // finishing
            if (curr == endNode) {
                System.out.println("Done");
            }

            int currRow = curr.getRow();
            int currCol = curr.getCol();

            // moving up
            if (currRow - 1 >= 0 && !set.contains(grids[currRow - 1][currCol])) {
                grids[currRow - 1][currCol].setColor(2);
                queue.offer(grids[currRow - 1][currCol]);
                set.add(grids[currRow - 1][currCol]);
                repaint();
                delay();

            }

            // moving down
            if (currRow + 1 < grids.length && !set.contains(grids[currRow + 1][currCol])) {
                grids[currRow + 1][currCol].setColor(2);
                queue.offer(grids[currRow + 1][currCol]);
                set.add(grids[currRow + 1][currCol]);
                repaint();
                delay();
            }

            // moving left
            if (currCol - 1 >= 0 && !set.contains(grids[currRow][currCol - 1])) {
                grids[currRow][currCol - 1].setColor(2);
                queue.offer(grids[currRow][currCol - 1]);
                set.add(grids[currRow][currCol - 1]);
                repaint();
                delay();
            }

            // moving right
            if (currCol + 1 < grids[currRow].length && !set.contains(grids[currRow][currCol + 1])) {
                grids[currRow][currCol + 1].setColor(2);
                queue.offer(grids[currRow][currCol + 1]);
                set.add(grids[currRow][currCol + 1]);
                repaint();
                delay();
            }
        }
    }

    public void delay() {    //DELAY METHOD
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / DIMENSION;
        int y = e.getY() / DIMENSION;
        grids[x][y].setColor(0);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
