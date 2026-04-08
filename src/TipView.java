import javax.swing.*;
import java.awt.GridLayout;

public class TipView extends JPanel {
    JButton one = new JButton("1");
    JButton two = new JButton("2");
    JButton three = new JButton("3");
    JButton ten = new JButton("10%");
    JButton four = new JButton("4");
    JButton five = new JButton("5");
    JButton six = new JButton("6");
    JButton fifteen = new JButton("15%");
    JButton seven = new JButton("7");
    JButton eight = new JButton("8");
    JButton nine = new JButton("9");
    JButton twenty = new JButton("20%");
    JButton zero = new JButton("0");
    JButton dot = new JButton(".");
    JButton equal = new JButton("=");
    JButton twentyFive = new JButton("25%");

    public TipView() {
        setLayout(new GridLayout(4,4));
        add(one);
        add(two);
        add(three);
        add(ten);
        add(four);
        add(five);
        add(six);
        add(fifteen);
        add(seven);
        add(eight);
        add(nine);
        add(twenty);
        add(zero);
        add(dot);
        add(equal);
        add(twentyFive);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TipView tipView = new TipView();
            JFrame test2 = new JFrame();
            test2.add(tipView);
            test2.setVisible(true);
            test2.setSize(600,400);
        });
    }
}