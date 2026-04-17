import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TipView extends JPanel {

    String[] btnTextArray = {"1", "2", "3", "10%","SIMPLE", "4", "5", "6", "15%","CONVERT", "7", "8", "9", "20%","HIST", "0", ".", "=", "25%","Num of Peeps"};
    JTextPane displayPane = new JTextPane();
    JScrollPane scrollPane = new JScrollPane();
    JPanel btnPanel = new JPanel();
    ActionListener btnLstnr;
    private JButton[] buttons;
    private boolean isDarkMode = true;

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

//

        scrollPane = new JScrollPane(displayPane);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(CalcColors.DISPLAY_BACKGROUND);
        add(scrollPane, gbc);

        //create JLabel for spacing
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBackground(Color.BLACK);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, gbc);

        /* === ROW 2: Button Pad === */
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.05;

        btnPanel = new JPanel(new GridLayout(4, 4, 0, 0));
        buttons = new JButton[btnTextArray.length];
        int index = 0;
//        btnPanel.setBackground(Color.BLACK);
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            buttons[index++] = button;
            if (btnLstnr != null) {
                button.addActionListener(btnLstnr);
            }

            if (text.matches("[ACDEL+/\\-%X=]")) {
                button.setBackground(CalcColors.OPERATOR_BUTTON_BACKGROUND);
            }

            btnPanel.add(button);
        }
            add(btnPanel, gbc);
    }
    
    // Method to set the button listener for all buttons in the view
    public void setButtonListener(ActionListener lstnr){
        btnLstnr = lstnr;
        if (buttons != null) {
            for (JButton button : buttons) {
                button.addActionListener(lstnr);
            }
        }
    }
    
    // Getter for the display pane to allow the controller to update the display
    public JTextPane getDisplayPane() {
        return displayPane;
    }
    
    // Method to apply the selected theme to all components in the view
    public void applyTheme(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        Color bgColor = isDarkMode ? Color.BLACK : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;
        Color btnBgColor = isDarkMode ? new Color(50, 50, 50) : new Color(220, 220, 220);
        Color specialBtnBgColor = isDarkMode ? new Color(80, 80, 80) : new Color(180, 180, 180);
        Color borderColor = isDarkMode ? Color.BLACK : Color.LIGHT_GRAY;

        this.setBackground(bgColor);
        if (displayPane != null) {
            displayPane.setBackground(bgColor);
            displayPane.setForeground(fgColor);
            displayPane.setCaretColor(fgColor);
        }
        if (scrollPane != null) {
            scrollPane.getViewport().setBackground(bgColor);
        }
        
        // Update button colors and borders
        if (btnPanel != null) {
            btnPanel.setBackground(bgColor);
            for (Component c : btnPanel.getComponents()) {
                if (c instanceof JButton) {
                    JButton button = (JButton) c;
                    String text = button.getText();
                    if (text.matches("[ACDEL+/\\-%X=]|THEME")) {
                        button.setBackground(specialBtnBgColor);
                    } else {
                        button.setBackground(btnBgColor);
                    }
                    button.setForeground(fgColor);
                    button.setBorder(BorderFactory.createLineBorder(borderColor));
                }
            }
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
