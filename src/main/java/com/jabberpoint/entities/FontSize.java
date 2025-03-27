package com.jabberpoint.entities;

public enum FontSize {
    SMALL(14, 10),
    MEDIUM(18, 14),
    LARGE(24, 18);

    private int titleSize;
    private int textSize;

    FontSize(int titleSize, int textSize) {
        this.titleSize = titleSize;
        this.textSize = textSize;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public int getTextSize() {
        return textSize;
    }
}
