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

public class CalculatorController {
    private CalculatorView myView;
    private CalculatorEngine myEngine;
    private CustomListener myListener;

    public CalculatorController() {
        myEngine = new CalculatorEngine();
        myView = new CalculatorView();
        myListener = new CustomListener();

        myView.setButtonListener(myListener);
    }

    List <String> userInput = new ArrayList<>();

    private class CustomListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            userInput.add(e.getActionCommand());
            System.out.println(userInput);
            myView.displayPane.setText(e.getActionCommand());


            }
            }

        }
