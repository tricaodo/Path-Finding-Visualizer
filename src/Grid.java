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

    private final int delay = 1000;
    HashSet<Node> set = new HashSet<>();

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
        startNode = grids[15][15];
        endNode = grids[30][40];
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
        new DFSTask().execute();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                g.setColor(Color.BLACK);
                g.drawRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                if (grids[col][row].isVisited()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                }
            }
        }
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
    public void actionPerformed(ActionEvent ev) {
        System.out.println("Action Performed");

    }

    class DFSTask extends SwingWorker<Void, Void> {

        private static final long DELAY = 3;
        private final Random rand = new Random();

        @Override
        public Void doInBackground() {
            bfs();
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

        void bfs() {
            Queue<Node> queue = new LinkedList<>();
            queue.offer(startNode);
            set.add(startNode);

            while (!queue.isEmpty()) {

                Node curr = queue.poll();
                curr.setVisited(true);

                if (curr == endNode) {
                    // done
                    System.out.println("Done");
                    break;
                }

                int currRow = curr.getX();
                int currCol = curr.getY();
                // moving up
                if (currRow - 1 >= 0 && !set.contains(grids[currRow - 1][currCol])) {
                    queue.offer(grids[currRow - 1][currCol]);
                    set.add(grids[currRow - 1][currCol]);
                    grids[currRow - 1][currCol].setVisited(true);
                    repaint();
                    delay();
                }

                // moving down
                if (currRow + 1 < grids.length && !set.contains(grids[currRow + 1][currCol])) {
                    queue.offer(grids[currRow + 1][currCol]);
                    set.add(grids[currRow + 1][currCol]);
                    grids[currRow + 1][currCol].setVisited(true);
                    repaint();
                    delay();
                }
                // moving left
                if (currCol - 1 >= 0 && !set.contains(grids[currRow][currCol - 1])) {
                    queue.offer(grids[currRow][currCol - 1]);
                    set.add(grids[currRow][currCol - 1]);
                    grids[currRow][currCol - 1].setVisited(true);
                    repaint();
                    delay();
                }

                // moving right
                if (currCol + 1 < grids[currRow].length && !set.contains(grids[currRow][currCol + 1])) {
                    queue.offer(grids[currRow][currCol + 1]);
                    set.add(grids[currRow][currCol + 1]);
                    grids[currRow][currCol + 1].setVisited(true);
                    repaint();
                    delay();
                }
            }

        }
    }
}
