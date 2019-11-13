import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    private final int WIDTH = 660;
    private final int HEIGHT = 530;
    private final JButton start = new JButton("Start");
    private final JButton reset = new JButton("Reset");
    private final Grid grid = new Grid();

    public MainFrame(){
        setSize(WIDTH, HEIGHT);
        setTitle("Path Finding");
        setResizable(false);
        setLayout(new BorderLayout());
        add(grid, BorderLayout.CENTER);
        add(start, BorderLayout.SOUTH);
        add(reset, BorderLayout.NORTH);
        start.addActionListener(e -> grid.start());
        reset.addActionListener(e -> grid.reset());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

}