package com.jabberpoint.patterns.composite;

import java.io.InputStream;

import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import com.jabberpoint.style.Style;

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
        InputStream inputStream = null;
        try {
            // First try to load from classpath
            inputStream = getClass().getClassLoader().getResourceAsStream(name);
            if (inputStream != null) {
                image = new Image(inputStream);
            } else {
                // If not found, try as a direct file path
                image = new Image(name);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + name);
        } finally {
            // Properly close the input stream if it was opened
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    // Ignore close errors
                }
            }
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