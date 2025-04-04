package com.jabberpoint.entities;

import javafx.scene.paint.Color;

public enum FontColor {
    BLACK(Color.BLACK),
    RED(Color.RED),
    BLUE(Color.BLUE);

    private final Color color;

    FontColor(Color color) {
        this.color = color;
    }

    public Color getJavaFXColor() {
        return color; // Returns the JavaFX Color
    }

    public Color getColor() {
        return color;
    }
}
