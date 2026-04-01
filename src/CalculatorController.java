//
//  CalculatorController.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the calculator application.
 * Manages interaction between the view and engine components.
 */
public class CalculatorController {
    CalculatorView myView;
    CalculatorEngine myEngine;
    CustomListener myListener;


    /**
     * Constructor initializes the calculator components and sets up listeners.
     */
    public CalculatorController() {

        myEngine = new CalculatorEngine();
        myView = new CalculatorView();
        myListener = new CustomListener();

        myView.setButtonListener(myListener);
    }

    List<String> userInput = new ArrayList<>();

    private class CustomListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            SoundPlayer.playSound("button-click.mp3");
            String actionSource = e.getActionCommand();

            // Handle numeric input (0-9) and decimal point
            // Append the digit or decimal point to the current display text
            if (actionSource.matches("[0-9.]")) {
                String currentText = CalculatorView.displayPane.getText();
                CalculatorView.displayPane.setText(currentText + actionSource);
            } else if (actionSource.equals("AC")) {
                // All Clear - reset the display to empty string
                CalculatorView.displayPane.setText("");
            } else if (actionSource.equals("DEL")) {
                // Delete - remove the last character from the display
                String currentText = CalculatorView.displayPane.getText();
                if (!currentText.isEmpty()) {
                    CalculatorView.displayPane.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (actionSource.equals("THEME")) {
                // Theme Toggle - switch between light and dark mode
                myView.toggleTheme();
            } else {
                // Placeholder for other operations
                System.out.println("Operation: " + actionSource);
            }

            // Track user input for calculation processing
            userInput.add(actionSource);
            System.out.println(userInput);

            switch (actionSource) {

                case "+":
                    // TODO: Implement addition operation
                    // add case a and b together
                    CalculatorEngine.evaluateExpression("+");
                    break;

                case "-":
                    // subtract case a and b
                    CalculatorEngine.evaluateExpression("-");
                    break;

                case "/":
                    // divide case a and b
                    CalculatorEngine.evaluateExpression("/");
                    break;

                case "X":
                    // multiply case a and b
                    CalculatorEngine.evaluateExpression("X *");
                    break;

                case "+/-":
                    // Get the current text properly
                    String currentText = CalculatorView.displayPane.getText();
                    if (currentText != null && !currentText.isEmpty() && !currentText.equals("0")) {
                        if (currentText.startsWith("-")) {
                            // Remove negative sign
                            CalculatorView.updateDisplay(currentText.substring(1));
                        } else {
                            // Add negative sign
                            CalculatorView.updateDisplay("-" + currentText);
                        }
                    }
                    break;

                case "=":
                    // TODO: Implement equals operation to evaluate expression
                    String result = CalculatorEngine.evaluateExpression("=");
                    CalculatorView.updateDisplay(result);
                    break;

            }
        }
    }
}
