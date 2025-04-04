package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class BulletPointItem extends SlideItem {
    public BulletPointItem(String text, Style style) {
        super(text, style);
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        // Draw bullet point
        gc.setFill(style.getFontColor().getColor());
        gc.fillOval(x - 10, y - 5, 5, 5);

        // Draw text
        Font font = Font.font(style.getFontName().toString(), style.getFontSize());
        gc.setFont(font);
        gc.fillText(getText(), x, y);
    }
}