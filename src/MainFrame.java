import javax.swing.*;

class MainFrame extends JFrame {
    private final int WIDTH = 660;
    private final int HEIGHT = 472;
    public MainFrame(){
        setSize(WIDTH, HEIGHT);
        setTitle("Path Finding");
        setResizable(false);

        Grid grid = new Grid();
        add(grid);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}