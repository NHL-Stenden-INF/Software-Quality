package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the BitmapItem class.
 */
public class BitmapItemTest {
    
    @Mock
    private GraphicsContextWrapper graphicsContext;
    
    private BitmapItem bitmapItem;
    private Style style;
    private static final String TEST_IMAGE_PATH = "file:test.png";
    private static final double TEST_X = 100.0;
    private static final double TEST_Y = 200.0;
    private static final int TEST_LEVEL = 2;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        bitmapItem = new BitmapItem(TEST_IMAGE_PATH, style);
    }
    
    @Test
    public void testConstructorWithStyle() {
        assertEquals(TEST_IMAGE_PATH, bitmapItem.getName());
        assertEquals(TEST_IMAGE_PATH, bitmapItem.getText());
        assertEquals(style, bitmapItem.getStyle());
        assertEquals(1, bitmapItem.getLevel());
    }
    
    @Test
    public void testConstructorWithLevel() {
        BitmapItem levelBitmapItem = new BitmapItem(TEST_LEVEL, TEST_IMAGE_PATH);
        assertEquals(TEST_IMAGE_PATH, levelBitmapItem.getName());
        assertEquals(TEST_IMAGE_PATH, levelBitmapItem.getText());
        assertEquals(TEST_LEVEL, levelBitmapItem.getLevel());
        assertNull(levelBitmapItem.getStyle());
    }
    
    @Test
    public void testDrawWithNullImage() {
        // Create a BitmapItem with an invalid image path
        BitmapItem invalidBitmapItem = new BitmapItem("invalid_path.png", style);
        
        // Call draw method
        invalidBitmapItem.draw(graphicsContext, TEST_X, TEST_Y);
        
        // Verify that drawImage was never called due to null image
        verify(graphicsContext, never()).drawImage(any(Image.class), anyDouble(), anyDouble());
    }
    
    @Test
    public void testIsValid() {
        assertTrue(bitmapItem.isValid());
        
        // Test with empty name
        BitmapItem emptyItem = new BitmapItem("", style);
        assertFalse(emptyItem.isValid());
        
        // Test with null name
        BitmapItem nullNameItem = new BitmapItem(TEST_LEVEL, null);
        assertFalse(nullNameItem.isValid());
        
        // Test with null style (valid for BitmapItem when using level constructor)
        BitmapItem nullStyleItem = new BitmapItem(TEST_LEVEL, TEST_IMAGE_PATH);
        assertTrue(nullStyleItem.isValid());
    }
    
    @Test
    public void testGetters() {
        assertEquals(TEST_IMAGE_PATH, bitmapItem.getName());
        assertEquals(TEST_IMAGE_PATH, bitmapItem.getText());
        assertEquals(style, bitmapItem.getStyle());
    }
} 