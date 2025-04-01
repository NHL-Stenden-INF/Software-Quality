package com.jabberpoint.factory;

import com.jabberpoint.entities.ItemType;
import com.jabberpoint.model.BitmapItem;
import com.jabberpoint.model.SlideItem;
import com.jabberpoint.model.TextItem;

public class ConcreteSlideItemFactory {

    public static SlideItem createSlideItem(ItemType type, String content) {
        switch (type) {
            case TEXT:
                return new TextItem(content);
            case BITMAP:
                return new BitmapItem(content);
            default:
                throw new IllegalArgumentException("Unknown item type: " + type);
        }
    }
}
