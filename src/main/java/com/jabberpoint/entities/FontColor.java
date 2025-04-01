package com.jabberpoint.entities;

public enum FontColor {
    BLACK("#000000"),
    BLUE("#0000FF"),
    RED("#FF0000"),
    GREEN("#008000");

    private String css;

    FontColor(String css) {
        this.css = css;
    }

    public String toCss() {
        return css;
    }
}
