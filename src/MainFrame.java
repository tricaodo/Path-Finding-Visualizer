import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class MainFrame extends JFrame implements ItemListener {
    private final int WIDTH = 710;
    private final int HEIGHT = 530;

    private final String[] algorithmArr = {"Breadth First Search",
            "Depth First Search", "Dijkstra", "A*"};
    private final String[] mazeArr = {"Prim's Algorithm", "Kruskal's Algorithm"};

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
        JButton startBtn = new JButton("        Start        ");
        JButton resetBtn = new JButton("        Reset        ");
        JButton mazeBtn = new JButton("  Generate Maze  ");
        JSlider sizeSlider = new JSlider(1, 5, 2);

        Border innerBorder = BorderFactory.createTitledBorder("Path Finding Visualizer");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        jPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        GridBagConstraints gridConstraints = new GridBagConstraints();

        // Label algorithm
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.fill = GridBagConstraints.NONE;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(algorithmLabel, gridConstraints);

        // Dropbox algorithm
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(algorithmCombo, gridConstraints);

        // Maze label
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(mazeLabel, gridConstraints);

        // Maze combo
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(mazeCombo, gridConstraints);

        // Button generate maze
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 4;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(mazeBtn, gridConstraints);

        // Size label
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 5;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(sizeLabel, gridConstraints);

        // Size slider
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 6;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(sizeSlider, gridConstraints);

        // Button Start
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 7;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(startBtn, gridConstraints);


        // Button resetBtn
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 8;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(resetBtn, gridConstraints);

        // Diagonal select box
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 9;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(diagonalCheckbox, gridConstraints);

        //=============== Calculation of algorithm =================//
        JPanel calculationPanel = new JPanel(new GridBagLayout());
        calculationPanel.setPreferredSize(new Dimension(200, 90));
        JLabel costStrLabel = new JLabel("   Cost$: ");
        JLabel lengthStrLabel = new JLabel("   Length: ");

        innerBorder = BorderFactory.createTitledBorder("Info");
        outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
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
        gridConstraintsInfo.weightx = 1;
        gridConstraintsInfo.weighty = 0.1;
        gridConstraintsInfo.gridx = 1;
        gridConstraintsInfo.gridy = 0;
        gridConstraintsInfo.anchor = GridBagConstraints.CENTER;
        calculationPanel.add(costValLabel, gridConstraintsInfo);

        // length string label
        gridConstraintsInfo.weightx = 1;
        gridConstraintsInfo.weighty = 0.1;
        gridConstraintsInfo.gridx = 0;
        gridConstraintsInfo.gridy = 1;
        gridConstraintsInfo.anchor = GridBagConstraints.LINE_START;
        calculationPanel.add(lengthStrLabel, gridConstraintsInfo);

        // length value label
        gridConstraintsInfo.weightx = 1;
        gridConstraintsInfo.weighty = 0.1;
        gridConstraintsInfo.gridx = 1;
        gridConstraintsInfo.gridy = 1;
        gridConstraintsInfo.anchor = GridBagConstraints.CENTER;
        calculationPanel.add(lengthValLabel, gridConstraintsInfo);

        // panel info
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.2;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 10;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(calculationPanel, gridConstraints);

        startBtn.addActionListener(e -> pathFinding.start(searchString));
        resetBtn.addActionListener(e -> pathFinding.reset(isDiagonal));

        algorithmCombo.addItemListener(this);
        diagonalCheckbox.addActionListener(e -> {
            if(diagonalCheckbox.isSelected()){
                isDiagonal = true;
                pathFinding.reset(isDiagonal);
            }else{
                isDiagonal = false;
                pathFinding.reset(isDiagonal);
            }
        });
        add(jPanel, BorderLayout.WEST);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            searchString = (String) e.getItem();

        }


    }
    private void checkBoxPerform(){

    }
}