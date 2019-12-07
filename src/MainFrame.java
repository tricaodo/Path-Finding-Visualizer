import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

class MainFrame extends JFrame implements ItemListener {
    private final int WIDTH = 1040;
    private final int HEIGHT = 560;

    private final String[] algorithmArr = {"Breadth First Search",
            "Depth First Search", "Dijkstra", "A*"};
    private final String[] mazeArr = {"Recursive Backtracking", "Random Maze"};

    private JComboBox algorithmCombo;
    private JComboBox mazeCombo;
    private JLabel costValLabel;
    private JLabel lengthValLabel;
    private JCheckBox diagonalCheckbox;
    private String searchString;
    private String mazeString;
    private boolean isDiagonal;

    private PathFinding pathFinding;

    public MainFrame() {
        algorithmCombo = new JComboBox(algorithmArr);
        mazeCombo = new JComboBox(mazeArr);
        costValLabel = new JLabel("0");
        lengthValLabel = new JLabel("0");
        diagonalCheckbox = new JCheckBox("Diagonal");
        pathFinding = new PathFinding(costValLabel, lengthValLabel);

        searchString = algorithmArr[0];
        mazeString = mazeArr[0];

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setTitle("Path Finding");
        setResizable(false);
        setLayout(new BorderLayout());

        add(pathFinding, BorderLayout.CENTER);
        topLayout();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }


    private void topLayout() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        Dimension dimension = new Dimension();
        dimension.width = 230;
        jPanel.setPreferredSize(dimension);

        JLabel algorithmLabel = new JLabel("Algorithm");
        JLabel mazeLabel = new JLabel("Maze generation");
        JLabel sizeLabel = new JLabel("Size");
        JLabel speedLabel = new JLabel("Delay(ms)");
        JButton startBtn = new JButton("        Start        ");
        JButton resetBtn = new JButton("        Reset        ");
        JButton mazeBtn = new JButton("  Generate Maze  ");
        JSlider sizeSlider = new JSlider(0, 100, 50);
        JSlider delaySlider = new JSlider(0, 12, 6);
        sizeSlider.setSnapToTicks(true);
        delaySlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> mapSlider = new Hashtable<>();
        for (int i = 0; i <= 100; i += 50) {
            mapSlider.put(i, new JLabel(i + ""));
        }
        Hashtable<Integer, JLabel> velocitySlider = new Hashtable<>();
        for (int i = 0; i <= 12; i += 3) {
            velocitySlider.put(i, new JLabel(i + ""));
        }
        sizeSlider.setLabelTable(mapSlider);
        sizeSlider.setPaintLabels(true);

        delaySlider.setLabelTable(velocitySlider);
        delaySlider.setPaintLabels(true);

        Border innerBorder = BorderFactory.createTitledBorder("Path Finding Visualizer");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        jPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        GridBagConstraints gridConstraints = new GridBagConstraints();

        // Label algorithm
        gridBagConstraints(1.0, 0.1, 0, 0, gridConstraints);
        jPanel.add(algorithmLabel, gridConstraints);

        // Dropbox algorithm
        gridBagConstraints(1.0, 0.1, 0, 1, gridConstraints);
        jPanel.add(algorithmCombo, gridConstraints);

        // Maze label
        gridBagConstraints(1.0, 0.1, 0, 2, gridConstraints);
        jPanel.add(mazeLabel, gridConstraints);

        // Maze combo
        gridBagConstraints(1.0, 0.1, 0, 3, gridConstraints);
        jPanel.add(mazeCombo, gridConstraints);

        // Button generate maze
        gridBagConstraints(1.0, 0.1, 0, 4, gridConstraints);
        jPanel.add(mazeBtn, gridConstraints);

        // Size label
        gridBagConstraints(1.0, 0.1, 0, 5, gridConstraints);
        jPanel.add(sizeLabel, gridConstraints);

        // Size slider
        gridBagConstraints(1.0, 0.1, 0, 6, gridConstraints);
        jPanel.add(sizeSlider, gridConstraints);

