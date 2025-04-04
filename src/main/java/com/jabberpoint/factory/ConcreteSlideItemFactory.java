package com.jabberpoint.factory;

import com.jabberpoint.entities.ItemType;
import com.jabberpoint.model.*;
import com.jabberpoint.entities.Style;

public class ConcreteSlideItemFactory {
    public static SlideItem createSlideItem(ItemType type, String content, Style style) {
        switch(type) {
            case TITLE:
                return new TitleItem(content, style);
            case SUBTITLE:
                return new SubtitleItem(content, style);
            case TEXT:
                return new BodyTextItem(content, style);
            case BULLET:
                return new BulletPointItem(content, style);
            case BITMAP:
                // For bitmap items, the content is treated as the image path
                return new BitmapItem(content, style);
            case BACKGROUND:
                return new BackgroundItem(content, style);
            default:
                throw new IllegalArgumentException("Unsupported item type: " + type);
        }
    }
}
