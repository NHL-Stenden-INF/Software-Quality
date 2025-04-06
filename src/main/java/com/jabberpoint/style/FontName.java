package com.jabberpoint.style;

import javafx.scene.text.Font;

public enum FontName {
    ARIAL("Arial"),
    CALIBRI("Calibri"),
    COMIC_SANS("Comic Sans MS"),
    COURIER("Courier New"),
    GEORGIA("Georgia"),
    IMPACT("Impact"),
    TAHOMA("Tahoma"),
    TIMES_NEW_ROMAN("Times New Roman"),
    VERDANA("Verdana");

    private final String name;

    FontName(String name) {
        this.name = name;
    }

    public Font getFont(double size) {
        return Font.font(name, size);
    }

    public String getName() {
        return name;
    }
}
