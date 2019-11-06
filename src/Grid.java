import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Grid extends JPanel implements MouseListener, ActionListener {
    private Node startNode;
    private Node endNode;

    private final int delay = 1000;
    HashSet<Node> set = new HashSet<>();
    Timer timer = new Timer(delay, this);

    private final int DIMENSION = 30; // dimension of single grid
    private final int WIDTH = 660;
    private final int HEIGHT = 460;

    private final int ROWS = HEIGHT / DIMENSION; // height
    private final int COLS = WIDTH / DIMENSION; // width
    private final Node[][] grids;

    public Grid() {
        grids = new Node[COLS][ROWS];
        addMouseListener(this);
        init();
    }

    // Initialize the grid
    public void init() {

        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row] = new Node(col, row);
            }
        }
        System.out.println("Width: " + grids.length + ", Height" + grids[0].length);
        startNode = grids[0][0];
        endNode = grids[18][13];
    }

    // Check if it is finished
    private boolean isFinished() {
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                if (!grids[col][row].isVisited()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void start() {
        bfs();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                g.drawRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                if (grids[col][row].isVisited()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                }
            }
        }
//        timer.start();
    }

    private void bfs() {
        repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("ashdasd");
        Queue<Node> queue = new LinkedList<>();
        queue.offer(startNode);
        set.add(startNode);

        while (!queue.isEmpty()) {

            Node curr = queue.poll();
            curr.setVisited(true);

            System.out.println("lalala");
            int currRow = curr.getX();
            int currCol = curr.getY();
            // moving up
            if (currRow - 1 >= 0 && !set.contains(grids[currRow - 1][currCol])){
                queue.offer(grids[currRow - 1][currCol]);
                set.add(grids[currRow - 1][currCol]);
                grids[currRow - 1][currCol].setVisited(true);
            }

            // moving down
            if (currRow + 1 < grids.length && !set.contains(grids[currRow + 1][currCol])) {
                queue.offer(grids[currRow + 1][currCol]);
                set.add(grids[currRow + 1][currCol]);
                grids[currRow + 1][currCol].setVisited(true);

            }

            // moving left
            if (currCol - 1 >= 0 && !set.contains(grids[currRow][currCol - 1])) {
                queue.offer(grids[currRow][currCol - 1]);
                set.add(grids[currRow][currCol - 1]);
                grids[currRow][currCol - 1].setVisited(true);

            }

            // moving right
            if (currCol + 1 < grids[currRow].length && !set.contains(grids[currRow][currCol + 1])) {
                queue.offer(grids[currRow][currCol + 1]);
                set.add(grids[currRow][currCol + 1]);
                grids[currRow][currCol + 1].setVisited(true);

            }

        }
    }

    public void delay() {    //DELAY METHOD
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
    }

    private void update() {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / DIMENSION;
        int y = e.getY() / DIMENSION;
//        grids[x][y].setColor(0);
//        repaint();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Performed");
//        repaint();
    }
}
