package com.jabberpoint.entities;

public enum FontSize {
    SMALL(12),
    MEDIUM(16),
    LARGE(24);

    private final double size;

    FontSize(double size) {
        this.size = size;
    }

    public double getJavaFXFontSize() {
        return size; // Returns the font size for JavaFX
    }

    public double getSize() {
        return size;
    }
}
