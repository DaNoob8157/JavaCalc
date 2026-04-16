import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimpleCalculatorView extends JPanel {
    private ActionListener btnLstnr;
    private JPanel panel, btnPanel;
    static JTextPane displayPane;
    private JScrollPane scrollPane;
    private String[] btnTextArray = {"AC","DEL","%","+/-","THEME","1","2","3","/","CONVERT","4",
            "5","6","X","HIST","7","8","9","-","TIP",".","0","=","+","EXIT"};
    private boolean isDarkMode = true;
    private JButton[] buttons;

    public SimpleCalculatorView (){
        addComponentsToPane();
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

        // Update background and foreground colors for the main panel and display
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

    // Method to initialize and add all components to the main panel
    private void addComponentsToPane(){
        // Create main panel to contain display & buttons
        setLayout(new GridBagLayout());
        setBackground(CalcColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();

        /* === ROW 0: TEXT FIELD FOR CALCULATOR === */
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        //Create a text pane to act as a display for the calculator
        displayPane = new JTextPane();
        displayPane.setFont(new Font("Arial", Font.TRUETYPE_FONT, 40));
        displayPane.setEditable(false);
        displayPane.setOpaque(true);
        displayPane.setBackground(CalcColors.DISPLAY_BACKGROUND);
        displayPane.setForeground(CalcColors.DISPLAY_TEXT);
        displayPane.setCaretColor(CalcColors.DISPLAY_CARET);

        scrollPane = new JScrollPane(displayPane);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(CalcColors.DISPLAY_BACKGROUND);

        add(scrollPane, gbc);

        // Create an empty label for spacing purposes
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBackground(CalcColors.BACKGROUND);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, gbc);

        /* === ROW 2: Button Pad === */
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.05;

        // Create button grid and add buttons
        // Need 5 rows x 5 columns for 25 buttons (AC, DEL, %, +/-, THEME, 1-9, /, CONVERT, X, HIST, -, TIP, ., 0, =, +, EXIT)
        btnPanel = new JPanel(new GridLayout(5,5,0,0));
        btnPanel.setBackground(CalcColors.BUTTON_PANEL_BACKGROUND);
        buttons = new JButton[btnTextArray.length];
        int index = 0;
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            buttons[index++] = button;
            if (btnLstnr != null) {
                button.addActionListener(btnLstnr);
            }

            button.setBackground(CalcColors.BUTTON_BACKGROUND);
            button.setForeground(CalcColors.BUTTON_TEXT);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));

            // Special color for operators and special buttons
            if (text.matches("[ACDEL+/\\-%X=]")) {
                button.setBackground(CalcColors.OPERATOR_BUTTON_BACKGROUND);
            }

            btnPanel.add(button);
        }
        add(btnPanel, gbc);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculatorView simpleCalculatorView = new SimpleCalculatorView();
            JFrame test = new JFrame();
            test.add(simpleCalculatorView);
            test.setVisible(true);
            test.setSize(600,400);
            test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    }

