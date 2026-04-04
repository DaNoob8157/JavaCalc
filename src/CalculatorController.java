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

    // Expression tracking for calculation
    private String firstNumber = "";
    private String operator = "";
    private String secondNumber = "";
    private boolean operatorPressed = false;
    private boolean justCalculated = false;
    private boolean operatorJustPressed = false;

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
                // If we just calculated, start fresh with this number
                if (justCalculated || operatorJustPressed) {
                    CalculatorView.displayPane.setText(actionSource);
                    justCalculated = false;
                    operatorJustPressed = false;
                } else {
                    CalculatorView.displayPane.setText(currentText + actionSource);
                }
            } else if (actionSource.equals("AC")) {
                // All Clear - reset the display to empty string
                CalculatorView.displayPane.setText("");
                firstNumber = "";
                operator = "";
                secondNumber = "";
                operatorPressed = false;
                justCalculated = false;
            } else if (actionSource.equals("DEL")) {
                // Delete - remove the last character from the display
                String currentText = CalculatorView.displayPane.getText();
                if (!currentText.isEmpty()) {
                    CalculatorView.displayPane.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (actionSource.equals("THEME")) {
                // Theme Toggle - switch between light and dark mode
                myView.toggleTheme();
            } else if (actionSource.equals("=")) {
                // Evaluate the complete expression when equals is pressed
                if (!firstNumber.isEmpty() && !operator.isEmpty() && !operatorJustPressed) {
                    secondNumber = CalculatorView.displayPane.getText();
                    if (!secondNumber.isEmpty()) {
                        // Build complete expression and evaluate
                        String expression = firstNumber + operator + secondNumber;
                        String result = CalculatorEngine.evaluateExpression(expression);
                        CalculatorView.updateDisplay(result);

                        // Prepare for next calculation - the result becomes the first number
                        firstNumber = result;
                        operator = "";
                        secondNumber = "";
                        operatorPressed = false;
                        justCalculated = true;  // Flag that we just calculated
                    }
                }
            } else if (actionSource.equals("+/-")) {
                // Toggle negative sign on current display
                String currentText = CalculatorView.displayPane.getText();
                if (currentText != null && !currentText.isEmpty() && !currentText.equals("0")) {
                    if (currentText.startsWith("-")) {
                        // Remove negative sign
                        CalculatorView.updateDisplay(currentText.substring(1));
                    } else {
                        try {
                            String text = CalculatorView.displayPane.getText().trim();
                            if (!text.isEmpty()) {
                                double val = Double.parseDouble(text);
                                val = val * -1;
                                CalculatorView.displayPane.setText(String.valueOf(val));
                            }
                        } catch (NumberFormatException n) {
                            // Handle error (e.g., clear pane or show error)
                            CalculatorView.displayPane.setText("error: negative sign");
                        }
                    }
                }
            } else if (actionSource.equals("%")) {
                // Modulo operation - convert to % operator
                String currentText = CalculatorView.displayPane.getText();

                // Case 1: Operator pressed while a second number is already entered (chain calculation)
                if (operatorPressed && !currentText.isEmpty()) {
                    // Calculate the current operation first
                    secondNumber = currentText;
                    String expression = firstNumber + operator + secondNumber;
                    String result = CalculatorEngine.evaluateExpression(expression);

                    // Use result as first number for next operation
                    firstNumber = result;
                    operator = "%";
                    operatorPressed = true;
                    justCalculated = false;
                    CalculatorView.displayPane.setText(firstNumber + " " + actionSource + secondNumber);
                    operatorJustPressed = true;  // Clear display for next number
                }
                // Case 2: If we just calculated and operator is pressed, use the result as first number
                else if (justCalculated && !currentText.isEmpty()) {
                    firstNumber = currentText;
                    operator = "%";
                    operatorPressed = true;
                    justCalculated = false;
                    CalculatorView.displayPane.setText(firstNumber + " " + actionSource + secondNumber);
                    operatorJustPressed = true;  // Clear display for second number
                }
                // Case 3: Normal case - first number entered, no operator yet
                else if (!currentText.isEmpty() && !operatorPressed) {
                    // Store the first number and operator
                    firstNumber = currentText;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    CalculatorView.displayPane.setText(firstNumber + " " + actionSource + secondNumber); // show it!
                    operatorJustPressed = true; // Clear display for second number
                }
            } else {
                // Handle operators: +, -, /, X
                String currentText = CalculatorView.displayPane.getText();

                // Case 1: Operator pressed while a second number is already entered (chain calculation)
                if (operatorPressed && !currentText.isEmpty()) {
                    // Calculate the current operation first
                    secondNumber = currentText;
                    String expression = firstNumber + operator + secondNumber;
                    String result = CalculatorEngine.evaluateExpression(expression);

                    // Use result as first number for next operation
                    firstNumber = result;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    CalculatorView.displayPane.setText(firstNumber + " " + actionSource + secondNumber);
                    operatorJustPressed = true;  // Clear display for next number
                }
                // Case 2: If we just calculated and operator is pressed, use the result as first number
                else if (justCalculated && !currentText.isEmpty()) {
                    firstNumber = currentText;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    CalculatorView.displayPane.setText(firstNumber + " " + actionSource + secondNumber);
                    operatorJustPressed = true;  // Clear display for second number
                }
                // Case 3: Normal case - first number entered, no operator yet
                else if (!currentText.isEmpty() && !operatorPressed) {
                    // Store the first number and operator
                    firstNumber = currentText;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    CalculatorView.displayPane.setText(firstNumber + " " + actionSource + secondNumber);
                    operatorJustPressed = true; // Clear display for second number
                }
            }

            // Track user input for calculation processing
            userInput.add(actionSource);
            System.out.println(userInput);
        }
    }
}
