package com.jabberpoint.entities;

import javafx.scene.paint.Color;

public enum FontColor {
    BLACK(Color.BLACK),
    RED(Color.RED),
    BLUE(Color.BLUE),
    PURPLE(Color.PURPLE),
    ORANGE(Color.ORANGE);

    private final Color color;

    FontColor(Color color) {
        this.color = color;
    }

    public Color getJavaFXColor() {
        return color;
    }

    public Color getColor() {
        return color;
    }

    // Method to handle defaulting to BLACK if the value is invalid
    public static FontColor fromString(String colorStr) {
        try {
            return FontColor.valueOf(colorStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid fontColor value, applying default: " + colorStr);
            return FontColor.BLACK; // Default to BLACK
        }
    }
}
