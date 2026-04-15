import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TipView extends JPanel {

    String[] btnTextArray = {"1", "2", "3", "10%", "4", "5", "6", "15%", "7", "8", "9", "20%", "0", ".", "=", "25%"};
    JTextPane displayPane = new JTextPane();
    JScrollPane scrollPane = new JScrollPane();
    JPanel btnPanel = new JPanel();
    ActionListener btnLstnr;


    public TipView() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        displayPane = new JTextPane();
        displayPane.setFont(new Font("Arial", Font.TRUETYPE_FONT, 40));
        displayPane.setEditable(false);
//       4

        scrollPane = new JScrollPane(displayPane);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(CalcColors.DISPLAY_BACKGROUND);

        //create JLabel for spacing
        JLabel lblNewLabel = new JLabel();
//        lblNewLabel.setBackground(Color.BLACK);
//        lblNewLabel.setOpaque(true);
        add(lblNewLabel, gbc);

        /* === ROW 2: Button Pad === */
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.05;

        btnPanel = new JPanel(new GridLayout(5, 5, 0, 0));
//        btnPanel.setBackground(Color.BLACK);
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            if (btnLstnr != null) {
                button.addActionListener(btnLstnr);
            }

            if (text.matches("[ACDEL+/\\-%X=]")) {
                button.setBackground(CalcColors.OPERATOR_BUTTON_BACKGROUND);
            }

            btnPanel.add(button);

            add(btnPanel, gbc);
//    private String[] tipBtnTextArray = {"1","2", "3", "10%", "4", "5", "6", "15%", "7", "8", "9", "20%", "0", ".", "=", "25%"};
//    private void addComponentsToTipPane(){
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//
//        /* === ROW 0: TEXT FIELD FOR CALCULATOR === */
//        gbc.gridy = 0;
//        gbc.gridx = 0;
//        gbc.gridheight = 3;
//        gbc.weightx = 1;
//        gbc.weighty = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//
//        JTextPane tipDisplayPane = new JTextPane();
//        tipDisplayPane.setFont(new Font("Arial", Font.TRUETYPE_FONT, 40));
//        tipDisplayPane.setEditable(false);
//        tipDisplayPane.setOpaque(true);
//        tipDisplayPane.setBackground(Color.BLACK);
//        tipDisplayPane.setForeground(Color.WHITE);
//        tipDisplayPane.setCaretColor(Color.WHITE);
//
//        JScrollPane tipScrollPane = new JScrollPane(tipDisplayPane);
//        tipScrollPane.setBorder(null);
//        tipScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        tipScrollPane.getViewport().setBackground(Color.BLACK);
//
//        panel.add(tipScrollPane, gbc);
//
//        // Create an empty label for spacing purposes
//        JLabel lblNewLabel = new JLabel();
//        lblNewLabel.setBackground(Color.BLACK);
//        lblNewLabel.setOpaque(true);
//        panel.add(lblNewLabel, gbc);
//
//        /* === ROW 2: Button Pad === */
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridy = 3;
//        gbc.gridx = 0;
//        gbc.gridheight = 3;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.05;
//
//        JPanel tipBtnPanel = new JPanel(new GridLayout(4,4,0,0));
//        panel.add(tipBtnPanel, gbc);
            
            
//        add(panel);
//        add(scrollPane, gbc);
//        add(one);
//        add(two);
//        add(three);
//        add(ten);
//        add(four);
//        add(five);
//        add(six);
//        add(fifteen);
//        add(seven);
//        add(eight);
//        add(nine);
//        add(twenty);
//        add(zero);
//        add(dot);
//        add(equal);
//        add(twentyFive);

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TipView tipView = new TipView();
            JFrame test2 = new JFrame();
            test2.add(tipView);
            test2.setVisible(true);
            test2.setSize(600, 400);
        });
    }
}
