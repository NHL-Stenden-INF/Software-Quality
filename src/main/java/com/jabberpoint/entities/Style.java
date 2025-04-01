package com.jabberpoint.entities;

public class Style {
    private FontName fontName;
    private FontSize fontSize;
    private FontColor fontColor;

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
