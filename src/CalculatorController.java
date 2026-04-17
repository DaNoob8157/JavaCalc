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
            if (actionSource.matches("[0-9.]")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();
                    if (justCalculated || operatorJustPressed) {
                        pane.setText(actionSource);
                        justCalculated = false;
                        operatorJustPressed = false;
                    } else {
                        pane.setText(currentText + actionSource);
                    }
                }
            } else if (actionSource.equals("AC")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) pane.setText("");
                firstNumber = "";
                operator = "";
                secondNumber = "";
                operatorPressed = false;
                justCalculated = false;
                operatorJustPressed = false;
            } else if (actionSource.equals("DEL")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();
                    if (!currentText.isEmpty()) {
                        pane.setText(currentText.substring(0, currentText.length() - 1));
                    }
                }
            } else if (actionSource.equals("THEME")) {
                myView.toggleTheme();
            } else if (actionSource.equals("=")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null && !firstNumber.isEmpty() && !operator.isEmpty() && !operatorJustPressed) {
                    secondNumber = pane.getText();
                    if (!secondNumber.isEmpty()) {
                        String expression = firstNumber + operator + secondNumber;
                        String result = CalculatorEngine.evaluateExpression(expression);
                        // Save full expression to history before overwriting
                        history.add(firstNumber + " " + operator + " " + secondNumber + " = " + result);
                        pane.setText(result);
                        firstNumber = result;
                        operator = "";
                        secondNumber = "";
                        operatorPressed = false;
                        justCalculated = true;
                    }
                }
                history.add(actionSource);
                System.out.println(history);
                return; // already added to history above
            } else if (actionSource.equals("+/-")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    String currentText = pane.getText();
                    if (currentText != null && !currentText.isEmpty() && !currentText.equals("0")) {
                        if (currentText.startsWith("-")) {
                            pane.setText(currentText.substring(1));
                        } else {
                            try {
                                double val = Double.parseDouble(currentText.trim());
                                pane.setText(String.valueOf(val * -1));
                            } catch (NumberFormatException n) {
                                pane.setText("error: negative sign");
                            }
                        }
                    }
                }
            } else if (actionSource.equals("%")) {
                JTextPane pane = myView.getActiveDisplayPane();
                String currenDisplay = pane != null ? pane.getText() : "";
                if (operatorPressed && !currenDisplay.isEmpty()) {
                    secondNumber = currenDisplay;
                    String result = CalculatorEngine.evaluateExpression(firstNumber + operator + secondNumber);
                    firstNumber = result;
                    operator = "%";
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " %");
                    operatorJustPressed = true;
                } else if (justCalculated && !currenDisplay.isEmpty()) {
                    firstNumber = currenDisplay;
                    operator = "%";
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " %");
                    operatorJustPressed = true;
                } else if (!currenDisplay.isEmpty() && !operatorPressed) {
                    firstNumber = currenDisplay;
                    operator = "%";
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " %");
                    operatorJustPressed = true;
                }
            } else if (actionSource.equals(".")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) pane.setText(".");
            } else if (actionSource.equals("CONVERT")) {
                myView.showConverter();
            } else if (actionSource.equals("TIP")) {
                myView.showTipCalculator();
            } else if (actionSource.equals("EXIT")) {
                System.exit(0);
            } else if (actionSource.equals("HIST")) {
                JTextPane pane = myView.getActiveDisplayPane();
                if (pane != null) {
                    if (history.isEmpty()) {
                        pane.setText("No history yet");
                    } else {
                        pane.setText(String.join("\n", history));
                    }
                }
            } else {
                // Operators only: +, -, /, X
                JTextPane pane = myView.getActiveDisplayPane();
                String currentText = pane != null ? pane.getText() : "";
                if (operatorPressed && !currentText.isEmpty()) {
                    secondNumber = currentText;
                    String result = CalculatorEngine.evaluateExpression(firstNumber + operator + secondNumber);
                    firstNumber = result;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " " + actionSource);
                    operatorJustPressed = true;
                } else if (justCalculated && !currentText.isEmpty()) {
                    firstNumber = currentText;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " " + actionSource);
                    operatorJustPressed = true;
                } else if (!currentText.isEmpty() && !operatorPressed) {
                    firstNumber = currentText;
                    operator = actionSource;
                    operatorPressed = true;
                    justCalculated = false;
                    if (pane != null) pane.setText(firstNumber + " " + actionSource);
                    operatorJustPressed = true;
                }
            }
            history.add(actionSource);
            System.out.println(history);
        }
    }
}