package com.jabberpoint.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {
    private String title;
    private List<SlideItem> items;
    private String backgroundImage;

    public Slide(String title) {
        this.title = title;
        this.items = new ArrayList<>();
    }

    public Slide addItem(SlideItem item) {
        items.add(item);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public List<SlideItem> getItems() {
        return items;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }    
}
