package com.jabberpoint.patterns.factory;

import com.jabberpoint.patterns.composite.*;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.Style;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SlideItemFactory class.
 */
class SlideItemFactoryTest {

    private Style style;

    @BeforeEach
    public void setUp() {
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
    }

    @Test
    public void testCreateSlideItemTitle() {
        // Test for a title
        SlideItem titleItem = SlideItemFactory.createSlideItem(ItemType.TITLE, "Test title", style);
        assertInstanceOf(TitleItem.class, titleItem, "Expected: Test title");

        // Test for a subtitle
        SlideItem subtitleItem = SlideItemFactory.createSlideItem(ItemType.SUBTITLE, "Test subtitle", style);
        assertInstanceOf(SubtitleItem.class, subtitleItem, "Expected: Test subtitle");

        // Test for a Text
        SlideItem textItem = SlideItemFactory.createSlideItem(ItemType.TEXT, "Test text", style);
        assertInstanceOf(SlideItem.class, textItem, "Expected: Test text");

        // Test for a bullet point
        SlideItem bulletItem = SlideItemFactory.createSlideItem(ItemType.BULLET, "Test bullet", style);
        assertInstanceOf(BulletPointItem.class, bulletItem, "Expected: Test bullet");

        // Test for a bitmap
        SlideItem bitmapItem = SlideItemFactory.createSlideItem(ItemType.BITMAP, "path/to/test.jpg", style);
        assertInstanceOf(SlideItem.class, bitmapItem, "Expected: path/to/test.jpg");

        // Test for a background
        SlideItem backgroundItem = SlideItemFactory.createSlideItem(ItemType.BACKGROUND, "Test background", style);
        assertInstanceOf(BackgroundItem.class, backgroundItem, "Expected: Test background");

        // Test for unsupported item
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            SlideItemFactory.createSlideItem(null, "Invalid item", style);
        });

        assertEquals("Item cannot be null", exception.getMessage());
    }
}