//
//  CalculatorController.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;
import java.util.ArrayList;
import java.util.List;

public class CalculatorController {

    CalculatorView myView;
    CalculatorEngine myEngine;
    CustomListener myListener;
    private String firstNumber = "";
    private String operator = "";
    private String secondNumber = "";
    private boolean operatorPressed = false;
    private boolean justCalculated = false;
    private boolean operatorJustPressed = false;

    public CalculatorController() {
        myEngine = new CalculatorEngine();
        myView = new CalculatorView();
        myListener = new CustomListener();
        SwingUtilities.invokeLater(() -> {
            myView.setButtonListener(myListener);
        });
    }

    List<String> history = new ArrayList<>();

    private class CustomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SoundPlayer.playSound("button-click.mp3");
            String actionSource = e.getActionCommand();

            // Handle numeric input (0-9) and decimal point
            // Append the digit or decimal point to the current display text
            if (actionSource.matches("[0-9.]")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();

                    // If we just calculated, start fresh with this number
                    if (justCalculated || operatorJustPressed) {
                        pane.setText(actionSource);
                        justCalculated = false;
                        operatorJustPressed = false;
                    } else {
                        pane.setText(currentText + actionSource);
                    }
                }

            } else if (actionSource.equals("AC")) {
                // All Clear - reset the display to empty string
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) pane.setText("");
                firstNumber = "";
                operator = "";
                secondNumber = "";
                operatorPressed = false;
                justCalculated = false;
                operatorJustPressed = false;
            } else if (actionSource.equals("DEL")) {
                // Delete - remove the last character from the display
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();
                    if (!currentText.isEmpty()) {
                        pane.setText(currentText.substring(0, currentText.length() - 1));
                    }
                }
            } else if (actionSource.equals("THEME")) {
                // Theme Toggle - switch between light and dark mode
                myView.toggleTheme();
            } else if (actionSource.equals("=")) {
                // Evaluate the complete expression when equals is pressed
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null && !firstNumber.isEmpty() && !operator.isEmpty() && !operatorJustPressed) {
                    secondNumber = pane.getText();
                    if (!secondNumber.isEmpty()) {
                        // Build complete expression and evaluate
                        String expression = firstNumber + operator + secondNumber;
                        String result = CalculatorEngine.evaluateExpression(expression);
                        // Save full expression to history before overwriting
                        history.add(firstNumber + " " + operator + " " + secondNumber + " = " + result);
                        pane.setText(result);
                        // Prepare for next calculation - the result becomes the first number
                        firstNumber = result;
                        operator = "";
                        secondNumber = "";
                        operatorPressed = false;
                        justCalculated = true; // Flag that we just calculated
                    }
                }

                history.add(actionSource);
                System.out.println(history);
                return; // already added to history above

            } else if (actionSource.equals("+/-")) {
                // Toggle negative sign on current display
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();
                    if (currentText != null && !currentText.isEmpty() && !currentText.equals("0")) { // Remove negative sign
                        if (currentText.startsWith("-")) {
                            pane.setText(currentText.substring(1));
                        } else {
                            try {
                                double val = Double.parseDouble(currentText.trim());
                                pane.setText(String.valueOf(val * -1));
                            } catch (NumberFormatException n) { // Handle error (e.g., clear pane or show error)
                                pane.setText("error: negative sign");
                            }
                        }
                    }
                }

            } else if (actionSource.equals("%")) {
                // Modulo operation - convert to % operator
                JTextPane pane = myView.getActiveDisplayPane();
                String currenDisplay = pane != null ? pane.getText() : "";
                // Case 1: Operator pressed while a second number is already entered (chain calculation)
                if (operatorPressed && !currenDisplay.isEmpty()) { // Calculate the current operation first
                    secondNumber = currenDisplay;
                    String result = CalculatorEngine.evaluateExpression(firstNumber + operator + secondNumber); // Use result as first number for next operation
                    firstNumber = result;
                    operator = "%";
                    operatorPressed = true; // Clear display for next number
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " %");
                    operatorJustPressed = true;
                } else if (justCalculated && !currenDisplay.isEmpty()) {
                    // Case 2: If we just calculated and operator is pressed, use the result as first number
                    firstNumber = currenDisplay;
                    operator = "%";
                    operatorPressed = true; // Clear display for second number
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " %");
                    operatorJustPressed = true;
                } else if (!currenDisplay.isEmpty() && !operatorPressed) {
                    // Case 3: Normal case - first number entered, no operator yet
                    firstNumber = currenDisplay; // Store the first number and operator
                    operator = "%";
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " %");
                    operatorJustPressed = true; // Clear display for second number
                }

            } else if (actionSource.equals(".")) {
                //shows "."
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) pane.setText(".");
            } else if (actionSource.equals("CONVERT")) {
                // switches panels to convert
                myView.showConverter();
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();

                    // If we just calculated, start fresh with this number
                    if (justCalculated || operatorJustPressed) {
                        pane.setText(actionSource);
                        justCalculated = false;
                        operatorJustPressed = false;
                    } else {
                        pane.setText(currentText + actionSource);
                    }
                }



            } else if (actionSource.equals("SIMPLE")) {
                // switches panels to simple
                myView.showSimpleCalculator();
            } else if (actionSource.equals("TIP")) {
                // switches panels to tip
                myView.showTipCalculator();
            } else if (actionSource.equals("EXIT")) {
                // closes the app
                System.exit(0);
            } else if (actionSource.equals("HIST")) {
                //shows the history of numbers and operators used
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    if (history.isEmpty()) { // if no numbers or operators pressed, then it will tell the user that
                        pane.setText("No history yet");
                    } else {
                        pane.setText(String.join("\n", history));
                    }
                }

            } else {
                // Operators only: +, -, /, X
                JTextPane pane = myView.getActiveDisplayPane();
                String currentText = pane != null ? pane.getText() : "";
                if (operatorPressed && !currentText.isEmpty()) { // Case 1: Operator pressed while a second number is already entered (chain calculation)
                    secondNumber = currentText; // Calculate the current operation first
                    String result = CalculatorEngine.evaluateExpression(firstNumber + operator + secondNumber);
                    firstNumber = result; // Use result as first number for next operation
                    operator = actionSource;
                    operatorPressed = true; // Clear display for next number
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " " + actionSource);
                    operatorJustPressed = true;
                } else if (justCalculated && !currentText.isEmpty()) {  // Case 2: If we just calculated and operator is pressed, use the result as first number
                    firstNumber = currentText;
                    operator = actionSource;
                    operatorPressed = true;  // Clear display for second number
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " " + actionSource);
                    operatorJustPressed = true;
                } else if (!currentText.isEmpty() && !operatorPressed) { // Case 3: Normal case - first number entered, no operator yet
                    firstNumber = currentText;   // Store the first number and operator
                    operator = actionSource;
                    operatorPressed = true; // Clear display for second number
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " " + actionSource);
                    operatorJustPressed = true;
                }
            }
            // Track user input for calculation processing
            history.add(actionSource);
            System.out.println(history);
        }
    }
}