import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Grid extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    private Vertex startVertex;

    private Vertex endVertex;
    private boolean isFinished;
    private int keyFlag = 0;
    private String algorithmStr = "Breath First Search";

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

    /**
     * reset all the grids to default value
     */
    public void reset() {
        System.out.println("Reset");
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row].setPrevious(null);
                grids[col][row].setStyle(-1);
                grids[col][row].setVisited(false);
                grids[col][row].setCost(Integer.MAX_VALUE); // default cost
            }
        }
        keyFlag = 0; // indicate whether start vertex, end vertex or the walls
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
                    int weight = (int) Math.floor(Math.random() * 5);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col - 1][row]));
                }

                //right
                if (col + 1 < grids.length) {
                    int weight = (int) Math.floor(Math.random() * 5);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col + 1][row]));
                }

                // bottom
                if (row - 1 >= 0) {
                    int weight = (int) Math.floor(Math.random() * 5);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col][row - 1]));
                }

                // top
                if (row + 1 < grids[col].length) {
                    int weight = (int) Math.floor(Math.random() * 5);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col][row + 1]));
                }
            }
        }
        System.out.println("Width: " + grids.length + ", Height" + grids[0].length);
    }

    public void start(String str) {
        algorithmStr = str;
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

    /**
     * get the coordinate of the grid and determine it is wall, start or end vertex.
     * @param e mouse event
     */
    private void calculateGridPosition(MouseEvent e) {
        int x = e.getX() / DIMENSION; // get the x position of the grid being dragged.
        int y = e.getY() / DIMENSION; // get the y position of the grid being dragged.
        if(e.getButton() == MouseEvent.BUTTON1){ // left mouse click.
            if (keyFlag == 0) {
                startVertex = grids[x][y];
                startVertex.setStyle(4); // set the grid is the start.
                keyFlag++;
            } else if (keyFlag == 1) {
                endVertex = grids[x][y];
                endVertex.setStyle(5); // set the grid is the end.
                keyFlag++;
            } else {
                grids[x][y].setStyle(3); // set the grid is the wall.
            }
        }else if(e.getButton() == MouseEvent.BUTTON3){ // right mouse click.
            // if it is a wall remove it.
            if(grids[x][y].getStyle() == 3){
                grids[x][y].setStyle(-1);
            }
        }
        repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        calculateGridPosition(e);
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
        if(keyFlag == 2){
            calculateGridPosition(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    class PathFinding extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            if (algorithmStr.equals("Breadth First Search")) {
                BFS();
            } else if (algorithmStr.equals("Depth First Search")) {
                DFS();
            } else if (algorithmStr.equals("Dijkstra")) {
                Dijkstra();
            }
            return null;
        }

        @Override
        public void done() {
        }

        private void DFS() {
            Stack<Vertex> stack = new Stack<>();
            HashSet<Vertex> visited = new HashSet<>();
            stack.add(startVertex);
            visited.add(startVertex);
            startVertex.setVisited(true);
            startVertex.setCost(0);
            Vertex targetVertex = null;

            while (!stack.isEmpty() && !isFinished) {
                Vertex current = stack.pop();
                if (current != startVertex) {
                    current.setStyle(0);
                }
                update(5);
                for (Edge edge : current.getEdges()) {
                    if (!visited.contains(edge.getDestination()) && edge.getDestination().getStyle() != 3) {
                        edge.getDestination().setPrevious(current); // point the pointer to the previous node.
                        edge.getDestination().setCost(current.getCost() + edge.getWeight());
                        if (edge.getDestination() == endVertex) {
                            targetVertex = endVertex;
                            isFinished = true;
                            break;
                        }
                        edge.getDestination().setStyle(1);
                        stack.push(edge.getDestination()); // push to the stack.
                        visited.add(edge.getDestination()); // add to visited.
                        update(5);
                    }
                }
            }
            while (!stack.isEmpty()) {
                Vertex current = stack.pop();
                current.setStyle(0);
                update(5);
            }
            traverseBack(targetVertex);
        }

        /**
         * - Employ the regular queue.
         * - Add the start vertex into the queue and process its neighbors along the way.
         */
        private void BFS() {
            Queue<Vertex> queue = new LinkedList<>();
            HashSet<Vertex> visited = new HashSet<>();
            queue.offer(startVertex);
            visited.add(startVertex);
            startVertex.setCost(0);
            Vertex targetVertex = null;

            while (!queue.isEmpty() && !isFinished) {
                Vertex current = queue.poll();
                if (current != startVertex) {
                    current.setStyle(0);
                }
                update(5);
                for (Edge edge : current.getEdges()) {
                    // check whether the neighbors are visited and those vertices are not the wall.
                    if (!visited.contains(edge.getDestination()) && edge.getDestination().getStyle() != 3) {
                        edge.getDestination().setCost(current.getCost() + edge.getWeight());
                        edge.getDestination().setPrevious(current); // point to the previous node to go back.
                        if (edge.getDestination() == endVertex) { // if found the vertex, stop searching.
                            targetVertex = edge.getDestination();
                            isFinished = true;
                            break;
                        }
                        edge.getDestination().setStyle(1);
                        queue.offer(edge.getDestination());
                        visited.add(edge.getDestination());
                        update(5);
                    }
                }
            }

            while (!queue.isEmpty()) {
                Vertex current = queue.poll();
                current.setStyle(0);
                update(5);
            }
            // check whether target vertex was found.
            traverseBack(targetVertex);
        }

        /**
         * - Employ priority queue with the comparator which compares the cost of 2 vertices.
         * - Setting the start vertex cost is 0;
         * - As long as queue is not empty, poll the vertex in the queue and update its neighbor cost
         *      if the total cost at current Vertex and edge weight less than the its neighbor cost
         */
        private void Dijkstra() {
            PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(new MyComparator());
            Vertex targetVertex = null;
            startVertex.setCost(0);
            priorityQueue.offer(startVertex);
            while (!priorityQueue.isEmpty()) {
                Vertex current = priorityQueue.poll();
                if (current != startVertex && current != endVertex) {
                    current.setStyle(0);
                }
                update(5);
                for (Edge edge : current.getEdges()) {
                    if (!edge.getDestination().isVisited() && edge.getDestination().getStyle() != 3) {
                        int cost = current.getCost() + edge.getWeight();
                        if (edge.getDestination().getCost() > cost) {
                            edge.getDestination().setCost(cost);
                            edge.getDestination().setPrevious(current);
                            priorityQueue.offer(edge.getDestination());
                            if (edge.getDestination() != endVertex) {
                                edge.getDestination().setStyle(1);
                            }
                            if (edge.getDestination() == endVertex) {
                                targetVertex = endVertex;
                            }
                            update(5);
                        }
                    }
                }
                current.setVisited(true);
            }
            traverseBack(targetVertex);
        }


        /**
         * Check whether there is a path
         *  - if the path is available traverse back from ending vertex to
         *      start vertex and also calculate the cost along the way
         * @param targetVertex the ending vertex
         */
        private void traverseBack(Vertex targetVertex) {
            int total = 0;
            // traverse back from target vertex to the start vertex.
            if (targetVertex != null) {
                while (targetVertex != null) {
                    total += targetVertex.getCost(); // calculate the cost along the way back.
                    if (targetVertex == startVertex) {
                        break; // break when hit the start vertex because don't want to change its color.
                    }
                    if (targetVertex != endVertex) {
                        targetVertex.setStyle(2);  // change color of the vertices except the target vertex.
                    }
                    targetVertex = targetVertex.getPrevious();
                    update(10);
                }
                System.out.println(algorithmStr + ": " + total);
            } else {
                System.out.println("No path!!!"); // No path found
            }
        }


        /**
         * repaint the canvas and with delay in millisecond.
         * @param delay millisecond delay
         */
        private void update(int delay) {
            repaint();
            try {
                Thread.sleep(delay); //simulate long process
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
