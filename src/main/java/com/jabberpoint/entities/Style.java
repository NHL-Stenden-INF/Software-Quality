package com.jabberpoint.entities;

public class Style {
    private final FontName fontName;
    private final FontSize fontSize;
    private final FontColor fontColor;

    public Style(FontName fontName, FontSize fontSize, FontColor fontColor) {
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    public FontName getFontName() {
        return fontName;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public FontColor getFontColor() {
        return fontColor;
    }
}
