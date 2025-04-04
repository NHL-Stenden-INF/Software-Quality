package com.jabberpoint.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Slide {
    private final String title;
    private final List<SlideItem> items = new ArrayList<>();

    public Slide(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Slide addItem(SlideItem item) {
        items.add(item);
        return this;
    }

    public List<SlideItem> getItems() {
        return new ArrayList<>(items);
    }

    public String getBackgroundImagePath() {
        return items.stream()
            .filter(BackgroundItem.class::isInstance)
            .map(BackgroundItem.class::cast)
            .findFirst()
            .map(BackgroundItem::getImagePath)
            .orElse(null);
    }

    public List<String> getBulletPoints() {
        return items.stream()
                .filter(item -> item instanceof BulletPointItem)
                .map(SlideItem::getText)
                .collect(Collectors.toList());
    }

    public void clearItems() {
        items.clear();
    }
}