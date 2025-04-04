package com.jabberpoint.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import com.jabberpoint.entities.Style;

public class TitleItem extends SlideItem {

    // Constructor that requires both text and style
    public TitleItem(String text, Style style) {
        super(text, style);
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        Font font = Font.font(style.getFontName().toString(), style.getFontSize().getJavaFXFontSize());
        gc.setFont(font);
        gc.setFill(style.getFontColor().getJavaFXColor());
        gc.fillText(getText(), width / 2, height / 2);
    }
}
