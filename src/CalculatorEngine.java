/**
 * Class to implement functionality of calculator.
 *
 */

public class CalculatorEngine {

    public CalculatorEngine() {
        initGame();
    }

    public void initGame() {

    }

    public static String evaluateExpression(String s) {
        // Remove spaces for easier parsing
        String expression = s.replaceAll("\\s+", "");

        int opIndex = -1; // only starts scanning for the operator at index 1, so a - on the first number cannot be mistaken for an operator
        for (int i = 1; i < expression.length(); i++) {
            char op = expression.charAt(i);
            if (op == '+' || op == '-' || op == '/' || op == 'X' || op == '%') {
                opIndex = i;
                break;
            }
        }
        if (opIndex == -1) {
            return "Error: Invalid format, use: 'number' 'operator' 'number'";
        }

        String A = expression.substring(0, opIndex);
        String operator = expression.substring(opIndex, opIndex + 1);
        String B = expression.substring(opIndex + 1);

        try {
            double a = Double.parseDouble(A);
            double b = Double.parseDouble(B);
            double result;
            switch (operator) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "X":
                    result = a * b;
                    break;
                case "/":
                    if (b == 0) return "Error: Division by zero";
                    result = a / b;
                    break;
                case "%":
                    if (b == 0) return "Error: Division by zero";
                    result = a % b;
                    break;
                default:
                    return "Error: Unknown operator" + operator;
            }
            return String.valueOf(result);
        } catch (NumberFormatException e) {
            return "Error: Invalid number format";
        }
    }

    public static double convertExpression(String targetUnit, double value, String inputUnit) {

        double valueInMeters;

        switch (inputUnit.toLowerCase()) {
            case "centimeter":
                valueInMeters = value / 100.0;
                break;
            case "kilometer":
                valueInMeters = value * 1000.0;
                break;
            case "meter":
                valueInMeters = value;
                break;
            case "foot":
                valueInMeters = value * 0.3048;
                break;
            case "yard":
                valueInMeters = value * 0.9144;
                break;
            case "inch":
                valueInMeters = value * 0.0254;
                break;
            case "millimeter":
                valueInMeters = value / 1000.0;
                break;
            case "mile":
                valueInMeters = value * 1609.34;
                break;
            case "nautical mile":
            case "nautical miles":
                valueInMeters = value * 1852.0;
                break;
            case "furlong":
            case "furlongs":
                valueInMeters = value * 201.168;
                break;
            default:
                throw new IllegalArgumentException("Unknown input unit: " + inputUnit);
        }

        switch (targetUnit.toLowerCase()) {
            case "centimeter":
                return valueInMeters * 100.0;
            case "kilometer":
                return valueInMeters / 1000.0;
            case "meter":
                return valueInMeters;
            case "foot":
                return valueInMeters / 0.3048;
            case "yard":
                return valueInMeters / 0.9144;
            case "inch":
                return valueInMeters / 0.0254;
            case "millimeter":
                return valueInMeters * 1000.0;
            case "mile":
                return valueInMeters / 1609.34;
            case "nautical mile":
            case "nautical miles":
                return valueInMeters / 1852.0;
            case "furlong":
            case "furlongs":
                return valueInMeters / 201.168;
            default:
                throw new IllegalArgumentException("Unknown target unit: " + targetUnit);
        }
    }

    public static double tipExpression(String tipPercentage, double bill, int people) {
        double tip;

        switch (tipPercentage) {
            case "10%":
                tip = bill * 0.1;
                break;
            case "15%":
                tip = bill * 0.15;
                break;
            case "20%":
                tip = bill * 0.2;
                break;
            case "25%":
                tip = bill * 0.25;
                break;
            default:
                // Handles any custom percentage ex "18%" or "18"
                try {
                    double custom = Double.parseDouble(tipPercentage.replace("%", ""));
                    tip = bill * (custom / 100.0);
                } catch (NumberFormatException e) {
                    tip = 0;
                }
                break;
        }
        return tip / people;
    }
}
