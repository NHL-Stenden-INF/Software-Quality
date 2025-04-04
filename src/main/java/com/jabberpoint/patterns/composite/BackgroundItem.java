package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundItem extends SlideItem {
    private final String imagePath;

    // Constructor expecting both imagePath and style
    public BackgroundItem(String imagePath, Style style) {
        super("", style);
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        Image image = new Image(imagePath);
        gc.drawImage(image, 0, 0, width, height);
    }
}
