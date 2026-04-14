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

    private ActionListener btnLstnr;
    private JPanel panel, btnPanel;
    static JTextPane displayPane;
    private JScrollPane scrollPane;
    private String[] btnTextArray = {"THEME","DEL","AC","HIST","=","1","2","3","4","X",
            "5","6","7","8","/","9","0",".","-","+","+/-","%","CONVERT", "TIP", "EXIT"};
    private boolean isDarkMode = true;
    private LayoutManager CardLayout;


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


    public void createAndShowGUI(){
        updateTheme();
    }

    // Toggles between dark mode and light mode themes.
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        updateTheme();
    }

    // Updates all UI components to match the current theme (dark or light mode).
    private void updateTheme() {
        Color bgColor = isDarkMode ? Color.BLACK : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;
        Color btnBgColor = isDarkMode ? new Color(50, 50, 50) : new Color(220, 220, 220);
        Color specialBtnBgColor = isDarkMode ? new Color(80, 80, 80) : new Color(180, 180, 180);
        Color borderColor = isDarkMode ? Color.BLACK : Color.LIGHT_GRAY;

        panel.setBackground(bgColor);
        displayPane.setBackground(bgColor);
        displayPane.setForeground(fgColor);
        displayPane.setCaretColor(fgColor);
        scrollPane.getViewport().setBackground(bgColor);


        if (btnPanel != null) {
            btnPanel.setBackground(bgColor);
            for (Component c : btnPanel.getComponents()) {
                if (c instanceof JButton) {
                    JButton button = (JButton) c;
                    String text = button.getText();
                    if (text.matches("[ACDEL+/\\-%X=]|THEME")) {
                        button.setBackground(specialBtnBgColor);
                    } else {
                        button.setBackground(btnBgColor);
                    }
                    button.setForeground(fgColor);
                    button.setBorder(BorderFactory.createLineBorder(borderColor));
                }
            }
        }
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

