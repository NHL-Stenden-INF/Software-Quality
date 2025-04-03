package com.jabberpoint.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {
    private String title;
    private List<SlideItem> items;
    private BackgroundItem background;

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

    public BackgroundItem getBackground() {
        return background;
    }

    public void setBackground(BackgroundItem background) {
        this.background = background;
    }

    public String getBackgroundImage() {
        return (background != null) ? background.getBackgroundPath() : null;
    }

    public void setBackgroundImage(String backgroundImage) {
        if (background == null) {
            this.background = new BackgroundItem(backgroundImage);
        } else {
            this.background.setBackgroundPath(backgroundImage);
        }
    }
}
