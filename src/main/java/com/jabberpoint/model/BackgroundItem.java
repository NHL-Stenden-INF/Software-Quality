package com.jabberpoint.model;

import com.jabberpoint.entities.Style;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundItem extends SlideItem {
    private final String imagePath;

    // Constructor expecting both imagePath and style
    public BackgroundItem(String imagePath, Style style) {
        super("", style);  // Pass empty text and style
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        Image image = new Image(imagePath); // Load the image
        gc.drawImage(image, 0, 0, width, height);
    }
}
