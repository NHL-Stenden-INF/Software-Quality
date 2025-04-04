package com.jabberpoint.factory;

import com.jabberpoint.entities.ItemType;
import com.jabberpoint.model.SlideItem;
import com.jabberpoint.model.TitleItem;
import com.jabberpoint.model.SubtitleItem;
import com.jabberpoint.model.BodyTextItem;
import com.jabberpoint.model.BulletPointItem;
import com.jabberpoint.entities.Style;
import com.jabberpoint.entities.FontName;
import com.jabberpoint.entities.FontSize;
import com.jabberpoint.entities.FontColor;

public class ConcreteSlideItemFactory {

    // Method to create SlideItem with content and style
    public static SlideItem createSlideItem(ItemType type, String content) {
        // You can change this default style or dynamically load it
        Style defaultStyle = new Style(FontName.ARIAL, FontSize.MEDIUM, FontColor.BLACK);

        switch (type) {
            case TEXT: // For BodyTextItem, pass both content and style
                return new BodyTextItem(content, defaultStyle);
            case TITLE: // For TitleItem, pass both content and style
                return new TitleItem(content, defaultStyle);
            case SUBTITLE: // For SubtitleItem, pass both content and style
                return new SubtitleItem(content, defaultStyle);
            case BULLET: // For BulletPointItem, pass both content and style
                return new BulletPointItem(content, defaultStyle);
            case BITMAP:
                // BitmapItem implementation is missing
                throw new UnsupportedOperationException("Bitmap not implemented in this factory");
            case BACKGROUND:
                // BackgroundItem implementation is missing
                throw new UnsupportedOperationException("Background not implemented here");
            default:
                throw new IllegalArgumentException("Unknown item type: " + type);
        }
    }
}
