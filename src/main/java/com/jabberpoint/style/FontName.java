package com.jabberpoint.style;

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
        return Font.font(name);
    }

    public String getName() {
        return name;
    }
}
