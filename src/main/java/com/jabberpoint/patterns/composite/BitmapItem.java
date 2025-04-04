package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import javafx.scene.image.Image;

public class BitmapItem extends SlideItem {
    private String name;
    private Image image;

    public BitmapItem(int level, String name) {
        super();
        this.level = level;
        this.name = name;
        loadImage();
    }
    
    public BitmapItem(String name, Style style) {
        super(name, style);
        this.name = name;
        loadImage();
    }

    private void loadImage() {
        try {
            image = new Image(name);
        } catch (Exception e) {
            System.err.println("Error loading image: " + name);
        }
    }

    @Override
    public String getText() {
        return name;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public void draw(GraphicsContextWrapper gc, double x, double y) {
        if (image != null) {
            gc.drawImage(image, x, y);
        }
    }
}