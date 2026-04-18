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

    // State variables to track current mode and inputs for each mode
    private enum ActiveMode { SIMPLE, TIP, CONVERTER }
    private ActiveMode activeMode = ActiveMode.SIMPLE;

    // Simple calculator state
    private String firstNumber = "";
    private String operator = "";
    private String secondNumber = "";
    private boolean operatorPressed = false;
    private boolean justCalculated = false;
    private boolean operatorJustPressed = false;

    // Tip calculator state
    // Dedicated fields are managed by TipView directly now

    // Converter state is now managed directly by ConverterView

    public CalculatorController() {
        myEngine = new CalculatorEngine();
        myView = new CalculatorView();
        myListener = new CustomListener();
        SwingUtilities.invokeLater(() -> {
            myView.setButtonListener(myListener);
        });
    }

    List<String> history = new ArrayList<>();
    private boolean historyVisible = false;

    // Method to switch active mode and reset relevant state variables
    private void switchToMode(ActiveMode mode) {
        activeMode = mode;
        justCalculated = false;
        operatorJustPressed = false;
        operatorPressed = false;
        firstNumber = "";
        operator = "";
        secondNumber = "";

        // Clear the display when switching modes
        JTextPane pane = myView.getActiveDisplayPane();
        if (pane != null) {
            pane.setText("");
        }
        historyVisible = false;
        if (mode == ActiveMode.CONVERTER && myView.getConverterView() != null) {
            myView.getConverterView().clearConverterFields();
        }
    }

    // Method to clear history if it's currently visible when a new action is taken
    private void clearHistoryIfVisible(JTextPane pane) {
        if (historyVisible && pane != null) {
            pane.setText("");
            historyVisible = false;
        }
    }

    // Helper methods to determine action types and handle them accordingly
    private boolean isSimpleOperator(String action) {
        return action.equals("+") || action.equals("-") || action.equals("X") || action.equals("/");
    }

    // Helper method to determine if an action corresponds to a converter unit
    private boolean isConverterUnit(String action) {
        switch (action.toLowerCase()) {
            case "meter":
            case "foot":
            case "centimeter":
            case "inches":
            case "millimeter":
            case "milliliters":
            case "milimeter":
            case "yard":
            case "mile":
            case "miles":
            case "kilogram":
            case "pound":
            case "ounces":
            case "liter":
            case "gallon":
            case "nautical mile":
            case "nautical miles":
            case "furlong":
            case "furlongs":
                return true;
            default:
                return false;
        }
    }

    // Helper method to normalize unit names to a standard format for the engine
    private String normalizeUnit(String action) {
        switch (action.toLowerCase()) {
            case "meter":
                return "meter";
            case "foot":
                return "foot";
            case "centimeter":
                return "centimeter";
            case "inches":
                return "inch";
            case "millimeter":
            case "milliliters":
            case "milimeter":
                return "millimeter";
            case "yard":
                return "yard";
            case "mile":
            case "miles":
                return "mile";
            case "nautical mile":
            case "nautical miles":
                return "nautical mile";
            case "furlong":
            case "furlongs":
                return "furlong";
            case "kilogram":
                return "kilogram";
            case "pound":
                return "pound";
            case "ounces":
                return "ounces";
            case "liter":
                return "liter";
            case "gallon":
                return "gallon";
            default:
                return action.toLowerCase();
        }
    }

    // Methods to handle different types of actions based on the current mode
    private void appendNumber(JTextPane pane, String value) {
        if (pane == null) return;
        String currentText = pane.getText();
        if (justCalculated || operatorJustPressed) {
            pane.setText(value);
            justCalculated = false;
            operatorJustPressed = false;
        } else {
            pane.setText(currentText + value);
        }
    }

    // Method to clear all state variables and reset the display
    private void clearAll(JTextPane pane) {
        if (pane != null) {
            pane.setText("");
        }
        firstNumber = "";
        operator = "";
        secondNumber = "";
        operatorPressed = false;
        justCalculated = false;
        operatorJustPressed = false;
        if (activeMode == ActiveMode.TIP && myView.getTipView() != null) {
            myView.getTipView().clearTipFields();
        }
    }

    // Method to handle backspace action by removing the last character from the display
    private void backspace(JTextPane pane) {
        if (pane == null) return;
        String currentText = pane.getText();
        if (!currentText.isEmpty()) {
            pane.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    // Method to toggle the sign of the current number in the display
    private void toggleSign(JTextPane pane) {
        if (pane == null) return;
        String currentText = pane.getText();
        if (currentText == null || currentText.isEmpty() || currentText.equals("0")) {
            return;
        }
        if (currentText.startsWith("-")) {
            pane.setText(currentText.substring(1));
        } else {
            try {
                double val = Double.parseDouble(currentText.trim());
                pane.setText(String.valueOf(val * -1));
            } catch (NumberFormatException n) {
                pane.setText("Error: invalid number");
            }
        }
    }

    // Method to handle operator button presses in simple calculator mode
    private void handleSimpleOperator(String actionSource, JTextPane pane) {
        if (pane == null) return;
        String currentText = pane.getText();
        // If an operator is already pressed and there's a current number, calculate the intermediate result before setting the new operator
        if (operatorPressed && !currentText.isEmpty()) {
            secondNumber = currentText;
            String result = CalculatorEngine.evaluateExpression(firstNumber + operator + secondNumber);
            firstNumber = result;
            operator = actionSource;
            operatorPressed = true;
            justCalculated = false;
            pane.setText(firstNumber + " " + actionSource);
            operatorJustPressed = true;
        // If just calculated and user presses an operator, use the result as the first number for the new operation
        } else if (justCalculated && !currentText.isEmpty()) {
            firstNumber = currentText;
            operator = actionSource;
            operatorPressed = true;
            justCalculated = false;
            pane.setText(firstNumber + " " + actionSource);
            operatorJustPressed = true;
        // If no operator is currently pressed, set the first number and operator
        } else if (!currentText.isEmpty() && !operatorPressed) {
            firstNumber = currentText;
            operator = actionSource;
            operatorPressed = true;
            justCalculated = false;
            pane.setText(firstNumber + " " + actionSource);
            operatorJustPressed = true;
        }
    }

    // Method to perform the calculation when the equals button is pressed in simple calculator mode
    private void calculateSimple(JTextPane pane) {
        if (pane == null || firstNumber.isEmpty() || operator.isEmpty() || operatorJustPressed) return;
        secondNumber = pane.getText();
        if (secondNumber.isEmpty()) return;
        String expression = firstNumber + operator + secondNumber;
        String result = CalculatorEngine.evaluateExpression(expression);
        history.add(firstNumber + " " + operator + " " + secondNumber + " = " + result);
        pane.setText(result);
        firstNumber = result;
        operator = "";
        secondNumber = "";
        operatorPressed = false;
        justCalculated = true;
    }

    // Method to handle actions in tip calculator mode based on the button pressed
    private void handleTipAction(String actionSource, JTextPane pane) {
        TipView tipView = myView.getTipView();
        if (tipView == null) return;

        // For tip calculator, we only have a meaningful action on the equals button, other buttons are just for input and are handled in appendNumber
        if (actionSource.equals("=")) {
            String billText = tipView.getBillText();
            String percentText = tipView.getTipPercentText();
            String peopleText = tipView.getPeopleText();
            if (billText.isEmpty() || percentText.isEmpty() || peopleText.isEmpty()) {
                tipView.setTipResult("Fill bill, tip %, and people");
                return;
            }
            try {
                double bill = Double.parseDouble(billText);
                int people = Integer.parseInt(peopleText);
                String percentValue = percentText.endsWith("%") ? percentText : percentText + "%";
                double tipAmount = CalculatorEngine.tipExpression(percentValue, bill, people);
                double totalPerPerson = (bill + tipAmount * people) / people;
                tipView.setTipResult(String.format("Tip per person: %.2f\nTotal per person: %.2f", tipAmount, totalPerPerson));
                justCalculated = true;
            } catch (NumberFormatException ex) {
                tipView.setTipResult("Invalid bill or people value");
            }
        }
    }

    // Method to handle actions in converter mode based on the button pressed
    private void handleConverterAction(String actionSource, JTextPane pane) {
        if (myView.getConverterView() == null) return;
        ConverterView converterView = myView.getConverterView();

        // In converter mode, the equals button triggers the conversion, while unit buttons set the selected field and input
        if (actionSource.equals("=")) {
            String valueText = converterView.getValueText();
            String fromUnit = normalizeUnit(converterView.getFromUnitText());
            String toUnit = normalizeUnit(converterView.getToUnitText());
            if (valueText.isEmpty() || fromUnit.isEmpty() || toUnit.isEmpty()) {
                converterView.setResultText("Enter a value and select both units.");
                return;
            }
            // Perform the conversion using the engine and display the result
            try {
                double value = Double.parseDouble(valueText.trim());
                double result = CalculatorEngine.convertExpression(toUnit, value, fromUnit);
                converterView.setResultText(String.format("%.4f %s = %.4f %s", value, converterView.getFromUnitText(), result, converterView.getToUnitText()));
                justCalculated = true;
            } catch (NumberFormatException ex) {
                converterView.setResultText("Invalid value format.");
            } catch (IllegalArgumentException ex) {
                converterView.setResultText("Unable to convert between selected units.");
            }
            return;
        }

        if (isConverterUnit(actionSource)) {
            if (pane == null) return;
            if (converterView.getSelectedField() == ConverterView.ConverterField.VALUE) {
                converterView.setSelectedField(ConverterView.ConverterField.FROM_UNIT);
                pane = converterView.getDisplayPane();
            }
            pane.setText(actionSource);
        }
    }

    // Method to display the history of calculations in the current display pane
    private void showHistory(JTextPane pane) {
        if (pane == null) return;
        historyVisible = true;
        if (history.isEmpty()) {
            pane.setText("No history yet");
        } else {
            pane.setText(String.join("\n", history));
        }
    }

    // Custom ActionListener to handle all button presses and route them to the appropriate handler based on the current mode
    private class CustomListener implements ActionListener {
        @Override
        // Play click sound and determine action source
        public void actionPerformed(ActionEvent e) {
            SoundPlayer.playSound("button-click.mp3");
            String actionSource = e.getActionCommand();
            JTextPane pane = myView.getActiveDisplayPane();

            if (!actionSource.equals("HIST")) {
                clearHistoryIfVisible(pane);
            }

            // Handle number and decimal point button presses for all modes
            if (actionSource.matches("[0-9.]")) {
                if (activeMode == ActiveMode.SIMPLE) {
                    appendNumber(pane, actionSource);
                } else if (activeMode == ActiveMode.TIP) {
                    appendNumber(pane, actionSource);
                } else if (activeMode == ActiveMode.CONVERTER) {
                    if (myView.getConverterView() != null && myView.getConverterView().getSelectedField() != ConverterView.ConverterField.VALUE) {
                        myView.getConverterView().setSelectedField(ConverterView.ConverterField.VALUE);
                        pane = myView.getConverterView().getDisplayPane();
                    }
                    appendNumber(pane, actionSource);
                }
                return;
            }

            // Handle other button presses based on their function and the current mode
            switch (actionSource) {
                case "AC":
                    clearAll(pane);
                    break;
                case "DEL":
                    backspace(pane);
                    break;
                case "THEME":
                    myView.toggleTheme();
                    break;
                case "SIMPLE":
                    switchToMode(ActiveMode.SIMPLE);
                    myView.showSimpleCalculator();
                    break;
                case "TIP":
                    switchToMode(ActiveMode.TIP);
                    myView.showTipCalculator();
                    break;
                case "CONVERT":
                    switchToMode(ActiveMode.CONVERTER);
                    myView.showConverter();
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
                case "HIST":
                    if (activeMode == ActiveMode.SIMPLE) {
                        showHistory(pane);
                    }
                    break;
                case "=":
                    if (activeMode == ActiveMode.SIMPLE) {
                        calculateSimple(pane);
                    } else if (activeMode == ActiveMode.TIP) {
                        handleTipAction("=", pane);
                    } else if (activeMode == ActiveMode.CONVERTER) {
                        handleConverterAction("=", pane);
                    }
                    history.add(actionSource);
                    return;
                case "+/-":
                    if (activeMode == ActiveMode.SIMPLE || activeMode == ActiveMode.CONVERTER) {
                        toggleSign(pane);
                    }
                    break;
                default:
                    if (activeMode == ActiveMode.SIMPLE && isSimpleOperator(actionSource)) {
                        handleSimpleOperator(actionSource, pane);
                    } else if (activeMode == ActiveMode.TIP) {
                        handleTipAction(actionSource, pane);
                    } else if (activeMode == ActiveMode.CONVERTER) {
                        handleConverterAction(actionSource, pane);
                    }
                    break;
            }

            // Add the action to history for all buttons except HIST, which is handled separately
            history.add(actionSource);
        }
    }
}
