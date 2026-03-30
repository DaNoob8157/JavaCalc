//
//  CalculatorView.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.SystemColor.text;

/**
 * TODO
 *
 * - Extend the view to include the converter
 * - Extend to incorporate different views for scientific, converter, & other
 * calculator features
 *
 * */
public class CalculatorView extends JFrame {
    //hola

    private ActionListener btnLstnr;
    private JPanel panel, btnPanel;
    static JTextPane displayPane;
    private JScrollPane scrollPane;
    private String[] btnTextArray = {"DEL","AC","+/-","%","7","8","9","X",
            "4","5","6","-","1","2","3","+",".","0","=", "/"};

    /**
     * Boilerplate constructor to create view
     * */

    public CalculatorView() {

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });

        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(500, 520);
    }

    /**
     * Custom constructor.
     * Can be modified anyway you see fit.
     * You can also change the parameters as you see fit.
     * */
    public CalculatorView (int width, int height){
        setSize(width, height);
    }

    public void setButtonListener(ActionListener lstnr){
        btnLstnr = lstnr;
        if (btnPanel != null) {
            for (Component c : btnPanel.getComponents()) {
                if (c instanceof JButton) {
                    ((JButton) c).addActionListener(btnLstnr);
                }
            }
        }
    }

    private void addComponentsToPane(){
        // Create main panel to contain display & buttons
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        /* === ROW 0: TEXT FIELD FOR CALCULATOR === */
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Create a text pane to act as a display for the calculator
        displayPane = new JTextPane();
        displayPane.setFont(new Font("Arial", Font.TRUETYPE_FONT, 40));
        displayPane.setEditable(false);
        displayPane.setOpaque(true);
        displayPane.setBackground(Color.BLACK);
        displayPane.setForeground(Color.WHITE);
        displayPane.setCaretColor(Color.WHITE);

        scrollPane = new JScrollPane(displayPane);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.BLACK);

        panel.add(scrollPane, gbc);

        // Create an empty label for spacing purposes
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBackground(Color.BLACK);
        lblNewLabel.setOpaque(true);
        panel.add(lblNewLabel, gbc);

        /* === ROW 2: Button Pad === */
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.05;

        // Create button grid and add buttons
        btnPanel = new JPanel(new GridLayout(5,5,0,0));
        btnPanel.setBackground(Color.BLACK);
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            if (btnLstnr != null) {
                button.addActionListener(btnLstnr);
            }
            
            button.setBackground(new Color(50, 50, 50));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            // Special color for operators and special buttons
            if (text.matches("[ACDEL+/\\-%X=]")) {
                button.setBackground(new Color(80, 80, 80));
            }
            
            btnPanel.add(button);
        }
        panel.add(btnPanel, gbc);

        add(panel);
    }

    public void createAndShowGUI(){
        addComponentsToPane();
    }

    public static void updateDisplay(String text) {
        displayPane.setText(text);
    }


    /* Main method to run and view boiler plate code */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           CalculatorView myCalculatorView = new CalculatorView();
        });
    }
}