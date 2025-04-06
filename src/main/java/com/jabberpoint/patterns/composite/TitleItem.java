package com.jabberpoint.patterns.composite;

import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import com.jabberpoint.style.Style;

import javafx.scene.text.Font;

public class TitleItem extends SlideItem {

    // Constructor that requires both text and style
    public TitleItem(String text, Style style) {
        super(text, style);
    }

    @Override
    public void draw(GraphicsContextWrapper gc, double width, double height) {
        Font font = Font.font(style.getFontName().toString(), style.getFontSizeValue());
        gc.setFont(font);
        gc.setFill(style.getFontColor().getColor());
        gc.fillText(getText(), width / 2, height / 4);
    }
}
