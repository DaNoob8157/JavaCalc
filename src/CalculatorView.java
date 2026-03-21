//
//  CalculatorView.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TODO
 *
 * - Extend the view to include the converter
 * - Extend to incorporate different views for scientific, converter, & other
 * calculator features
 *
 * */
public class CalculatorView extends JFrame {
    private static final String DEFAULT_THEME_PATH = "src/Styling/style-dark.css";

    private ActionListener btnLstnr;
    private final String[] btnTextArray = {"DEL","AC","+/-","%","7","8","9","X",
            "4","5","6","-","1","2","3","+",".","0","=", "/"};
    private final StyleManager styleManager;

    /**
     * Boilerplate constructor to create view
     * */

    public CalculatorView() {
        styleManager = new StyleManager(DEFAULT_THEME_PATH);

        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createAndShowGUI();
        setSize(500, 520);
        setVisible(true);
    }


    /**
     * Custom constructor.
     * Can be modified anyway you see fit.
     * You can also change the parameters as you see fit.
     * */
    public CalculatorView (int width, int height){
        styleManager = new StyleManager(DEFAULT_THEME_PATH);
        setSize(width, height);
    }

    public void setButtonListener(ActionListener lstnr){
        btnLstnr = lstnr;
    }

    private void addComponentsToPane(){
        // Create main panel to contain display & buttons
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        /* === ROW 0: TEXT FIELD FOR CALCULATOR === */
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Create a text pane to act as a display for the calculator
        JTextPane displayPane = new JTextPane();
        displayPane.setFont(new Font("Arial", Font.PLAIN, 40));
        displayPane.setEditable(true);
        displayPane.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(displayPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, gbc);


        /* === ROW 2: Button Pad === */
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.05;

        // Create button grid and add buttons
        JPanel btnPanel = new JPanel(new GridLayout(5,5,0,0));
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            button.addActionListener(btnLstnr);
            styleManager.styleButton(button, ".button");
            btnPanel.add(button);
        }

        // This 
        styleManager.stylePanel(panel, ".root");
        styleManager.stylePanel(btnPanel, ".root");
        styleManager.styleTextPane(displayPane, ".root");
        Color rootColor = styleManager.getParser().getBackgroundColor(".root");
        if (rootColor != null) {
            scrollPane.getViewport().setBackground(rootColor);
        }

        panel.add(btnPanel, gbc);

        add(panel);
    }

    public void createAndShowGUI(){
        addComponentsToPane();
    }

    /* Main method to run and view boiler plate code */
    static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorView::new);
    }
}
