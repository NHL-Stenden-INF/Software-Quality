package com.jabberpoint.presentation;

import com.jabberpoint.entities.FontColor;
import com.jabberpoint.entities.FontName;
import com.jabberpoint.entities.FontSize;
import com.jabberpoint.entities.Style;

public class StyleManager {

    private static Style currentStyle = new Style(FontName.ARIAL, FontSize.MEDIUM, FontColor.BLACK);

    public static Style getCurrentStyle() {
        return currentStyle;
    }

    public static void setCurrentStyle(Style style) {
        currentStyle = style;
    }
}
