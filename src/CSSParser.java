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
    
    // TODO: Add a field to store parsed styles
    // Structure: Map<String, Map<String, String>>
    // Example: styles.get(".button").get("-fx-background-color") returns "#333333"
    
    /**
     * Constructor - Initialize the parser
     * TODO: Initialize the styles map (HashMap)
     */
    public CSSParser() {
        // TODO: Create new HashMap for styles
    }
    
    /**
     * Load and parse a CSS file
     * @param filePath Path to the CSS file to load
     * 
     * TODO: 
     * 1. Use BufferedReader and FileReader to read the file
     * 2. Read all lines into a StringBuilder
     * 3. Call parseCSS() with the full content as a String
     * 4. Handle IOException if file doesn't exist
     */
    public void loadCSS(String filePath) {
        // TODO: Implement file reading logic
    }
    
    /**
     * Parse CSS content and extract rules
     * @param cssContent The full CSS content as a String
     * 
     * TODO:
     * 1. Remove comments using regex: replaceAll("/\\*.*?\\*/", "")
     * 2. Use regex to find rules: Pattern.compile("([^{]+)\\{([^}]+)\\}")
     *    - This captures: selector { properties }
     * 3. For each match, extract selector and properties
     * 4. Call parseProperties() on the properties string
     * 5. Store in the styles map: styles.put(selector, propertyMap)
     */
    private void parseCSS(String cssContent) {
        // TODO: Implement CSS parsing logic
    }
    
    /**
     * Parse properties from a CSS rule
     * @param propertiesStr A properties string (e.g., "-fx-color: #333333; -fx-size: 14px;")
     * @return Map of property names to values
     * 
     * TODO:
     * 1. Split by semicolon: split(";")
     * 2. For each property pair, split by colon: split(":", 2)
     * 3. Trim whitespace from key and value
     * 4. Store in a HashMap
     * 5. Return the map
     */
    private Map<String, String> parseProperties(String propertiesStr) {
        // TODO: Implement properties parsing logic
        return null;
    }
    
    /**
     * Get a property value for a selector
     * @param selector CSS selector (e.g., ".button")
     * @param property Property name (e.g., "-fx-background-color")
     * @return Property value as String, or null if not found
     * 
     * TODO:
     * 1. Look up selector in styles map
     * 2. If found, look up property in the selector's map
     * 3. Return the value or null if not found
     */
    public String getProperty(String selector, String property) {
        // TODO: Implement property lookup
        return null;
    }
    
    /**
     * Convert hex color string to Java Color
     * @param hexColor Hex color string (e.g., "#202020" or "202020")
     * @return Java Color object, or null if invalid
     * 
     * TODO:
     * 1. Remove "#" if present using replace("#", "")
     * 2. Parse hex string to integer: Integer.parseInt(hexColor, 16)
     * 3. Create Color object: new Color(parsedInt)
     * 4. Catch NumberFormatException and return null if invalid
     */
    public static Color hexToColor(String hexColor) {
        // TODO: Implement hex to color conversion
        return null;
    }
    
    /**
     * Get background color for a selector
     * @param selector CSS selector
     * @return Color object or null if not found
     * 
     * TODO:
     * 1. Use getProperty() to get "-fx-background-color"
     * 2. Pass the result to hexToColor() to convert to Color
     * 3. Return the Color
     */
    public Color getBackgroundColor(String selector) {
        // TODO: Implement background color getter
        return null;
    }
    
    /**
     * Get text color for a selector
     * @param selector CSS selector
     * @return Color object or null if not found
     * 
     * TODO:
     * 1. Use getProperty() to get "-fx-text-fill"
     * 2. Pass the result to hexToColor()
     * 3. Return the Color
     */
    public Color getTextColor(String selector) {
        // TODO: Implement text color getter
        return null;
    }
    
    /**
     * Get font size for a selector
     * @param selector CSS selector
     * @return Font size in pixels, or -1 if not found
     * 
     * TODO:
     * 1. Use getProperty() to get "-fx-font-size"
     * 2. Remove "px" from the string
     * 3. Parse to int using Integer.parseInt()
     * 4. Catch NumberFormatException and return -1 if invalid
     */
    public int getFontSize(String selector) {
        // TODO: Implement font size getter
        return -1;
    }
    
    /**
     * Get font family for a selector
     * @param selector CSS selector
     * @return Font family name or null if not found
     * 
     * TODO:
     * 1. Use getProperty() to get "-fx-font-family"
     * 2. Remove quotes: replace("\"", "")
     * 3. Trim whitespace
     * 4. Return the font family name
     */
    public String getFontFamily(String selector) {
        // TODO: Implement font family getter
        return null;
    }
}
