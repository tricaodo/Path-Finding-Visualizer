import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class MainFrame extends JFrame implements ItemListener {
    private final int WIDTH = 700;
    private final int HEIGHT = 450;
    private final String[] strCombo = {"Breadth First Search",
            "Depth First Search", "Dijkstra"};
    private JComboBox comboBox = new JComboBox(strCombo);
    private String optionString = "Breadth First Search";


    private final Grid grid = new Grid();

    public MainFrame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setTitle("Path Finding");
        setResizable(false);
        setLayout(new BorderLayout());

        add(grid, BorderLayout.CENTER);
        topLayout();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }


    private void topLayout() {
        JPanel jPanel = new JPanel();
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 0);
        jPanel.setBorder(outerBorder);
        Dimension dimension = new Dimension();
        dimension.width = 250;
        jPanel.setPreferredSize(dimension);
        JButton start = new JButton("Start");
        JButton reset = new JButton("Reset");
        start.setPreferredSize(new Dimension(200, 20));
        start.setBounds(0,25, 120, 25);
        reset.setPreferredSize(new Dimension(100, 20));

        reset.setPreferredSize(dimension);
        JLabel algorithmLabel = new JLabel("Algorithm");

        jPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.1;

        // Label algorithm
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.fill = GridBagConstraints.NONE;
        gridConstraints.anchor = GridBagConstraints.LINE_START;
        jPanel.add(algorithmLabel, gridConstraints);

        // Dropbox algorithm
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.LINE_END;
        jPanel.add(comboBox, gridConstraints);


        //************* Button Panel *************//
        // Button Start
//        gridConstraints.weightx = 0.1;
//        gridConstraints.weighty = 1;
//        gridConstraints.gridx = 0;
//        gridConstraints.gridy = 1;
////        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
//        jPanel.add(start, gridConstraints);

//
//        // Button reset
//        gridConstraints.gridx = 1;
//        gridConstraints.gridy = 2;
////        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
//        jPanel.add(reset, gridConstraints);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(start);
        start.addActionListener(e -> grid.start(optionString));
        btnPanel.add(reset);
        reset.addActionListener(e -> grid.reset());

        gridConstraints.weightx = 0.1;
        gridConstraints.weighty = 2.0;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
//        gridConstraints.anchor = GridBagConstraints.CENTER;
        jPanel.add(btnPanel, gridConstraints);

        comboBox.addItemListener(this);
        add(jPanel, BorderLayout.WEST);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            optionString = (String) e.getItem();
        }
    }
}