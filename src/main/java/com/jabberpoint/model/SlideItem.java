package com.jabberpoint.model;

import javafx.scene.canvas.GraphicsContext;
import com.jabberpoint.entities.Style;

public abstract class SlideItem {
    protected String text;
    protected Style style;

    public SlideItem(String text, Style style) {
        this.text = text;
        this.style = style; 
    }

    public String getText() {
        return text;
    }

    public Style getStyle() {
        return style;
    }

    public abstract void draw(GraphicsContext gc, double width, double height);
}
