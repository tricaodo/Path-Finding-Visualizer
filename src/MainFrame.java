import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class MainFrame extends JFrame implements ItemListener {
    private final int WIDTH = 660;
    private final int HEIGHT = 530;
    private final String[] strCombo = {"Breadth First Search",
            "Depth First Search", "Dijkstra"};

    private final JButton start = new JButton("Start");
    private final JButton reset = new JButton("Reset");

    private JComboBox comboBox = new JComboBox(strCombo);
    private String optionString = "Breadth First Search";


    private final Grid grid = new Grid();

    public MainFrame() {
        setSize(WIDTH, HEIGHT);
        setTitle("Path Finding");
        setResizable(false);
        setLayout(new BorderLayout());

        add(grid, BorderLayout.CENTER);
        topLayout();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    private void topLayout() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 1;

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        jPanel.add(start, gridConstraints);
        start.addActionListener(e -> {
            grid.start(optionString);
        });

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        jPanel.add(reset, gridConstraints);
        reset.addActionListener(e -> grid.reset());

        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        jPanel.add(comboBox, gridConstraints);
        comboBox.addItemListener(this);
        add(jPanel, BorderLayout.NORTH);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            optionString = (String) e.getItem();
        }
    }
}