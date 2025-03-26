package com.jabberpoint;

public class StyleManager {

    private static Style currentStyle = new Style(FontName.ARIAL, FontSize.MEDIUM, FontColor.BLACK);

    public static Style getCurrentStyle() {
        return currentStyle;
    }

    public static void setCurrentStyle(Style style) {
        currentStyle = style;
    }
}

