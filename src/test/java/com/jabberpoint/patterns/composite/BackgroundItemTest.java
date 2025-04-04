package com.jabberpoint.patterns.composite;

import com.jabberpoint.BaseTest;
import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the BackgroundItem class.
 */
public class BackgroundItemTest extends BaseTest {
    
    @Mock
    protected GraphicsContextWrapper graphicsContext;
    
    @Mock
    protected Style style;
    
    private BackgroundItem backgroundItem;
    private static final String TEST_IMAGE_PATH = "test-image.jpg";
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        backgroundItem = new BackgroundItem(TEST_IMAGE_PATH, style);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(TEST_IMAGE_PATH, backgroundItem.getImagePath());
        assertEquals(style, backgroundItem.getStyle());
        assertEquals(1, backgroundItem.getLevel());
    }
    
    @Test
    public void testIsValid() {
        assertTrue(backgroundItem.isValid());
        
        // Test with empty image path
        BackgroundItem emptyItem = new BackgroundItem("", style);
        assertFalse(emptyItem.isValid());
        
        // Test with null image path
        BackgroundItem nullPathItem = new BackgroundItem(null, style);
        assertFalse(nullPathItem.isValid());
        
        // Test with null style
        BackgroundItem nullStyleItem = new BackgroundItem(TEST_IMAGE_PATH, null);
        assertFalse(nullStyleItem.isValid());
    }
} 