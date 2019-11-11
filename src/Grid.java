import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Grid extends JPanel implements MouseListener, ActionListener {
    private Vertex startVertex;
    private Vertex endVertex;
    private HashSet<Vertex> visited;
    private ArrayList<Vertex> foundPath;
    private boolean isFinished;

    private final int DIMENSION = 20; // dimension of single grid
    private final int WIDTH = 660;
    private final int HEIGHT = 460;

    private final int ROWS = HEIGHT / DIMENSION; // height
    private final int COLS = WIDTH / DIMENSION; // width
    private final Vertex[][] grids;

    public Grid() {
        grids = new Vertex[COLS][ROWS];
        visited = new HashSet<>();
        foundPath = new ArrayList<>();
        isFinished = false;

        addMouseListener(this);
        init();
    }

    // Initialize the grid
    public void init() {
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row] = new Vertex(col, row);
            }
        }
        System.out.println("Width: " + grids.length + ", Height" + grids[0].length);
        startVertex = grids[10][10];
        endVertex = grids[22][22];
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
                    g.setColor(IStyle.darkText); // wall
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

        private static final long DELAY = 10;

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
            Queue<Vertex> queue = new LinkedList<>();
            queue.offer(startVertex);
            visited.add(startVertex);

            while (!queue.isEmpty()) {

                Vertex curr = queue.poll();
                curr.setStyle(0);
                int currRow = curr.getX();
                int currCol = curr.getY();

                Vertex topGrid = null;
                Vertex bottomGrid = null;
                Vertex leftGrid = null;
                Vertex rightGrid = null;

                // complete
                if (curr == endVertex) {
                    System.out.println("Done");
                    // when it found the node, it will turn all the grids to green
                    while (!queue.isEmpty()) {
                        queue.poll().setStyle(0);
                    }
                    isFinished = true;
                    repaint();
                    delay();
                    for(Vertex vertex : foundPath){
                        vertex.setStyle(2);
                    }
                    repaint();
                    delay();
                    break;
                }

                // moving up
                if (currRow - 1 >= 0 && !visited.contains(grids[currRow - 1][currCol]) && grids[currRow - 1][currCol].getStyle() != 3) {
                    topGrid = grids[currRow - 1][currCol];
                    queue.offer(topGrid);
                    topGrid.setStyle(1);
                    visited.add(topGrid);
                }

                // moving left
                if (currCol - 1 >= 0 && !visited.contains(grids[currRow][currCol - 1]) && grids[currRow][currCol - 1].getStyle() != 3) {
                    leftGrid = grids[currRow][currCol - 1];
                    queue.offer(leftGrid);
                    leftGrid.setStyle(1);
                    visited.add(leftGrid);
                }

                // moving down
                if (currRow + 1 < grids.length && !visited.contains(grids[currRow + 1][currCol]) && grids[currRow + 1][currCol].getStyle() != 3) {
                    bottomGrid = grids[currRow + 1][currCol];
                    queue.offer(bottomGrid);
                    bottomGrid.setStyle(1);
                    visited.add(bottomGrid);
                }

                // moving right
                if (currCol + 1 < grids[currRow].length && !visited.contains(grids[currRow][currCol + 1]) && grids[currRow][currCol + 1].getStyle() != 3) {
                    rightGrid = grids[currRow][currCol + 1];
                    queue.offer(rightGrid);
                    rightGrid.setStyle(1);
                    visited.add(rightGrid);
                }
                if(bottomGrid != null){
                    foundPath.add(bottomGrid);
                }else if(leftGrid != null){
                    foundPath.add(leftGrid);
                }else if(rightGrid != null){
                    foundPath.add(rightGrid);
                }else if(topGrid != null){
                    foundPath.add(topGrid);
                }
                repaint();
                delay();
            }
        }
    }
}
