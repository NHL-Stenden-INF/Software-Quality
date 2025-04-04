package com.jabberpoint.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import com.jabberpoint.entities.Style;

public class BodyTextItem extends SlideItem {

    // Constructor that requires both text and style
    public BodyTextItem(String text, Style style) {
        super(text, style);  // Call the constructor in SlideItem
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        Font font = Font.font(style.getFontName().toString(), style.getFontSize().getJavaFXFontSize());
        gc.setFont(font);  // Set the font
        gc.setFill(style.getFontColor().getJavaFXColor());  // Set the color
        gc.fillText(getText(), width / 2, height / 2); // Draw text at the center of the specified area
    }
}
