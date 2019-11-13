import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Grid extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    private Vertex startVertex;
    private Vertex endVertex;
    private boolean isFinished;

    private final int DIMENSION = 20; // dimension of single grid
    private final int WIDTH = 660;
    private final int HEIGHT = 460;

    private final int ROWS = HEIGHT / DIMENSION; // height
    private final int COLS = WIDTH / DIMENSION; // width
    private final Vertex[][] grids;

    public Grid() {
        grids = new Vertex[COLS][ROWS];
        isFinished = false;

        addMouseListener(this);
        addMouseMotionListener(this);
        buildGraph();
    }

    public void reset() {
        System.out.println("Reset");
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row].setPrevious(null);
                grids[col][row].setStyle(-1);
            }
        }
        startVertex = grids[10][10];
        startVertex.setPrevious(null);
        startVertex.setStyle(4);

        endVertex = grids[22][20];
        endVertex.setPrevious(null);
        endVertex.setStyle(5);
        isFinished = false;
        repaint();
    }

    // Initialize the grid
    public void buildGraph() {
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row] = new Vertex(col, row);
            }
        }

        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {

                // left
                if (col - 1 >= 0) {
                    int cost = (int) Math.floor(Math.random() * 15);
                    grids[col][row].getEdges().add(new Edge(cost, grids[col - 1][row]));
                }

                //right
                if (col + 1 < grids.length) {
                    int cost = (int) Math.floor(Math.random() * 15);
                    grids[col][row].getEdges().add(new Edge(cost, grids[col + 1][row]));
                }

                // bottom
                if (row - 1 >= 0) {
                    int cost = (int) Math.floor(Math.random() * 15);
                    grids[col][row].getEdges().add(new Edge(cost, grids[col][row - 1]));
                }

                // top
                if (row + 1 < grids[col].length) {
                    int cost = (int) Math.floor(Math.random() * 15);
                    grids[col][row].getEdges().add(new Edge(cost, grids[col][row + 1]));
                }
            }
        }
        System.out.println("Width: " + grids.length + ", Height" + grids[0].length);
        startVertex = grids[10][10];
        startVertex.setStyle(4);
        endVertex = grids[22][20];
        endVertex.setStyle(5);
    }

    public void start() {
        new PathFinding().execute();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                g.setColor(IStyle.lightDark);
                g.drawRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                if (grids[col][row].getStyle() == -1) {
                    g.setColor(IStyle.lightWhite); // default
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 0) {
                    g.setColor(IStyle.lightGreen); // visited
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 1) {
                    g.setColor(IStyle.lightOrange); // processing outer
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 2) {
                    g.setColor(IStyle.lightBlue); // found path
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 3) {
                    g.setColor(IStyle.dark); // wall
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 4) {
                    g.setColor(IStyle.green); // start
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 5) {
                    g.setColor(IStyle.lightPink); // end
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                }
            }
        }
    }

    private void calculateGridPosition(MouseEvent e) {
        int x = e.getX() / DIMENSION; // get the x position of the grid being dragged.
        int y = e.getY() / DIMENSION; // get the y position of the grid being dragged.
        grids[x][y].setStyle(3); // set the grid is the wall.
        repaint(); // repaint every time move the dragged mouse.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        calculateGridPosition(e);
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

    @Override
    public void mouseDragged(MouseEvent e) {
        calculateGridPosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    class PathFinding extends SwingWorker<Void, Void> {

        private static final long DELAY = 5;

        public void delay() {
            try {
                Thread.sleep(DELAY); //simulate long process
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        private void delay(int delay) {
            try {
                Thread.sleep(delay); //simulate long process
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }


        @Override
        public Void doInBackground() {
            BFS();
            return null;
        }

        @Override
        public void done() {
        }

        private void DFS() {
        }

        private void BFS() {
            Queue<Vertex> queue = new LinkedList<>();
            HashSet<Vertex> visited = new HashSet<>();
            queue.offer(startVertex);
            visited.add(startVertex);
            Vertex targetNode = null;

            while (!queue.isEmpty() && !isFinished) {
                Vertex current = queue.poll();
                if (startVertex != current) {
                    current.setStyle(0);
                }
                repaint();
                delay();
                for (Edge edge : current.getEdges()) {
                    // check whether the neighbors are visited and those vertices are not the wall.
                    if (!visited.contains(edge.getDestination()) && edge.getDestination().getStyle() != 3) {
                        edge.getDestination().setPrevious(current); // point to the previous node to go back.
                        if (edge.getDestination() == endVertex) { // if found the vertex, stop searching.
                            targetNode = edge.getDestination();
                            isFinished = true;
                            break;
                        }
                        edge.getDestination().setStyle(1);
                        queue.offer(edge.getDestination());
                        visited.add(edge.getDestination());
                        repaint();
                        delay();
                    }
                }
            }
            // check whether target vertex was found.
            if (targetNode != null) {
                // traverse back from target vertex to the start vertex.
                while (targetNode != null) {

                    if (targetNode == startVertex) {
                        break; // break when hit the start vertex because don't change its color.
                    }
                    if (targetNode != endVertex) {
                        targetNode.setStyle(2);  // change color of the vertices except the target vertex.
                    }
                    targetNode = targetNode.getPrevious();
                    repaint();
                    delay(10);
                }
            } else {
                System.out.println("No Path!!!");
            }
        }
    }
}
