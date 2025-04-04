package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class BodyTextItem extends SlideItem {

    // Constructor that requires both text and style
    public BodyTextItem(String text, Style style) {
        super(text, style);
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        Font font = Font.font(style.getFontName().toString(), style.getFontSize());
        gc.setFont(font);
        gc.setFill(style.getFontColor().getColor());
        gc.fillText(getText(), width / 2, height / 2);
    }
}
