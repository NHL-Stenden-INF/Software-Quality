package com.jabberpoint.patterns.factory;

import com.jabberpoint.patterns.composite.BackgroundItem;
import com.jabberpoint.patterns.composite.BitmapItem;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.patterns.composite.BulletPointItem;
import com.jabberpoint.patterns.composite.SlideItem;
import com.jabberpoint.patterns.composite.SubtitleItem;
import com.jabberpoint.patterns.composite.TitleItem;
import com.jabberpoint.style.Style;

public class SlideItemFactory {
    public static SlideItem createSlideItem(ItemType type, String content, Style style) {
        if (type == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

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
