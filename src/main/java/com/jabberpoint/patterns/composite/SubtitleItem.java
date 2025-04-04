package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SubtitleItem extends SlideItem {
    private Style style;

    public SubtitleItem(String text, Style style) {
        super(text, style);
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.DARKGRAY);
        gc.setFont(Font.font("Arial", 36));
        gc.fillText(getText(), 50, 150);
    }
}