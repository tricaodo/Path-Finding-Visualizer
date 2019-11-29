import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Queue;
import java.util.*;


public class PathFinding extends JPanel implements MouseListener, ActionListener, MouseMotionListener {
    private Vertex startVertex;
    private Vertex endVertex;
    private boolean isFinished;
    private boolean isDiagonal;
    private int keyFlag = 0;
    private int velocity = 5;
    private String algorithmStr = "Breath First Search";
    private String mazeStr = "Random Maze";

    private int DIMENSION = 18; // dimension of single grid
    private int WIDTH = 810;
    private int HEIGHT = 540;

    private int ROWS;// height
    private int COLS;// width
    private Vertex[][] grids;

    private JLabel costValLabel;
    private JLabel lengthValLabel;

    public PathFinding(JLabel costValLabel, JLabel lengthValLabel) {

        this.costValLabel = costValLabel;
        this.lengthValLabel = lengthValLabel;
        initializeGrid();
        isFinished = false;

        addMouseListener(this);
        addMouseMotionListener(this);

        buildGraph();
    }

    /**
     * Initialize the grid.
     */
    private void initializeGrid() {
        calculateRowsAndCols();
        grids = new Vertex[COLS][ROWS];
        reset(isDiagonal);
        repaint();
    }

    /**
     * Calculate the rows and columns.
     */
    private void calculateRowsAndCols() {
        if (DIMENSION == 16) DIMENSION = DIMENSION + 1;
        COLS = WIDTH / DIMENSION;
        ROWS = HEIGHT / DIMENSION;
    }

    /**
     * Build graph.
     */
    public void buildGraph() {
        reset(isDiagonal);
        System.out.println("Width: " + grids.length + ", Height" + grids[0].length);
    }

