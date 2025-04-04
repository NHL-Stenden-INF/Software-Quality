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
 * Tests for the BackgroundItem class.
 */
public class BackgroundItemTest {
    
    @Mock
    private GraphicsContextWrapper graphicsContext;
    
    private BackgroundItem backgroundItem;
    private Style style;
    private static final String TEST_IMAGE_PATH = "file:background.png";
    private static final double TEST_WIDTH = 800.0;
    private static final double TEST_HEIGHT = 600.0;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        backgroundItem = new BackgroundItem(TEST_IMAGE_PATH, style);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(TEST_IMAGE_PATH, backgroundItem.getImagePath());
        assertEquals("", backgroundItem.getText()); // Text should be empty for background items
        assertEquals(style, backgroundItem.getStyle());
        assertEquals(1, backgroundItem.getLevel());
    }
    
    @Test
    public void testGetImagePath() {
        assertEquals(TEST_IMAGE_PATH, backgroundItem.getImagePath());
    }
    
    @Test
    public void testIsValid() {
        // A BackgroundItem is valid even with empty text if it has a style
        assertTrue(backgroundItem.isValid());
        
        // Test with null style
        BackgroundItem nullStyleItem = new BackgroundItem(TEST_IMAGE_PATH, null);
        assertFalse(nullStyleItem.isValid());
    }
    
    @Test
    public void testDraw() {
        // We can't verify the Image constructor directly since it's final
        // But we can verify that drawImage was called with the correct parameters
        
        // Call draw method
        backgroundItem.draw(graphicsContext, TEST_WIDTH, TEST_HEIGHT);
        
        // Verify that drawImage was called with the correct parameters
        verify(graphicsContext).drawImage(any(Image.class), eq(0.0), eq(0.0), eq(TEST_WIDTH), eq(TEST_HEIGHT));
    }
    
    @Test
    public void testDrawWithDifferentDimensions() {
        double customWidth = 1024.0;
        double customHeight = 768.0;
        
        // Call draw method with different dimensions
        backgroundItem.draw(graphicsContext, customWidth, customHeight);
        
        // Verify that drawImage was called with the correct parameters
        verify(graphicsContext).drawImage(any(Image.class), eq(0.0), eq(0.0), eq(customWidth), eq(customHeight));
    }
} 