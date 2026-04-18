//
//  CalculatorView.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class CalculatorView extends JFrame {

    private ActionListener btnLstnr;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    static JTextPane displayPane;
    private boolean isDarkMode = true;
    
    // Card names
    public static final String SIMPLE_CALC = "SimpleCalc";
    public static final String TIP_VIEW = "TipView";
    public static final String CONVERTER_VIEW = "ConverterView";
    
    // View references
    private SimpleCalculatorView simpleCalcView;
    private TipView tipView;
    private ConverterView converterView;


    /**
     * Boilerplate constructor to create view
     * */

    public CalculatorView() {

        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 520);
        
        // Ensure GUI creation and updates happen on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
            setVisible(true);
        });

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
        // Add listener to all cards when they exist
        if (simpleCalcView != null) {
            simpleCalcView.setButtonListener(lstnr);
        }
        if (tipView != null) {
            tipView.setButtonListener(lstnr);
        }
        if (converterView != null) {
            converterView.setButtonListener(lstnr);
        }
    }
    
    // Get the display pane from the currently active view
    public JTextPane getActiveDisplayPane() {
        if (simpleCalcView != null && simpleCalcView.isVisible()) {
            return simpleCalcView.getDisplayPane();
        } else if (tipView != null && tipView.isVisible()) {
            return tipView.getDisplayPane();
        } else if (converterView != null && converterView.isVisible()) {
            return converterView.getDisplayPane();
        }
        // Default to simple calc view if nothing is visible
        return simpleCalcView != null ? simpleCalcView.getDisplayPane() : null;
    }


    public void createAndShowGUI(){
        // Initialize CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Create view cards
        simpleCalcView = new SimpleCalculatorView();
        tipView = new TipView();
        converterView = new ConverterView();
        
        // Set button listeners for all views
        if (btnLstnr != null) {
            simpleCalcView.setButtonListener(btnLstnr);
            tipView.setButtonListener(btnLstnr);
            converterView.setButtonListener(btnLstnr);
        }
        
        // Add cards to card panel
        cardPanel.add(simpleCalcView, SIMPLE_CALC);
        cardPanel.add(tipView, TIP_VIEW);
        cardPanel.add(converterView, CONVERTER_VIEW);
        
        // Set the card panel as the main content pane
        this.setContentPane(cardPanel);
        
        // Show the simple calculator view by default
        cardLayout.show(cardPanel, SIMPLE_CALC);
        
        updateTheme();
    }

    // Toggles between dark mode and light mode themes.
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        updateTheme();
    }

    // Updates all UI components to match the current theme (dark or light mode).
    private void updateTheme() {
        Color bgColor = isDarkMode ? CalcColors.BACKGROUND : CalcColors.LIGHT_BACKGROUND;
        
        // Apply theme to all cards
        if (simpleCalcView != null) {
            simpleCalcView.applyTheme(isDarkMode);
        }
        if (tipView != null) {
            tipView.applyTheme(isDarkMode);
        }
        if (converterView != null) {
            converterView.applyTheme(isDarkMode);
        }
        
        if (cardPanel != null) {
            cardPanel.setBackground(bgColor);
        }
    }
    
    // Methods to switch between views
    public void showSimpleCalculator() {
        cardLayout.show(cardPanel, SIMPLE_CALC);
    }
    
    public void showTipCalculator() {
        cardLayout.show(cardPanel, TIP_VIEW);
    }
    
    public void showConverter() {
        cardLayout.show(cardPanel, CONVERTER_VIEW);
    }

    public TipView getTipView() {
        return tipView;
    }

    public ConverterView getConverterView() {
        return converterView;
    }

}

