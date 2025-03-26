package com.jabberpoint.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {
    private String title;
    private List<SlideItem> items;

    public Slide(String title) {
        this.title = title;
        items = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addItem(SlideItem item) {
        items.add(item);
    }

    public List<SlideItem> getItems() {
        return items;
    }
}
