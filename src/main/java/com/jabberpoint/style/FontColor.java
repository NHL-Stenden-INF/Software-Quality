package com.jabberpoint.style;

import javafx.scene.paint.Color;

public enum FontColor {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE),
    RED(Color.RED),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    CYAN(Color.CYAN),
    MAGENTA(Color.MAGENTA);

    private final Color color;

    FontColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // Method to handle defaulting to BLACK if the value is invalid
    public static FontColor fromString(String colorStr) {
        if (colorStr == null) {
            System.err.println("Invalid fontColor value, applying default: ");
            return FontColor.BLACK;
        }
        
        try {
            return FontColor.valueOf(colorStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid fontColor value, applying default: " + colorStr);
            return FontColor.BLACK; 
        }
    }
}
