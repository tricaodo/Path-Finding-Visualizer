import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Grid extends JPanel implements MouseListener, ActionListener {
    private Node startNode;
    private Node endNode;
    HashSet<Node> visited = new HashSet<>();

    private final int DIMENSION = 10; // dimension of single grid
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
        startNode = grids[32][23];
        endNode = grids[65][45];
    }

    public void start() {
        new PathFinding().execute();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                g.setColor(IStyle.btnPanel);
                g.drawRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                if (grids[col][row].getStyle() == -1) {
                    g.setColor(IStyle.lightText); // default
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 0) {
                    g.setColor(IStyle.greenHighlight); // visited
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 1) {
                    g.setColor(IStyle.redHighlight); // processing outer
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 2) {
                    g.setColor(IStyle.blueHighlight); // found path
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else {
                    g.setColor(IStyle.btnPanel); // wall
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / DIMENSION;
        int y = e.getY() / DIMENSION;
        grids[x][y].setStyle(3);
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

    @Override
    public void actionPerformed(ActionEvent ev) {
        System.out.println("Action Performed");

    }

    class PathFinding extends SwingWorker<Void, Void> {

        private static final long DELAY = 3;
        private final Random rand = new Random();

        @Override
        public Void doInBackground() {
            BFS();
            return null;
        }

        @Override
        public void done() {
        }

        public void delay() {
            try {
                Thread.sleep(DELAY); //simulate long process
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        private void BFS() {
            Queue<Node> queue = new LinkedList<>();
            queue.offer(startNode);
            visited.add(startNode);

            while (!queue.isEmpty()) {

                Node curr = queue.poll();
                curr.setStyle(0);
                int currRow = curr.getX();
                int currCol = curr.getY();

                // complete
                if (curr == endNode) {
                    System.out.println("Done");
                    // when it found the node, it will turn all the grids to green
                    while (!queue.isEmpty()) {
                        queue.poll().setStyle(0);
                    }
                    repaint();
                    delay();
                    break;
                }

                // moving up
                if (currRow - 1 >= 0 && !visited.contains(grids[currRow - 1][currCol]) && grids[currRow - 1][currCol].getStyle() != 3) {
                    Node topGrid = grids[currRow - 1][currCol];
                    queue.offer(topGrid);
                    topGrid.setStyle(1);
                    visited.add(topGrid);
                }

                // moving down
                if (currRow + 1 < grids.length && !visited.contains(grids[currRow + 1][currCol]) && grids[currRow + 1][currCol].getStyle() != 3) {
                    Node bottomGrid = grids[currRow + 1][currCol];
                    queue.offer(bottomGrid);
                    bottomGrid.setStyle(1);
                    visited.add(bottomGrid);
                }
                // moving left
                if (currCol - 1 >= 0 && !visited.contains(grids[currRow][currCol - 1]) && grids[currRow][currCol - 1].getStyle() != 3) {
                    Node leftGrid = grids[currRow][currCol - 1];
                    queue.offer(leftGrid);
                    leftGrid.setStyle(1);
                    visited.add(leftGrid);
                }

                // moving right
                if (currCol + 1 < grids[currRow].length && !visited.contains(grids[currRow][currCol + 1]) && grids[currRow][currCol + 1].getStyle() != 3) {
                    Node rightGrid = grids[currRow][currCol + 1];
                    queue.offer(rightGrid);
                    rightGrid.setStyle(1);
                    visited.add(rightGrid);
                }
                repaint();
                delay();

            }
        }
    }
}
