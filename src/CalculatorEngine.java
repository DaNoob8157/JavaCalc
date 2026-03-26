import java.util.Scanner;

/**
 * Class to implement functionality of calculator.
 *
 */

public class CalculatorEngine {

    public CalculatorEngine() {

    }

    /*Methods to implement calculator functionality*/

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        return a / b;
    }

    public double modulus(double a, double b) {
        return a % b;
    }

    public double switchSign(double a, double b) {
        return a * -1;
    }

    public String updateExpression() {
        return "";
    }

    public void evaluateExpression() {

        String calculation = "1+2"; //pass from panel
        String[] calulation = calculation.split("[+\\-*/]"); // tells regex engine to "treat those as literal characters"
        // StringBuilder result = new StringBuilder();

        for (eghieruhwfe)


   }
}