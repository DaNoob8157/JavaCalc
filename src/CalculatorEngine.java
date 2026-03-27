import java.util.Scanner;

/**
 * Class to implement functionality of calculator.
 *
 */

public class CalculatorEngine {

    public CalculatorEngine() {

    }

    public void evaluateExpression() {

        String expression = "1+2";
        String[] parts = expression.split("[+\\-/*]");

        if (parts.length != 3) {
            System.out.println("Error: Invalid expression format. Please use 'number operator number' format.");
            return;
        }

        // Assign parts based on your logic
        double a = 0;
        double b = 0;
        String operator = "";
        boolean first = true;

        for (String part : parts) {
            if (first) {
                // Check if the first part is a number
                try {
                    a = Double.parseDouble(part);
                    first = false;
                } catch (NumberFormatException e) {
                    System.out.println("Error: The first part must be a number (because an operator is not good).");
                    return;
                }

            } else if (isOperator(part)) {
                operator = part;
            } else {
                // Assume the remaining part is a number
                try {
                    b = Double.parseDouble(part);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid number format for the second operand.");
                    return;
                }
            }
        }

        // Perform the calculation and "render" to the console
        if (!operator.isEmpty()) {
            double result = 0;
            switch (operator) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    if (b == 0) {
                        System.out.println("Error: Division by zero is not allowed.");
                        return;
                    }
                    result = a / b;
                    break;
                default:
                    System.out.println("Error: Unknown operator.");
                    return;
            }
            // Render the result to the panel (console in this case)
            System.out.println("Result: " + result);
        }
    }

    /**
     * Helper method to check if a string is a recognized operator.
     * @param s The string to check.
     * @return true if the string is an operator, false otherwise.
     */
    public static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
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