//
//  StyleManager.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/20/26
//

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * StyleManager applies CSS styles to Swing components
 * Works with CSSParser to read CSS files and apply styling
 */
public class StyleManager {
    private final CSSParser parser;
    
    /**
     * Constructor - Initialize the StyleManager with a CSS file path
     * @param cssFilePath Path to the CSS file (e.g., "src/Styling/style-dark.css")
     */
    public StyleManager(String cssFilePath) {
        parser = new CSSParser();
        parser.loadCSS(cssFilePath);
    }
    
    /**
     * Style a JButton using CSS properties
     * @param button The button to style
     * @param selector CSS selector (e.g., ".button")
     */
    public void styleButton(JButton button, String selector) {
        Color background = parser.getBackgroundColor(selector);
        Color textColor = parser.getTextColor(selector);
        Color pressedBackground = parser.getBackgroundColor(selector + ":pressed");
        int fontSize = parser.getFontSize(selector);
        String fontFamily = parser.getFontFamily(selector);

        // Keep default calculator buttons readable if a CSS file/path fails to load.
        if (".button".equals(selector)) {
            if (background == null) {
                background = new Color(92, 97, 104);
            }
            if (textColor == null) {
                textColor = Color.WHITE;
            }
        }

        if (pressedBackground == null && background != null) {
            pressedBackground = background.darker();
        }

        // Use a basic UI delegate so custom colors are honored across platform LAFs.
        button.setUI(new BasicButtonUI());

        if (background != null) {
            button.setBackground(background);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
        }

        if (textColor != null) {
            button.setForeground(textColor);
        }

        button.setFocusPainted(false);
        if (background != null) {
            button.setBorder(new LineBorder(background.brighter().brighter(), 1));
        }

        final Color normalBackground = background;
        final Color activePressedBackground = pressedBackground;
        if (normalBackground != null && activePressedBackground != null) {
            button.getModel().addChangeListener(e -> {
                ButtonModel model = button.getModel();
                button.setBackground(model.isPressed() ? activePressedBackground : normalBackground);
            });
        }

        if (fontSize > 0 || fontFamily != null) {
            Font current = button.getFont();
            String resolvedFamily = fontFamily != null ? fontFamily : current.getFamily();
            int resolvedSize = fontSize > 0 ? fontSize : current.getSize();
            button.setFont(new Font(resolvedFamily, current.getStyle(), resolvedSize));
        }
    }
    
    /**
     * Style a JPanel using CSS properties
     * @param panel The panel to style
     * @param selector CSS selector (e.g., ".root")
     */
    public void stylePanel(JPanel panel, String selector) {
        Color background = parser.getBackgroundColor(selector);
        if (background != null) {
            panel.setBackground(background);
            panel.setOpaque(true);
        }
    }
    
    /**
     * Style a JTextPane using CSS properties
     * @param textPane The text pane to style
     * @param selector CSS selector (e.g., ".root")
     */
    public void styleTextPane(JTextPane textPane, String selector) {
        Color background = parser.getBackgroundColor(selector);
        Color textColor = parser.getTextColor(selector);
        int fontSize = parser.getFontSize(selector);
        String fontFamily = parser.getFontFamily(selector);

        if (background != null) {
            textPane.setBackground(background);
            textPane.setOpaque(true);
        }

        if (textColor != null) {
            textPane.setForeground(textColor);
        }

        if (fontSize > 0 || fontFamily != null) {
            Font current = textPane.getFont();
            String resolvedFamily = fontFamily != null ? fontFamily : current.getFamily();
            int resolvedSize = fontSize > 0 ? fontSize : current.getSize();
            textPane.setFont(new Font(resolvedFamily, current.getStyle(), resolvedSize));
        }
    }
    
    /**
     * Get the parser instance for custom operations
     * @return The CSSParser instance
     */
    public CSSParser getParser() {
        return parser;
    }
}
