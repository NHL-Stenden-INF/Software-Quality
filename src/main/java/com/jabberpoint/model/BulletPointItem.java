package com.jabberpoint.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import com.jabberpoint.entities.Style;

public class BulletPointItem extends SlideItem {
    public BulletPointItem(String text, Style style) {
        super(text, style);
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        gc.setFill(getStyle().getFontColor().getJavaFXColor());
        gc.setFont(Font.font(getStyle().getFontName().toString(), 
                           getStyle().getFontSize().getJavaFXFontSize()));
        gc.fillText("• " + getText(), 70, 250);
    }
}