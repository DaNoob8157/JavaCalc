import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimpleCalculatorView extends JPanel {
    private ActionListener btnLstnr;
    private JPanel panel, btnPanel;
    static JTextPane displayPane;
    private JScrollPane scrollPane;
    private String[] btnTextArray = {"THEME","DEL","AC","+/-","%","7","8","9","X",
            "4","5","6","-","1","2","3","+",".","0","=", "/"};
    private boolean isDarkMode = true;

    public SimpleCalculatorView (){
        addComponentsToPane();
    }

    private void addComponentsToPane(){
        // Create main panel to contain display & buttons
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
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
        displayPane.setBackground(Color.BLACK);
        displayPane.setForeground(Color.WHITE);
        displayPane.setCaretColor(Color.WHITE);

        scrollPane = new JScrollPane(displayPane);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.BLACK);

        add(scrollPane, gbc);

        // Create an empty label for spacing purposes
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

        // Create button grid and add buttons
        btnPanel = new JPanel(new GridLayout(5,4,0,0));
        btnPanel.setBackground(Color.BLACK);
        for (String text : btnTextArray) {
            JButton button = new JButton(text);
            if (btnLstnr != null) {
                button.addActionListener(btnLstnr);
            }

            button.setBackground(new Color(50, 50, 50));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Special color for operators and special buttons
            if (text.matches("[ACDEL+/\\-%X=]")) {
                button.setBackground(new Color(80, 80, 80));
            }

            btnPanel.add(button);
        }
        add(btnPanel, gbc);

    }
    //hello
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculatorView simpleCalculatorView = new SimpleCalculatorView();
            JFrame test = new JFrame();
            test.add(simpleCalculatorView);
            test.setVisible(true);
            test.setSize(600,400);
        });
    }

    }

