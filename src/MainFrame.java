import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainFrame extends JFrame {
    private final int WIDTH = 660;
    private final int HEIGHT = 530;
    private final JButton start = new JButton("Start");
    private final Grid grid = new Grid();

    public MainFrame(){
        setSize(WIDTH, HEIGHT);
        setTitle("Path Finding");
        setResizable(false);
        setLayout(new BorderLayout());
        add(grid, BorderLayout.CENTER);
        add(start, BorderLayout.SOUTH);
        System.out.println("hello");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.start();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

}