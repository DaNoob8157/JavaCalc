import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConverterView extends JPanel {

    /* === ENUM TO TRACK WHICH FIELD IS SELECTED === */
    public enum ConverterField {
        VALUE,
        FROM_UNIT,
        TO_UNIT
    }

    /* === BUTTON LABELS === */
    String[] btnTextArray = {
        "1", "2", "3", "Meter", "Foot", "Centimeter",
        "4", "5", "6", "Inches", "Yard", "Miles",
        "7", "8", "9", "Kilogram", "Pound", "Ounces",
        ".", "0", "=", "Liter", "Gallon", "Milliliters",
        "TIP", "SIMPLE", "THEME", "EXIT", "Nautical Mile", "Furlong"
    };

    /* === UI COMPONENTS === */
    private JTextPane valuePane;
    private JTextPane fromPane;
    private JTextPane toPane;
    private JTextPane resultPane;
    private JScrollPane valueScroll;
    private JScrollPane fromScroll;
    private JScrollPane toScroll;
    private JScrollPane resultScroll;
    private JTextPane selectedPane;
    private ConverterField selectedField;
    JPanel btnPanel;
    ActionListener btnLstnr;
    private JButton[] buttons;

    public ConverterView() {
        setLayout(new GridBagLayout());
        setBackground(CalcColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();

        /* === ROW 1: Input fields === */
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        add(createFieldPanel(), gbc);

        /* === ROW 2: Button pad === */
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        btnPanel = new JPanel(new GridLayout(0, 6, 6, 6));
        btnPanel.setBackground(CalcColors.BUTTON_PANEL_BACKGROUND);
        buttons = new JButton[btnTextArray.length];
        int index = 0;
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            buttons[index++] = button;
            if (btnLstnr != null) {
                button.addActionListener(btnLstnr);
            }

            // Style buttons
            button.setBackground(CalcColors.BUTTON_BACKGROUND);
            button.setForeground(CalcColors.BUTTON_TEXT);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
            if (text.matches("[=]|TIP|SIMPLE|EXIT|THEME ")) {
                button.setBackground(CalcColors.OPERATOR_BUTTON_BACKGROUND);
            }
            btnPanel.add(button);
        }
        add(btnPanel, gbc);

        setSelectedField(ConverterField.VALUE);
    }

    /* === UI COMPONENT CREATION METHODS === */
    private JPanel createFieldPanel() {
        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(CalcColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;

        /* === ROW 1: Labels === */
        JLabel valueLabel = new JLabel("Value");
        valueLabel.setForeground(CalcColors.DISPLAY_TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldPanel.add(valueLabel, gbc);

        // The "From Unit" and "To Unit" labels are added in the same row but different columns
        JLabel fromLabel = new JLabel("From Unit");
        fromLabel.setForeground(CalcColors.DISPLAY_TEXT);
        gbc.gridx = 1;
        fieldPanel.add(fromLabel, gbc);

        // The "To Unit" label is added in the same row but different column
        JLabel toLabel = new JLabel("To Unit");
        toLabel.setForeground(CalcColors.DISPLAY_TEXT);
        gbc.gridx = 2;
        fieldPanel.add(toLabel, gbc);

        /* === ROW 2: Input panes === */
        valuePane = createInputPane(ConverterField.VALUE);
        fromPane = createInputPane(ConverterField.FROM_UNIT);
        toPane = createInputPane(ConverterField.TO_UNIT);
        resultPane = createResultPane();

        // The input panes are added in the same row but different columns
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.33;
        fieldPanel.add(valueScroll, gbc);

        // The "From Unit" and "To Unit" input panes are added in the same row but different columns
        gbc.gridx = 1;
        fieldPanel.add(fromScroll, gbc);

        // The "To Unit" input pane is added in the same row but different column
        gbc.gridx = 2;
        fieldPanel.add(toScroll, gbc);

        /* === ROW 3: Result pane === */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 0.0;
        fieldPanel.add(createResultPanel(), gbc);

        return fieldPanel;
    }

    /* === END OF UI COMPONENT CREATION METHODS === */
    private JTextPane createInputPane(ConverterField field) {
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
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        if (field == ConverterField.VALUE) {
            valueScroll = scroll;
        } else if (field == ConverterField.FROM_UNIT) {
            fromScroll = scroll;
        } else {
            toScroll = scroll;
        }
        return pane;
    }

    /* === END OF INPUT PANE CREATION METHOD === */
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

    /* === END OF RESULT PANE CREATION METHOD === */
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CalcColors.BACKGROUND);
        JLabel label = new JLabel("Result");
        label.setForeground(CalcColors.DISPLAY_TEXT);
        panel.add(label, BorderLayout.NORTH);
        panel.add(resultScroll, BorderLayout.CENTER);
        return panel;
    }

    public void setButtonListener(ActionListener lstnr){
        btnLstnr = lstnr;
        if (buttons != null) {
            for (JButton button : buttons) {
                button.addActionListener(lstnr);
            }
        }
    }

    public JTextPane getDisplayPane() {
        return selectedPane;
    }

    /* === METHODS TO MANAGE FIELD SELECTION AND TEXT ACCESS === */
    public void setSelectedField(ConverterField field) {
        selectedField = field;
        switch (field) {
            case VALUE -> selectedPane = valuePane;
            case FROM_UNIT -> selectedPane = fromPane;
            case TO_UNIT -> selectedPane = toPane;
        }
        updateFieldSelection();
    }

    /* === END OF FIELD SELECTION METHOD === */
    public ConverterField getSelectedField() {
        return selectedField;
    }

    /* === END OF GETTER FOR SELECTED FIELD === */
    private void updateFieldSelection() {
        valuePane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        fromPane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        toPane.setBorder(BorderFactory.createLineBorder(CalcColors.BUTTON_BORDER));
        if (selectedPane != null) {
            selectedPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
    }

    
    public String getValueText() {
        return valuePane.getText().trim();
    }

    public String getFromUnitText() {
        return fromPane.getText().trim();
    }

    public String getToUnitText() {
        return toPane.getText().trim();
    }

    public void setResultText(String text) {
        resultPane.setText(text);
    }

    /* === END OF METHODS TO MANAGE FIELD SELECTION AND TEXT ACCESS === */
    public void clearConverterFields() {
        valuePane.setText("");
        fromPane.setText("");
        toPane.setText("");
        resultPane.setText("");
        setSelectedField(ConverterField.VALUE);
    }

    /* === THEME MANAGEMENT === */
    public void applyTheme(boolean isDarkMode) {
        Color bgColor = isDarkMode ? CalcColors.BACKGROUND : CalcColors.LIGHT_BACKGROUND;
        Color fgColor = isDarkMode ? CalcColors.DISPLAY_TEXT : CalcColors.LIGHT_DISPLAY_TEXT;
        Color paneBg = isDarkMode ? CalcColors.DISPLAY_BACKGROUND : CalcColors.LIGHT_DISPLAY_BACKGROUND;
        Color btnBgColor = isDarkMode ? CalcColors.BUTTON_BACKGROUND : CalcColors.LIGHT_BUTTON_BACKGROUND;
        Color specialBtnBgColor = isDarkMode ? CalcColors.OPERATOR_BUTTON_BACKGROUND : CalcColors.LIGHT_OPERATOR_BUTTON_BACKGROUND;
        Color borderColor = isDarkMode ? CalcColors.BUTTON_BORDER : CalcColors.LIGHT_BUTTON_BORDER;

        // Update colors for all components
        this.setBackground(bgColor);
        valuePane.setBackground(paneBg);
        valuePane.setForeground(fgColor);
        valuePane.setCaretColor(isDarkMode ? CalcColors.DISPLAY_CARET : CalcColors.LIGHT_DISPLAY_CARET);
        fromPane.setBackground(paneBg);
        fromPane.setForeground(fgColor);
        fromPane.setCaretColor(isDarkMode ? CalcColors.DISPLAY_CARET : CalcColors.LIGHT_DISPLAY_CARET);
        toPane.setBackground(paneBg);
        toPane.setForeground(fgColor);
        toPane.setCaretColor(isDarkMode ? CalcColors.DISPLAY_CARET : CalcColors.LIGHT_DISPLAY_CARET);
        resultPane.setBackground(paneBg);
        resultPane.setForeground(fgColor);

        // Update scroll pane backgrounds
        if (valueScroll != null) valueScroll.getViewport().setBackground(paneBg);
        if (fromScroll != null) fromScroll.getViewport().setBackground(paneBg);
        if (toScroll != null) toScroll.getViewport().setBackground(paneBg);
        if (resultScroll != null) resultScroll.getViewport().setBackground(paneBg);

        // Update button colors
        if (btnPanel != null) {
            btnPanel.setBackground(isDarkMode ? CalcColors.BUTTON_PANEL_BACKGROUND : CalcColors.LIGHT_BUTTON_PANEL_BACKGROUND);
            for (Component c : btnPanel.getComponents()) {
                if (c instanceof JButton button) {
                    String text = button.getText();
                    if (text.matches("[=]|TIP|HIST|EXIT")) {
                        button.setBackground(specialBtnBgColor);
                    } else {
                        button.setBackground(btnBgColor);
                    }
                    button.setForeground(isDarkMode ? CalcColors.BUTTON_TEXT : CalcColors.LIGHT_BUTTON_TEXT);
                    button.setBorder(BorderFactory.createLineBorder(borderColor));
                }
            }
        }

        updateFieldSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConverterView converterView = new ConverterView();
            JFrame test2 = new JFrame();
            test2.add(converterView);
            test2.setVisible(true);
            test2.setSize(700, 500);
        });
    }
}
