package com.jabberpoint.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import com.jabberpoint.entities.Style;

public class BulletPointItem extends SlideItem {
    private Style style;

    // Constructor accepting both text and style
    public BulletPointItem(String text, Style style) {
        super(text, style);
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 24));
        gc.fillText("• " + getText(), 70, 250); // Indent bullet points
    }
}