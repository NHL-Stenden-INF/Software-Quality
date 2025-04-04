package com.jabberpoint.patterns.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jabberpoint.style.Style;

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

    public Slide addItems(List<SlideItem> newItems) {
        items.addAll(newItems);
        return this;
    }

    public List<SlideItem> getItems() {
        return new ArrayList<>(items);
    }

    public <T extends SlideItem> List<T> getItemsByType(Class<T> type) {
        return items.stream()
            .filter(type::isInstance)
            .map(type::cast)
            .collect(Collectors.toList());
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

    public void updateItemStyle(Style newStyle) {
        items.forEach(item -> item.setStyle(newStyle));
    }

    public void reorderItems(int fromIndex, int toIndex) {
        if (fromIndex >= 0 && fromIndex < items.size() &&
            toIndex >= 0 && toIndex < items.size()) {
            SlideItem item = items.remove(fromIndex);
            items.add(toIndex, item);
        }
    }

    public boolean isValid() {
        return title != null && !title.trim().isEmpty() &&
               items.stream().allMatch(SlideItem::isValid);
    }
}