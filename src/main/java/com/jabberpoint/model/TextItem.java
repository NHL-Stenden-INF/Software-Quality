package com.jabberpoint.model;

public class TextItem implements SlideItem {
    private String text;

    public TextItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