    /**
     * reset all the grids to default value
     */
    public void reset(boolean isDiagonal) {
        this.isDiagonal = isDiagonal;
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                grids[col][row] = new Vertex(col, row);
            }
        }
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {

                // left
                if (col - 1 >= 0) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col - 1][row]));
                }

                //right
                if (col + 1 < grids.length) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col + 1][row]));
                }

                // top
                if (row - 1 >= 0) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col][row - 1]));
                }

                // bottom
                if (row + 1 < grids[col].length) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col][row + 1]));
                }
            }
        }
        if (isDiagonal) { // enable diagonal path.
            enableDiagonal();
        }
        keyFlag = 0; // indicate whether start vertex, end vertex or the walls
        isFinished = false;
        costValLabel.setText("0");
        lengthValLabel.setText("0");
        repaint();
    }

    /**
     * Enable the diagonal graph.
     */
    public void enableDiagonal() {
        for (int col = 0; col < grids.length; col++) {
            for (int row = 0; row < grids[col].length; row++) {
                //============= diagonal ================//
                //top left
                if (row - 1 >= 0 && col - 1 >= 0) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col - 1][row - 1]));
                }
                // top right
                if (row - 1 >= 0 && col + 1 < grids.length) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col + 1][row - 1]));
                }

                // bottom left
                if (row + 1 < grids[col].length && col - 1 >= 0) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col - 1][row + 1]));
                }
                // bottom right
                if (row + 1 < grids[col].length && col + 1 < grids.length) {
                    int weight = (int) (Math.floor(Math.random() * 3) + 1);
                    grids[col][row].getEdges().add(new Edge(weight, grids[col + 1][row + 1]));
                }
            }
        }
    }

    /**
     * Changing the dimension the single grid and rebuild the whole canvas.
     *
     * @param size the size of the current slider value.
     */
    public void changeSizeOfGrid(int size) {
        switch (size) {
            case 0:
                DIMENSION = 27;
                initializeGrid();
                break;
            case 50:
                DIMENSION = 18;
                initializeGrid();
                break;
            case 100:
                DIMENSION = 15;
                initializeGrid();
                break;
        }
    }

    public void changeVelocity(int speed) {
        if (speed == 0) speed = 1;
        this.velocity = 40 / speed;
    }

    /**
     * Generating random wall.
     *
     * @param mazeStr what kind of generating maze string.
     */
    public void generateRandomMaze(String mazeStr) {
        reset(false);
        this.mazeStr = mazeStr;
//        for (int col = 0; col < grids.length; col++) {
//            for (int row = 0; row < grids[col].length; row++) {
//                if (grids[col][row].getStyle() != 4 && grids[col][row].getStyle() != 5)
//                    if (Math.random() < 0.3) {
//                        grids[col][row].setStyle(3);
//                    }
//            }
//
//        }
        new Algorithm().recursiveBacktracking();
        repaint();
    }

    /**
     * Start the program.
     *
     * @param str what kind of algorithm
     */
    public void start(String str) {
        algorithmStr = str;
        new Algorithm().execute();
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
                    g.setColor(IStyle.lightGreen1); // processing
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 1) {
                    g.setColor(IStyle.lightGreen2); // visited
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 2) {
                    g.setColor(IStyle.lightOrange); // found path
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 3) {
                    g.setColor(IStyle.dark); // wall
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 4) {
                    g.setColor(IStyle.green); // start
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                } else if (grids[col][row].getStyle() == 5) {
                    g.setColor(IStyle.red); // end
                    g.fillRect(col * DIMENSION, row * DIMENSION, DIMENSION, DIMENSION);
                }
            }
        }
    }

    /**
     * get the coordinate of the grid and determine it is wall, start or end vertex.
     *
     * @param e mouse event
     */
    private void calculateGridPosition(MouseEvent e) {
        if (e.getX() < WIDTH && e.getX() >= 0 && e.getY() < HEIGHT && e.getY() >= 0) {
            int x = e.getX() / DIMENSION; // get the x position of the grid being dragged.
            int y = e.getY() / DIMENSION; // get the y position of the grid being dragged.
            if (e.getButton() == MouseEvent.BUTTON1) { // left mouse click.
                if (keyFlag == 0) {
                    startVertex = grids[x][y];
                    startVertex.setStyle(4); // set the grid is the start.
                    keyFlag++;
                } else if (keyFlag == 1) {
                    endVertex = grids[x][y];
                    endVertex.setStyle(5); // set the grid is the end.
                    keyFlag++;
                } else {
                    if (grids[x][y].getStyle() != 4 && grids[x][y].getStyle() != 5) {
                        grids[x][y].setStyle(3); // set the grid is the wall except the start and end vertex.
                    }
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) { // right mouse click.
                // if it is a wall remove it.
                if (grids[x][y].getStyle() == 3) {
                    grids[x][y].setStyle(-1);
                }
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
        if (keyFlag == 2) {
            calculateGridPosition(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    class Algorithm extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            if (algorithmStr.equals("Breadth First Search")) {
                BFS();
            } else if (algorithmStr.equals("Depth First Search")) {
                DFS();
            } else if (algorithmStr.equals("Dijkstra")) {
                Dijkstra();
            } else if (algorithmStr.equals("A*")) {
                AStar();
            }
            return null;
        }

        @Override
        public void done() {
        }

        /**
         * - Employ the regular stack.
         * - Add the start vertex into the stack and process its neighbors along the way until stack is empty.
         */
        private void DFS() {
            Stack<Vertex> stack = new Stack<>();
            HashSet<Vertex> visited = new HashSet<>();
            stack.add(startVertex);
            visited.add(startVertex);
            startVertex.setVisited(true);
            startVertex.setG(0);
            Vertex targetVertex = null;

            while (!stack.isEmpty() && !isFinished) {
                Vertex current = stack.pop();
                if (current != startVertex) {
                    current.setStyle(0);
                }
                update(velocity);
                for (Edge edge : current.getEdges()) {
                    if (!visited.contains(edge.getDestination()) && edge.getDestination().getStyle() != 3) {
                        edge.getDestination().setPrevious(current); // point the pointer to the previous node.
                        edge.getDestination().setG(current.getG() + edge.getWeight());
                        if (edge.getDestination() == endVertex) {
                            targetVertex = endVertex;
                            isFinished = true;
                            break;
                        }
                        edge.getDestination().setStyle(1);
                        stack.push(edge.getDestination()); // push to the stack.
                        visited.add(edge.getDestination()); // add to visited.
                        update(velocity);
                    }
                }
            }
            while (!stack.isEmpty()) {
                Vertex current = stack.pop();
                current.setStyle(0);
                update(velocity);
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
            startVertex.setG(0);
            Vertex targetVertex = null;

            while (!queue.isEmpty() && !isFinished) {
                Vertex current = queue.poll();
                if (current != startVertex) {
                    current.setStyle(0);
                }
                update(velocity);
                for (Edge edge : current.getEdges()) {
                    // check whether the neighbors are visited and those vertices are not the wall.
                    if (!visited.contains(edge.getDestination()) && edge.getDestination().getStyle() != 3) {
                        edge.getDestination().setG(current.getG() + edge.getWeight());
                        edge.getDestination().setPrevious(current); // point to the previous node to go back.
                        if (edge.getDestination() == endVertex) { // if found the vertex, stop searching.
                            targetVertex = edge.getDestination();
                            isFinished = true;
                            break;
                        }
                        edge.getDestination().setStyle(1);
                        queue.offer(edge.getDestination());
                        visited.add(edge.getDestination());
                        update(velocity);
                    }
                }
            }

            while (!queue.isEmpty()) {
                Vertex current = queue.poll();
                current.setStyle(0);
                update(velocity);
            }
            // check whether target vertex was found.
            traverseBack(targetVertex);
        }

        /**
         * - Employ priority queue with the comparator which compares the cost of 2 vertices.
         * - Setting the start vertex cost is 0;
         * - As long as queue is not empty, poll the vertex in the queue and update its neighbor cost
         * if the total cost at current Vertex and edge weight less than the its neighbor cost
         */
        private void Dijkstra() {
            PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(CostComparator.compare_G());
            Vertex targetVertex = null;
            startVertex.setG(0);
            priorityQueue.offer(startVertex);
            while (!priorityQueue.isEmpty() && !isFinished) {
                Vertex current = priorityQueue.poll();
                if (current != startVertex && current != endVertex) {
                    current.setStyle(0);
                }
                update(velocity);
                for (Edge edge : current.getEdges()) {
                    if (!edge.getDestination().isVisited() && edge.getDestination().getStyle() != 3) {
                        int cost = current.getG() + edge.getWeight();
                        if (edge.getDestination().getG() > cost) {
                            priorityQueue.remove(edge.getDestination());
                            edge.getDestination().setG(cost);
                            edge.getDestination().setPrevious(current);
                            priorityQueue.offer(edge.getDestination());
                            if (edge.getDestination() != endVertex) {
                                edge.getDestination().setStyle(1);
                            }
                            if (edge.getDestination() == endVertex) {
                                targetVertex = endVertex;
                                isFinished = true;
                                break;
                            }
                            update(velocity);
                        }
                    }
                }
                current.setVisited(true);
            }
            while (!priorityQueue.isEmpty()) {
                Vertex current = priorityQueue.poll();
                if (current != endVertex) {
                    current.setStyle(0);
                }
                update(velocity);
            }
            traverseBack(targetVertex);
        }

        /**
         * - Employ Priority Queue which can sort F cost.
         * - Setting the start vertex F's cost and G's cost is 0;
         * - As long as queue is not empty, poll the vertex in the queue and
         * add it into the close list.
         * + if found the goal -> terminate the algorithm
         * + check its neighbors
         * - if close set already contains this neighbor -> skip that
         * - else calculate the G cost (G cost current vertex with the weight to get its current neighbor)
         * + if the new G cost is less than the cost of the current neighbor
         * - update the new G cost
         * - set the current neighbor point to its parent
         * - calculate the heuristic (H cost) from current neighbor to end vertex
         * - calculate the F cost (F = G + H)
         * if the total cost at current Vertex and edge weight less than the its neighbor cost
         */
        private void AStar() {
            PriorityQueue<Vertex> openSet = new PriorityQueue<>(CostComparator.compare_F());
            Vertex targetVertex = null;
            List<Vertex> closedSet = new LinkedList<>();
            startVertex.setF(0);
            startVertex.setG(0);
            openSet.offer(startVertex);
            while (!openSet.isEmpty()) {
                Vertex current = openSet.poll();
                closedSet.add(current);

                if (current == endVertex) {
                    targetVertex = endVertex;
                    break;
                }

                if (current != endVertex && current != startVertex) {
                    current.setStyle(1);
                    update(velocity);
                }

                for (Edge edge : current.getEdges()) {
                    Vertex neighbor = edge.getDestination();
                    if (closedSet.contains(neighbor)) {
                        continue;
                    }
                    int tempG = current.getG() + edge.getWeight();
                    if (tempG < neighbor.getG() && neighbor.getStyle() != 3) {
                        neighbor.setPrevious(current);
                        neighbor.setG(tempG);
                        neighbor.setH(heuristic(neighbor, endVertex));
                        neighbor.setF(neighbor.getH() + neighbor.getG());
                        if (openSet.contains(neighbor)) {
                            openSet.remove(neighbor); // must remove the element from pqueue and add it back
                        }
                        openSet.offer(neighbor);

                        if (neighbor != endVertex) {
                            neighbor.setStyle(0);
                        }
                        update(velocity);
                    }
                }
            }
            while (!openSet.isEmpty()) {
                Vertex current = openSet.poll();
                current.setStyle(0);
            }
            traverseBack(targetVertex);
        }

//        public void recursiveBacktracking() {
//            for (int i = 0; i < grids.length; i++) {
//                for (int j = 0; j < grids[i].length; j++) {
//                        grids[i][j].setStyle(3);
//                }
//            }
//            Stack<Vertex> stack = new Stack<>(); // initialize a stack
//            Vertex current = grids[1][1]; // mark the first top left vertex
//            stack.push(current);
//            while (!stack.isEmpty()) {
//                current = stack.pop(); // pop from the stack
//                if(current.getStyle() == 3){
//                    current.setStyle(-1);
//                    int x = current.getX();
//                    int y = current.getY();
//
//                    int top = -1;
//                    int right = -1;
//                    int bottom = -1;
//                    int left = -1;
//
//                    // left
//                    if (x - 2 >= 0) {
//                        left = x - 2;
//                    }
//
//                    // right
//                    if (x + 2 < grids.length) {
//                        right = x + 2;
//                    }
//
//                    // top
//                    if (y - 2 >= 0) {
//                        top = y - 2;
//                    }
//
//                    // bottom
//                    if (y + 2 < grids[0].length) {
//                        bottom = y + 2;
//                    }
//                    // left vertex
//                    if (left != -1 && grids[left][y].getStyle() != -1) {
//                        grids[left - 1][y].setStyle(-1);
//                        stack.push(grids[left][y]);
//                    }
//                    // right vertex
//                    if (right != -1 && grids[right][y].getStyle() != -1) {
//                        grids[right + 1][y].setStyle(-1);
//                        stack.push(grids[right][y]);
//                    }
//                    // top vertex
//                    if (top != -1 && grids[x][top].getStyle() != -1) {
//                        grids[x][top - 1].setStyle(-1);
//                        stack.push(grids[x][top]);
//                    }
//                    // bottom vertex
//                    if (bottom != -1 && grids[x][bottom].getStyle() != -1) {
//                        grids[x][bottom + 1].setStyle(-1);
//                        stack.push(grids[x][bottom]);
//                    }
//                }
//            }
//            repaint();
//        }

        public void recursiveBacktracking() {
            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length; j++) {
                        grids[i][j].setStyle(3);
                }
            }
            grids[1][1].setStyle(-1);
            recursion(1,1);
            repaint();
        }

        public Integer[] generateRandomDirections() {
            ArrayList<Integer> randoms = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                randoms.add(i + 1);
            Collections.shuffle(randoms);

            return randoms.toArray(new Integer[4]);
        }

        public void recursion(int r, int c) {
            // 4 random directions
            Integer[] randDirs = generateRandomDirections();
            // Examine each direction
            for (int i = 0; i < randDirs.length; i++) {

                switch (randDirs[i]) {
                    case 1: // Up
                        //　Whether 2 cells up is out or not
                        if (r - 2 <= 0)
                            continue;
                        if (grids[r - 2][c].getStyle() != -1) {
                            grids[r - 2][c].setStyle(-1);
                            grids[r - 1][c].setStyle(-1);
                            recursion(r - 2, c);
                        }
                        break;
                    case 2: // Right
                        // Whether 2 cells to the right is out or not
                        if (c + 2 >= grids[0].length)
                            continue;
                        if (grids[r][c + 2].getStyle() != -1) {
                            grids[r][c + 2].setStyle(-1);
                            grids[r][c + 1].setStyle(-1);
                            recursion(r, c + 2);
                        }
                        break;
                    case 3: // Down
                        // Whether 2 cells down is out or not
                        if (r + 2 >= grids.length)
                            continue;
                        if (grids[r + 2][c].getStyle() != -1) {
                            grids[r + 2][c].setStyle(-1);
                            grids[r + 1][c].setStyle(-1);
                            recursion(r + 2, c);
                        }
                        break;
                    case 4: // Left
                        // Whether 2 cells to the left is out or not
                        if (c - 2 <= 0)
                            continue;
                        if (grids[r][c - 2].getStyle() != -1) {
                            grids[r][c - 2].setStyle(-1);
                            grids[r][c - 1].setStyle(-1);
                            recursion(r, c - 2);
                        }
                        break;
                }
            }
        }

            private void removerWalls(Vertex a, Vertex b) {
            int x = Math.abs(a.getX() - b.getX());
            int y = Math.abs(a.getY() - b.getY());
            if (x != 0) {
                grids[x - 1][a.getY()].setStyle(-1);
            }
            if (y != 0) {
                System.out.println("Neighbour: " + grids[a.getX()][y]);
                grids[a.getX()][y - 1].setStyle(-1);
            }
        }

        private Vertex getNeighbour(Vertex current) {
            int x = current.getX();
            int y = current.getY();

            int top = -1;
            int right = -1;
            int bottom = -1;
            int left = -1;

            // left
            if (x - 2 >= 0) {
                left = x - 2;
            }

            // right
            if (x + 2 < grids.length) {
                right = x + 2;
            }

            // top
            if (y - 2 >= 0) {
                top = y - 2;
            }

            // bottom
            if (y + 2 < grids[0].length) {
                bottom = y + 2;
            }
            // add all the unvisited neighbors to list.
            List<Vertex> neighbors = new ArrayList<>();
            // left vertex
            if (left != -1 && grids[left][y].getStyle() != -1) {
                neighbors.add(grids[left][y]);
            }
            // right vertex
            if (right != -1 && grids[right][y].getStyle() != -1) {
                neighbors.add(grids[right][y]);
            }
            // top vertex
            if (top != -1 && grids[x][top].getStyle() != -1) {
                neighbors.add(grids[x][top]);
            }
            // bottom vertex
            if (bottom != -1 && grids[x][bottom].getStyle() != -1) {
                neighbors.add(grids[x][bottom]);
            }
            if (neighbors.size() > 0) {
                int rand = (int) (Math.random() * neighbors.size());
                return neighbors.get(rand);
            } else {
                return null;
            }
        }


        /**
         * Calculate the heuristic from the current vertex to the ending vertex.
         *
         * @param current current vertex.
         * @param end     ending vertex.
         * @return the heuristic
         */
        private int heuristic(Vertex current, Vertex end) {
            int dx = Math.abs(end.getX() - current.getX());
            int dy = Math.abs(end.getY() - current.getY());
            return 2 * (dx * dx + dy * dy);
        }

        /**
         * Check whether there is a path
         * - if the path is available traverse back from ending vertex to
         * start vertex and also calculate the cost along the way
         *
         * @param targetVertex the ending vertex
         */
        private void traverseBack(Vertex targetVertex) {
            int total = 0;
            int length = 0;
            // traverse back from target vertex to the start vertex.
            if (targetVertex != null) {
                while (targetVertex != null) {
                    total += targetVertex.getG(); // calculate the cost along the way back.
                    length++;
                    costValLabel.setText(total + "");
                    lengthValLabel.setText(length + "");
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
         *
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
