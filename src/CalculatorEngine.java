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

        String expression = "1+2";
        String[] parts = expression.split("[+\\-/*]");




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
   }
}