package com.jabberpoint.style;

import javafx.scene.text.Font;

public class Style {
    private static final double TITLE_FONT_SIZE = 40.0;
    private static final double BODY_FONT_SIZE = 20.0;

    private FontName titleFontName;
    private FontName bodyFontName;
    private FontColor titleColor;
    private FontColor bodyColor;
    private FontSize fontSize = FontSize.MEDIUM; // Default font size

    public Style() {
        this.titleFontName = FontName.ARIAL;
        this.bodyFontName = FontName.ARIAL;
        this.titleColor = FontColor.BLACK;
        this.bodyColor = FontColor.BLACK;
    }
    
    public Style(FontName titleFontName, FontName bodyFontName, FontColor titleColor, FontColor bodyColor) {
        this.titleFontName = titleFontName;
        this.bodyFontName = bodyFontName;
        this.titleColor = titleColor;
        this.bodyColor = bodyColor;
    }

    public Font getTitleFont() {
        return titleFontName.getFont(TITLE_FONT_SIZE);
    }

    public Font getBodyFont() {
        return bodyFontName.getFont(BODY_FONT_SIZE);
    }

    public FontName getTitleFontName() {
        return titleFontName;
    }

    public FontName getBodyFontName() {
        return bodyFontName;
    }

    public FontColor getTitleColor() {
        return titleColor;
    }

    public FontColor getBodyColor() {
        return bodyColor;
    }

    public void setTitleFontName(FontName fontName) {
        this.titleFontName = fontName;
    }

    public void setBodyFontName(FontName fontName) {
        this.bodyFontName = fontName;
    }

    public void setTitleColor(FontColor color) {
        this.titleColor = color;
    }

    public void setBodyColor(FontColor color) {
        this.bodyColor = color;
    }
    
    public FontSize getFontSize() {
        return fontSize;
    }
    
    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }
    
    // Methods for compatibility with existing code
    public FontName getFontName() {
        return bodyFontName;
    }
    
    public double getFontSizeValue() {
        return fontSize != null ? fontSize.getSize() : BODY_FONT_SIZE;
    }
    
    public FontColor getFontColor() {
        return bodyColor;
    }
}
