package com.jabberpoint.model;

public class BitmapItem implements SlideItem {
    private String imagePath;

    public BitmapItem(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
