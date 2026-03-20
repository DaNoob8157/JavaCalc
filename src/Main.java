import java.util.Scanner;
//
//  Main.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

public class Main{
    public static void main (String[] args) {
        // CalculatorController controller = new CalculatorController();

        // initializes user interface
        Scanner scanner = new Scanner(System.in);
        //creates a loop
        boolean whileCalc = true;

        //everything in while loop happens when whileCalc is true
        while (whileCalc) {

            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();

            System.out.println("Please enter a operation");
            String operator = scanner.next();

            double result = 0;

            // operator logic
            switch (operator){
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
            }

        }

    }
}
