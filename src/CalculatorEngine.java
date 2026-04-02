import java.util.Arrays;

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

        // Split by operators, keeping the operators in the array
        String[] parts = expression.split("(?<=[+\\-/X%])|(?=[+\\-/X%])");

        if (parts.length < 3) {
            return "Error: Invalid format, use: 'number' 'operator' 'number'";
        }

        try {
            double a = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double result;

            // operation functions
            double b = Double.parseDouble(parts[2]);
            switch (operator) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "X":
                case "*":
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
                    return "Error: Unknown operator " + operator;
            }
            return String.valueOf(result);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return "Error: Invalid number format";
        }
    }
}