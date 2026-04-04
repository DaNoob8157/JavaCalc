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
}