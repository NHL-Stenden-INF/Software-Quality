package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the SlideItem abstract class.
 */
public class SlideItemTest {
    
    @Mock
    private GraphicsContextWrapper graphicsContext;
    
    private TestSlideItem slideItem;
    private Style style;
    
    // Concrete implementation for testing
    private static class TestSlideItem extends SlideItem {
        public TestSlideItem() {
            super();
        }
        
        public TestSlideItem(String text, Style style) {
            super(text, style);
        }
        
        @Override
        public void draw(GraphicsContextWrapper gc, double x, double y) {
            // Test implementation
        }
    }
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        slideItem = new TestSlideItem("Test Text", style);
    }
    
    @Test
    public void testDefaultConstructor() {
        SlideItem item = new TestSlideItem();
        assertEquals(1, item.getLevel());
        assertEquals("", item.getText());
        assertNull(item.getStyle());
    }
    
    @Test
    public void testParameterizedConstructor() {
        assertEquals("Test Text", slideItem.getText());
        assertEquals(style, slideItem.getStyle());
        assertEquals(1, slideItem.getLevel());
    }
    
    @Test
    public void testSetStyle() {
        Style newStyle = new Style(FontName.TIMES_NEW_ROMAN, FontName.COURIER, FontColor.RED, FontColor.BLUE);
        slideItem.setStyle(newStyle);
        assertEquals(newStyle, slideItem.getStyle());
    }
    
    @Test
    public void testIsValidWithValidData() {
        assertTrue(slideItem.isValid());
    }
    
    @Test
    public void testIsValidWithInvalidData() {
        SlideItem emptyItem = new TestSlideItem("", style);
        assertFalse(emptyItem.isValid());
        
        SlideItem nullTextItem = new TestSlideItem(null, style);
        assertFalse(nullTextItem.isValid());
        
        SlideItem nullStyleItem = new TestSlideItem("Text", null);
        assertFalse(nullStyleItem.isValid());
        
        SlideItem whitespaceItem = new TestSlideItem("   ", style);
        assertFalse(whitespaceItem.isValid());
    }
    
    @Test
    public void testGetLevel() {
        assertEquals(1, slideItem.getLevel());
    }
} 