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
        String[] parts = expression.split("(?<=[+\\-/X=])|(?=[+\\-/X=])");

        if (parts.length < 4 && !expression.contains("+/-X=")) {
            return "Error: Invalid format, use: 'number' 'operator' 'number'";
        }

        try {
            double a = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double result;

            // Changes number to a negative
            if (operator.equals("+/-X")) {
                result = a * -1;
            } else {
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
                    default:
                        return "Error: Unknown operator " + operator;
                }
            }
            return String.valueOf(result);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return "Error: Invalid number format";
        }
    }
}

// PSUEDOCODE CODE THINGYMABOBBER
        /* for (parts){
            if first
                if number
                    then assign to a
                else
                    print error (because it is a operator, and that is not good no no no)
            else if operator
                assign operator
            else if number
                assign b

            if a and b and operator:
                if there is another part
                    print error

            else if sign ==+
                render (a+b)  to the panel (etc for *-/)
        }

        */