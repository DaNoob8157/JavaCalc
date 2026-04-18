import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TipView extends JPanel {

    public enum TipField {
        BILL,
        PERCENT,
        PEOPLE
    }

    String[] btnTextArray = {"1", "2", "3", "4", "SIMPLE", "5", "6", "7", "8", "CONVERT", "9", "0", ".", "=", "AC"};
    // Display panes and scroll panes for input fields and result
    private JTextPane billPane;
    private JTextPane tipPane;
    private JTextPane peoplePane;
    private JTextPane resultPane;
    private JTextPane selectedPane;
    private JScrollPane billScroll;
    private JScrollPane tipScroll;
    private JScrollPane peopleScroll;
    private JScrollPane resultScroll;
    JPanel btnPanel;
    ActionListener btnLstnr;
    private JButton[] buttons;
    public TipView() {
        setLayout(new GridBagLayout());
        setBackground(CalcColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();

        /* === ROW 1: Input Fields === */
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        add(createFieldPanel(), gbc);

        /* === ROW 2: Button Pad === */
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.85;
        btnPanel = new JPanel(new GridLayout(3, 5, 4, 4));
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
            if (text.matches("[ACDEL+/\\-%X=]|THEME")) {
                button.setBackground(CalcColors.OPERATOR_BUTTON_BACKGROUND);
            }
            btnPanel.add(button);
        }
        add(btnPanel, gbc);

        setSelectedField(TipField.BILL);
    }

    // Method to create the panel containing input fields and their labels
    private JPanel createFieldPanel() {
        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(CalcColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.0;

        // Add labels for each input field
        JLabel billLabel = new JLabel("Bill Amount");
        billLabel.setForeground(CalcColors.DISPLAY_TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldPanel.add(billLabel, gbc);

        // Add empty label for spacing between bill and tip percent labels
        JLabel tipLabel = new JLabel("Tip %");
        tipLabel.setForeground(CalcColors.DISPLAY_TEXT);
        gbc.gridx = 1;
        fieldPanel.add(tipLabel, gbc);

        // Add empty label for spacing between tip percent and people labels
        JLabel peopleLabel = new JLabel("Number of People");
        peopleLabel.setForeground(CalcColors.DISPLAY_TEXT);
        gbc.gridx = 2;
        fieldPanel.add(peopleLabel, gbc);

        // Create text panes for each input field and wrap them in scroll panes
        billPane = createInputPane(TipField.BILL);
        tipPane = createInputPane(TipField.PERCENT);
        peoplePane = createInputPane(TipField.PEOPLE);
        resultPane = createResultPane();

        // Add scroll panes to the field panel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.33;
        fieldPanel.add(billScroll, gbc);

        // Add empty label for spacing between bill and tip percent fields
        gbc.gridx = 1;
        fieldPanel.add(tipScroll, gbc);

        // Add empty label for spacing between tip percent and people fields
        gbc.gridx = 2;
        fieldPanel.add(peopleScroll, gbc);

        // Add result pane below the input fields
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        fieldPanel.add(createResultPanel(), gbc);

        // Set the preferred size of the field panel to ensure it takes up appropriate space
        return fieldPanel;
    }

    // Helper method to create a JTextPane for input fields with appropriate styling and mouse listener
    private JTextPane createInputPane(TipField field) {
        JTextPane pane = new JTextPane();
        pane.setFont(new Font("Arial", Font.PLAIN, 20));
        pane.setEditable(false);
        pane.setOpaque(true);
        pane.setBackground(CalcColors.DISPLAY_BACKGROUND);
        pane.setForeground(CalcColors.DISPLAY_TEXT);
        pane.setCaretColor(CalcColors.DISPLAY_CARET);
        pane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSelectedField(field);
            }
        });
        // Wrap the text pane in a scroll pane to handle overflow and set appropriate policies
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        if (field == TipField.BILL) {
            billScroll = scroll;
        } else if (field == TipField.PERCENT) {
            tipScroll = scroll;
        } else {
            peopleScroll = scroll;
        }
        return pane;
    }

    // Helper method to create the result pane with appropriate styling and scroll behavior
    private JTextPane createResultPane() {
        JTextPane pane = new JTextPane();
        pane.setFont(new Font("Arial", Font.PLAIN, 18));
        pane.setEditable(false);
        pane.setOpaque(true);
        pane.setBackground(CalcColors.LIGHT_BACKGROUND);
        pane.setForeground(CalcColors.DISPLAY_TEXT);
        pane.setCaretColor(CalcColors.DISPLAY_CARET);
        pane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        resultScroll = new JScrollPane(pane);
        resultScroll.setBorder(BorderFactory.createEmptyBorder());
        resultScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultScroll.setPreferredSize(new Dimension(0, 80));
        return pane;
    }

    // Helper method to create the panel that contains the result pane, allowing for better layout control
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CalcColors.BACKGROUND);
        JLabel label = new JLabel("Tip Result");
        label.setForeground(CalcColors.DISPLAY_TEXT);
        panel.add(label, BorderLayout.NORTH);
        panel.add(resultScroll, BorderLayout.CENTER);
        return panel;
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
        return selectedPane;
    }

    // Method to set the currently selected input field based on user interaction
    public void setSelectedField(TipField field) {
        switch (field) {
            case BILL -> selectedPane = billPane;
            case PERCENT -> selectedPane = tipPane;
            case PEOPLE -> selectedPane = peoplePane;
        }
        updateFieldSelection();
    }

    // Method to visually indicate which input field is currently selected by changing the border color
    private void updateFieldSelection() {
        billPane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        tipPane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        peoplePane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        if (selectedPane != null) {
            selectedPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
    }

    // Getter methods to retrieve the text from each input field, trimming any extra whitespace
    public String getBillText() {
        return billPane.getText().trim();
    }

    public String getTipPercentText() {
        return tipPane.getText().trim();
    }

    public String getPeopleText() {
        return peoplePane.getText().trim();
    }

    public void setTipResult(String text) {
        resultPane.setText(text);
    }

    // Method to clear all input fields and reset the selected field to the bill amount
    public void clearTipFields() {
        billPane.setText("");
        tipPane.setText("");
        peoplePane.setText("");
        resultPane.setText("");
        setSelectedField(TipField.BILL);
    }

    // Method to apply the selected theme to all components in the view
    public void applyTheme(boolean isDarkMode) {
        Color bgColor = isDarkMode ? CalcColors.BACKGROUND : CalcColors.LIGHT_BACKGROUND;
        Color fgColor = isDarkMode ? CalcColors.DISPLAY_TEXT : CalcColors.LIGHT_DISPLAY_TEXT;
        Color paneBg = isDarkMode ? CalcColors.DISPLAY_BACKGROUND : CalcColors.LIGHT_DISPLAY_BACKGROUND;
        Color btnBgColor = isDarkMode ? CalcColors.BUTTON_BACKGROUND : CalcColors.LIGHT_BUTTON_BACKGROUND;
        Color specialBtnBgColor = isDarkMode ? CalcColors.OPERATOR_BUTTON_BACKGROUND : CalcColors.LIGHT_OPERATOR_BUTTON_BACKGROUND;
        Color borderColor = isDarkMode ? CalcColors.BUTTON_BORDER : CalcColors.LIGHT_BUTTON_BORDER;

        // Update background and foreground colors for the main panel and all text panes
        this.setBackground(bgColor);
        billPane.setBackground(paneBg);
        billPane.setForeground(fgColor);
        billPane.setCaretColor(isDarkMode ? CalcColors.DISPLAY_CARET : CalcColors.LIGHT_DISPLAY_CARET);
        tipPane.setBackground(paneBg);
        tipPane.setForeground(fgColor);
        tipPane.setCaretColor(isDarkMode ? CalcColors.DISPLAY_CARET : CalcColors.LIGHT_DISPLAY_CARET);
        peoplePane.setBackground(paneBg);
        peoplePane.setForeground(fgColor);
        peoplePane.setCaretColor(isDarkMode ? CalcColors.DISPLAY_CARET : CalcColors.LIGHT_DISPLAY_CARET);
        resultPane.setBackground(paneBg);
        resultPane.setForeground(fgColor);

        // Update the background color of the scroll panes to match the new theme
        if (billScroll != null) billScroll.getViewport().setBackground(paneBg);
        if (tipScroll != null) tipScroll.getViewport().setBackground(paneBg);
        if (peopleScroll != null) peopleScroll.getViewport().setBackground(paneBg);
        if (resultScroll != null) resultScroll.getViewport().setBackground(paneBg);

        // Update button colors and borders to match the new theme
        if (btnPanel != null) {
            btnPanel.setBackground(isDarkMode ? CalcColors.BUTTON_PANEL_BACKGROUND : CalcColors.LIGHT_BUTTON_PANEL_BACKGROUND);
            for (Component c : btnPanel.getComponents()) {
                if (c instanceof JButton button) {
                    String text = button.getText();
                    if (text.matches("[ACDEL+/\\-%X=]|THEME")) {
                        button.setBackground(specialBtnBgColor);
                    } else {
                        button.setBackground(btnBgColor);
                    }
                    button.setForeground(isDarkMode ? CalcColors.BUTTON_TEXT : CalcColors.LIGHT_BUTTON_TEXT);
                    button.setBorder(BorderFactory.createLineBorder(borderColor));
                }
            }
        }

        // Update the borders of the input fields to reflect the new theme while maintaining the selected field highlight
        updateFieldSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TipView tipView = new TipView();
            JFrame test2 = new JFrame();
            test2.add(tipView);
            test2.setVisible(true);
            test2.setSize(700, 500);
        });
    }
}
