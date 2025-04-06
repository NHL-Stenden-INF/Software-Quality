package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the Slide class.
 */
public class SlideTest {
    
    private Slide slide;
    private Style style;
    private static final String TEST_TITLE = "Test Slide";
    private static final String TEST_BACKGROUND = "background.png";
    
    @BeforeEach
    public void setUp() {
        slide = new Slide(TEST_TITLE);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
    }
    
    @Test
    public void testDefaultConstructor() {
        Slide defaultSlide = new Slide();
        assertEquals("", defaultSlide.getTitle());
        assertTrue(defaultSlide.getItems().isEmpty());
        assertNull(defaultSlide.getBackground());
    }
    
    @Test
    public void testTitleConstructor() {
        assertEquals(TEST_TITLE, slide.getTitle());
        assertTrue(slide.getItems().isEmpty());
        assertNull(slide.getBackground());
    }
    
    @Test
    public void testSetAndGetTitle() {
        String newTitle = "New Title";
        slide.setTitle(newTitle);
        assertEquals(newTitle, slide.getTitle());
    }
    
    @Test
    public void testSetAndGetBackground() {
        slide.setBackground(TEST_BACKGROUND);
        assertEquals(TEST_BACKGROUND, slide.getBackground());
        assertEquals(TEST_BACKGROUND, slide.getBackgroundImagePath());
    }
    
    @Test
    public void testAddAndGetItems() {
        SlideItem item1 = new TitleItem("Title", style);
        SlideItem item2 = new BodyTextItem("Body", style);
        
        slide.addItem(item1);
        slide.addItem(item2);
        
        List<SlideItem> items = slide.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }
    
    @Test
    public void testAddItems() {
        SlideItem item1 = new TitleItem("Title", style);
        SlideItem item2 = new BodyTextItem("Body", style);
        List<SlideItem> newItems = Arrays.asList(item1, item2);
        
        slide.addItems(newItems);
        
        List<SlideItem> items = slide.getItems();
        assertEquals(2, items.size());
        assertTrue(items.containsAll(newItems));
    }
    
    @Test
    public void testGetItemsByType() {
        TitleItem titleItem = new TitleItem("Title", style);
        BodyTextItem bodyItem1 = new BodyTextItem("Body 1", style);
        BodyTextItem bodyItem2 = new BodyTextItem("Body 2", style);
        
        slide.addItem(titleItem);
        slide.addItem(bodyItem1);
        slide.addItem(bodyItem2);
        
        List<TitleItem> titleItems = slide.getItemsByType(TitleItem.class);
        List<BodyTextItem> bodyItems = slide.getItemsByType(BodyTextItem.class);
        
        assertEquals(1, titleItems.size());
        assertEquals(2, bodyItems.size());
        assertTrue(titleItems.contains(titleItem));
        assertTrue(bodyItems.contains(bodyItem1));
        assertTrue(bodyItems.contains(bodyItem2));
    }
    
    @Test
    public void testGetBulletPoints() {
        BulletPointItem bullet1 = new BulletPointItem("Bullet 1", style);
        BulletPointItem bullet2 = new BulletPointItem("Bullet 2", style);
        BodyTextItem bodyItem = new BodyTextItem("Body", style);
        
        slide.addItem(bullet1);
        slide.addItem(bodyItem);
        slide.addItem(bullet2);
        
        List<String> bulletPoints = slide.getBulletPoints();
        assertEquals(2, bulletPoints.size());
        assertTrue(bulletPoints.contains("Bullet 1"));
        assertTrue(bulletPoints.contains("Bullet 2"));
        assertFalse(bulletPoints.contains("Body"));
    }
    
    @Test
    public void testClearItems() {
        slide.addItem(new TitleItem("Title", style));
        slide.addItem(new BodyTextItem("Body", style));
        
        assertFalse(slide.getItems().isEmpty());
        slide.clearItems();
        assertTrue(slide.getItems().isEmpty());
    }
    
    @Test
    public void testUpdateItemStyle() {
        TitleItem titleItem = new TitleItem("Title", style);
        BodyTextItem bodyItem = new BodyTextItem("Body", style);
        slide.addItem(titleItem);
        slide.addItem(bodyItem);
        
        Style newStyle = new Style(FontName.TIMES_NEW_ROMAN, FontName.COURIER, FontColor.RED, FontColor.BLUE);
        slide.updateItemStyle(newStyle);
        
        assertEquals(newStyle, titleItem.getStyle());
        assertEquals(newStyle, bodyItem.getStyle());
    }
    
    @Test
    public void testReorderItems() {
        SlideItem item1 = new TitleItem("Title 1", style);
        SlideItem item2 = new TitleItem("Title 2", style);
        SlideItem item3 = new TitleItem("Title 3", style);
        
        slide.addItem(item1);
        slide.addItem(item2);
        slide.addItem(item3);
        
        slide.reorderItems(0, 2); // Move item1 to the end
        
        List<SlideItem> items = slide.getItems();
        assertEquals(item2, items.get(0));
        assertEquals(item3, items.get(1));
        assertEquals(item1, items.get(2));
    }
    
    @Test
    public void testReorderItemsInvalidIndices() {
        SlideItem item1 = new TitleItem("Title 1", style);
        SlideItem item2 = new TitleItem("Title 2", style);
        
        slide.addItem(item1);
        slide.addItem(item2);
        
        // Try to reorder with invalid indices
        slide.reorderItems(-1, 1);
        slide.reorderItems(0, 3);
        
        // Order should remain unchanged
        List<SlideItem> items = slide.getItems();
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }
    
    @Test
    public void testIsValid() {
        // Empty slide with title should be valid
        assertTrue(slide.isValid());
        
        // Slide with null title should be invalid
        slide.setTitle(null);
        assertFalse(slide.isValid());
        
        // Slide with empty title should be invalid
        slide.setTitle("");
        assertFalse(slide.isValid());
        
        // Slide with whitespace title should be invalid
        slide.setTitle("   ");
        assertFalse(slide.isValid());
        
        // Slide with valid title and valid items should be valid
        slide.setTitle(TEST_TITLE);
        slide.addItem(new TitleItem("Valid Title", style));
        assertTrue(slide.isValid());
        
        // Slide with invalid item should be invalid
        slide.addItem(new TitleItem("", style)); // Invalid item (empty text)
        assertFalse(slide.isValid());
    }
} 