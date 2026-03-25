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

        Scanner scan = new Scanner(System.in);
        String calculation = scan.nextLine();
        String [] parts = calculation.split(" ");
        String s="12";
        int i = Integer.parseInt(s);

        switch (parts[2]) {

            // String ans = String.valueOf(Integer.parseInt(parts[0]) / Integer.parseInt(parts[1]));

        }

   }
}