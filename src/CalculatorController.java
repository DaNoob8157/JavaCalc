//
//  CalculatorController.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/19/26
//

public class CalculatorController {
    public CalculatorController() {
        new CalculatorEngine();
        CalculatorView myView = new CalculatorView();

        myView.setButtonListener(e -> System.out.println(e.getActionCommand()));
    }
}

