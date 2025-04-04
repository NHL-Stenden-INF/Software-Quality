package com.jabberpoint.model;

import com.jabberpoint.entities.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BitmapItem extends SlideItem {
    private final Image image;

    public BitmapItem(String imagePath, Style style) {
        super("", style);
        if (imagePath.startsWith("file:")) {
            this.image = new Image(imagePath);
        } else {
            this.image = new Image("file:" + imagePath);
        }
    }

    @Override
    public void draw(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        // Define the maximum area for the image
        double maxWidth = canvasWidth - 100;
        double maxHeight = 200;

        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        // Calculate scale preserving aspect ratio
        double scaleX = maxWidth / imageWidth;
        double scaleY = maxHeight / imageHeight;
        double scale = Math.min(scaleX, scaleY);

        double drawWidth = imageWidth * scale;
        double drawHeight = imageHeight * scale;

        // Center the image within the designated area
        double x = 50 + (maxWidth - drawWidth) / 2;
        double y = 300 + (maxHeight - drawHeight) / 2;

        gc.drawImage(image, x, y, drawWidth, drawHeight);
    }
}