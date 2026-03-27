//
//  CalculatorController.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
    private CalculatorView myView;
    private CalculatorEngine myEngine;
    private CustomListener myListener;

    public CalculatorController() {
        myEngine = new CalculatorEngine();
        myListener = new CustomListener();
        myView = new CalculatorView(myListener, 500, 520);
    }

    private class CustomListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            myView.displayPane.setText(e.getActionCommand());
            }
        // CalculatorView.playSound("/button-click.mp3");
        }
    }
