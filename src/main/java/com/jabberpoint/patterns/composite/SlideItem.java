package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;

public abstract class SlideItem {
    protected int level;
    protected String text;
    protected Style style;

    public SlideItem() {
        this.level = 1;
        this.text = "";
    }
    
    public SlideItem(String text, Style style) {
        this.level = 1;
        this.text = text;
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public boolean isValid() {
        return text != null && !text.trim().isEmpty() && style != null;
    }

    public int getLevel() {
        return level;
    }

    public abstract void draw(GraphicsContextWrapper gc, double x, double y);
}
