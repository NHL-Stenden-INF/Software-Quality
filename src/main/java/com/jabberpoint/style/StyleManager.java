package com.jabberpoint.style;

public class StyleManager {
    private static Style currentStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);

    public static Style getCurrentStyle() {
        return currentStyle;
    }

    public static void setCurrentStyle(Style style) {
        currentStyle = style;
    }

    public static void loadStyleFromXML(String fontName, String fontSize, String fontColor) {
        try {
            FontName fontNameEnum = FontName.valueOf(fontName.toUpperCase());
            FontColor fontColorEnum = FontColor.fromString(fontColor);
            currentStyle = new Style(fontNameEnum, fontNameEnum, fontColorEnum, fontColorEnum);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid style properties in XML: " + e.getMessage());
        }
    }
}
