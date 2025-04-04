package com.jabberpoint.entities;

import javafx.scene.text.Font;

public enum FontName {
    ARIAL("Arial"),
    VERDANA("Verdana"),
    TIMES("Times New Roman");

    private final String name;

    FontName(String name) {
        this.name = name;
    }

    public Font getJavaFXFont() {
        return Font.font(name); // This will return the corresponding Font for JavaFX
    }

    public String getName() {
        return name;
    }
}
