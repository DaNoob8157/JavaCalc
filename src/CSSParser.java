//
//  CSSParser.java
//  JavaCalc-Class
//
//  Created by DaNoob8157 on 03/20/26
//

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSS Parser - Reads CSS files and extracts styling properties
 * Converts CSS values to Java/Swing-compatible types
 */
public class CSSParser {
    private final Map<String, Map<String, String>> styles;
    
    /**
     * Constructor - initializes the in-memory selector/property map.
     */
    public CSSParser() {
        styles = new HashMap<>();
    }
    
    /**
     * Loads a CSS file from disk and parses its contents.
     *
     * @param filePath path to the CSS file to load
     */
    public void loadCSS(String filePath) {
        StringBuilder cssBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cssBuilder.append(line).append('\n');
            }
            parseCSS(cssBuilder.toString());
        } catch (IOException e) {
            System.err.println("Failed to load CSS file: " + filePath);
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Parses raw CSS text into selector/property maps.
     *
     * @param cssContent full CSS content as a string
     */
    private void parseCSS(String cssContent) {
        styles.clear();

        // enables DOTALL so multiline comments are removed correctly.
        String cssWithoutComments = cssContent.replaceAll("(?s)/\\*.*?\\*/", "");
        Pattern rulePattern = Pattern.compile("([^{]+)\\{([^}]+)\\}", Pattern.DOTALL);
        Matcher matcher = rulePattern.matcher(cssWithoutComments);

        while (matcher.find()) {
            String selector = matcher.group(1).trim();
            String properties = matcher.group(2).trim();
            Map<String, String> propertyMap = parseProperties(properties);

            if (!selector.isEmpty() && !propertyMap.isEmpty()) {
                styles.put(selector, propertyMap);
            }
        }
    }
    
    /**
     * Parses a declaration block into key/value property entries.
     *
     * @param propertiesStr declaration block, for example "-fx-color: #333333;"
     * @return map of property names to values
     */
    private Map<String, String> parseProperties(String propertiesStr) {
        Map<String, String> propertyMap = new HashMap<>();
        String[] propertyPairs = propertiesStr.split(";");

        for (String pair : propertyPairs) {
            String trimmedPair = pair.trim();
            if (trimmedPair.isEmpty()) {
                continue;
            }

            String[] keyValue = trimmedPair.split(":", 2);
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            if (!key.isEmpty() && !value.isEmpty()) {
                propertyMap.put(key, value);
            }
        }

        return propertyMap;
    }
    
    /**
     * Looks up a property value for a selector.
     *
     * @param selector CSS selector (for example ".button")
     * @param property property name (for example "-fx-background-color")
     * @return property value, or null when not found
     */
    public String getProperty(String selector, String property) {
        Map<String, String> selectorProperties = styles.get(selector);
        if (selectorProperties == null) {
            return null;
        }
        return selectorProperties.get(property);
    }
    
    /**
     * Converts a hex color string into a Java Color.
     *
     * @param hexColor hex color string, with or without '#'
     * @return Color instance, or null when invalid
     */
    public static Color hexToColor(String hexColor) {
        if (hexColor == null) {
            return null;
        }

        String cleanedHex = hexColor.trim().replace("#", "");
        try {
            if (cleanedHex.length() == 6) {
                return new Color(Integer.parseInt(cleanedHex, 16));
            }
            if (cleanedHex.length() == 3) {
                String expanded = "" + cleanedHex.charAt(0) + cleanedHex.charAt(0)
                        + cleanedHex.charAt(1) + cleanedHex.charAt(1)
                        + cleanedHex.charAt(2) + cleanedHex.charAt(2);
                return new Color(Integer.parseInt(expanded, 16));
            }
        } catch (NumberFormatException ignored) {
            return null;
        }
        return null;
    }
    
    /**
     * Returns the parsed background color for a selector.
     *
     * @param selector CSS selector
     * @return Color object or null if not found
     */
    public Color getBackgroundColor(String selector) {
        return hexToColor(getProperty(selector, "-fx-background-color"));
    }
    
    /**
     * Returns the parsed text color for a selector.
     *
     * @param selector CSS selector
     * @return Color object or null if not found
     */
    public Color getTextColor(String selector) {
        return hexToColor(getProperty(selector, "-fx-text-fill"));
    }
    
    /**
     * Returns the parsed font size for a selector.
     *
     * @param selector CSS selector
     * @return font size in pixels, or -1 if not found/invalid
     */
    public int getFontSize(String selector) {
        String size = getProperty(selector, "-fx-font-size");
        if (size == null) {
            return -1;
        }

        try {
            return Integer.parseInt(size.replace("px", "").trim());
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }
    
    /**
     * Returns the parsed font family for a selector.
     *
     * @param selector CSS selector
     * @return font family name or null if not found
     */
    public String getFontFamily(String selector) {
        String family = getProperty(selector, "-fx-font-family");
        if (family == null) {
            return null;
        }
        return family.replace("\"", "").trim();
    }
}
