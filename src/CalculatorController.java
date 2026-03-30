//
//  CalculatorController.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the calculator application.
 * Manages interaction between the view and engine components.
 */
public class CalculatorController {
    CalculatorView myView;
    CalculatorEngine myEngine;
    CustomListener myListener;


    /**
     * Constructor initializes the calculator components and sets up listeners.
     */
    public CalculatorController() {

        myEngine = new CalculatorEngine();
        myView = new CalculatorView();
        myListener = new CustomListener();

        myView.setButtonListener(myListener);
    }

    List<String> userInput = new ArrayList<>();

    private class CustomListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionSource = e.getActionCommand();

            // Handle numeric input (0-9) and decimal point
            // Append the digit or decimal point to the current display text
            if (actionSource.matches("[0-9.]")) {
                String currentText = CalculatorView.displayPane.getText();
                CalculatorView.displayPane.setText(currentText + actionSource);
            } else if (actionSource.equals("AC")) {
                // All Clear - reset the display to empty string
                CalculatorView.displayPane.setText("");
            } else if (actionSource.equals("DEL")) {
                // Delete - remove the last character from the display
                String currentText = CalculatorView.displayPane.getText();
                if (!currentText.isEmpty()) {
                    CalculatorView.displayPane.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else {
                // Placeholder for other operations
                System.out.println("Operation: " + actionSource);
            }

            // Track user input for calculation processing
            userInput.add(actionSource);
            System.out.println(userInput);
            
            switch (actionSource) {
                case "Exit":
                    // Terminate the application
                    System.exit(0);
                    break;

                case "+":
                    // TODO: Implement addition operation

                case "=":
                    // TODO: Implement equals operation to evaluate expression


            }


        }
    }

}
