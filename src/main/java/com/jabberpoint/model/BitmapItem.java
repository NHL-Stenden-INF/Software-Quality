package com.jabberpoint.model;

import com.jabberpoint.entities.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BitmapItem extends SlideItem {
    private final Image image;

    public BitmapItem(String imagePath, Style style) {
        super("",style);
        this.image = new Image("file:" + imagePath);
    }

    @Override
    public void draw(GraphicsContext gc, double width, double height) {
        gc.drawImage(image, 50, 300, width - 100, 200);
    }
}