        // Speed label
        gridBagConstraints(1.0, 0.1, 0, 7, gridConstraints);
        jPanel.add(speedLabel, gridConstraints);

        // Speed slider
        gridBagConstraints(1.0, 0.1, 0, 8, gridConstraints);
        jPanel.add(delaySlider, gridConstraints);

        // Button Start
        gridBagConstraints(1.0, 0.1, 0, 9, gridConstraints);
        jPanel.add(startBtn, gridConstraints);


        // Button resetBtn
        gridBagConstraints(1.0, 0.1, 0, 10, gridConstraints);
        jPanel.add(resetBtn, gridConstraints);

        // Diagonal select box
        gridBagConstraints(1.0, 0.1, 0, 11, gridConstraints);
        jPanel.add(diagonalCheckbox, gridConstraints);

        //=============== Calculation of algorithm =================//
        JPanel calculationPanel = new JPanel(new GridBagLayout());
        calculationPanel.setPreferredSize(new Dimension(200, 90));
        JLabel costStrLabel = new JLabel("   Cost$: ");
        JLabel lengthStrLabel = new JLabel("   Length: ");

        innerBorder = BorderFactory.createTitledBorder("Info");
        outerBorder = BorderFactory.createEmptyBorder(5, 10, 5, 10);
        calculationPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        GridBagConstraints gridConstraintsInfo = new GridBagConstraints();

        // cost string label
        gridConstraintsInfo.weightx = 1;
        gridConstraintsInfo.weighty = 0.1;
        gridConstraintsInfo.gridx = 0;
        gridConstraintsInfo.gridy = 0;
        gridConstraintsInfo.fill = GridBagConstraints.NONE;
        gridConstraintsInfo.anchor = GridBagConstraints.LINE_START;
        calculationPanel.add(costStrLabel, gridConstraintsInfo);

        // cost value label

        gridBagConstraints(1.0, 0.1, 1, 0, gridConstraintsInfo);
        calculationPanel.add(costValLabel, gridConstraintsInfo);

        // length string label
        gridConstraintsInfo.weightx = 1;
        gridConstraintsInfo.weighty = 0.1;
        gridConstraintsInfo.gridx = 0;
        gridConstraintsInfo.gridy = 1;
        gridConstraintsInfo.anchor = GridBagConstraints.LINE_START;
        calculationPanel.add(lengthStrLabel, gridConstraintsInfo);

        // length value label
        gridBagConstraints(1.0, 0.1, 1, 1, gridConstraintsInfo);
        calculationPanel.add(lengthValLabel, gridConstraintsInfo);

        // panel info
        gridBagConstraints(1.0, 0.2, 0, 12, gridConstraints);
        jPanel.add(calculationPanel, gridConstraints);

        startBtn.addActionListener(e -> pathFinding.start(searchString));
        resetBtn.addActionListener(e -> pathFinding.reset(isDiagonal));
        mazeBtn.addActionListener(e -> pathFinding.mazeSelection(mazeString));

        algorithmCombo.addItemListener(this);
        mazeCombo.addItemListener(this);
        sizeSlider.setMajorTickSpacing(50);
        delaySlider.setMajorTickSpacing(3);

        sizeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int size = source.getValue();
                pathFinding.changeSizeOfGrid(size);
            }

        });
        delaySlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int speed = source.getValue();
                pathFinding.changeVelocity(speed);
            }

        });

        diagonalAction();
        add(jPanel, BorderLayout.WEST);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == algorithmCombo) {
            searchString = (String) e.getItem();
        }

        if (e.getSource() == mazeCombo) {
            mazeString = (String) e.getItem();
        }
    }

    private void gridBagConstraints(double weightx, double weighty, int gridx, int gridy, GridBagConstraints gridBagConstraints) {
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
    }


    private void diagonalAction() {
        diagonalCheckbox.addActionListener(e -> {
            if (diagonalCheckbox.isSelected()) {
                isDiagonal = true;
                pathFinding.reset(isDiagonal);
            } else {
                isDiagonal = false;
                pathFinding.reset(isDiagonal);
            }
        });
    }
}