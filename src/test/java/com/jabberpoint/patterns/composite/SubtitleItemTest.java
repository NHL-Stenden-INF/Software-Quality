package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the SubtitleItem class.
 */
public class SubtitleItemTest {
    
    @Mock
    private GraphicsContext graphicsContext;
    
    private SubtitleItem subtitleItem;
    private Style style;
    private static final String TEST_TEXT = "Test Subtitle";
    private static final double TEST_WIDTH = 800.0;
    private static final double TEST_HEIGHT = 600.0;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        subtitleItem = new SubtitleItem(TEST_TEXT, style);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(TEST_TEXT, subtitleItem.getText());
        assertEquals(style, subtitleItem.getStyle());
        assertEquals(1, subtitleItem.getLevel());
    }
    
    @Test
    public void testGetStyle() {
        assertEquals(style, subtitleItem.getStyle());
    }
    
    @Test
    public void testDraw() {
        // Call draw method
        subtitleItem.draw(graphicsContext, TEST_WIDTH, TEST_HEIGHT);
        
        // Verify that the correct methods were called on the GraphicsContext
        verify(graphicsContext).setFill(Color.DARKGRAY);
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).fillText(TEST_TEXT, 50, 150);
        
        // Verify order of operations
        inOrder(graphicsContext).verify(graphicsContext).setFill(Color.DARKGRAY);
        inOrder(graphicsContext).verify(graphicsContext).setFont(any(Font.class));
        inOrder(graphicsContext).verify(graphicsContext).fillText(TEST_TEXT, 50, 150);
    }
    
    @Test
    public void testIsValid() {
        assertTrue(subtitleItem.isValid());
        
        // Test with empty text
        SubtitleItem emptyItem = new SubtitleItem("", style);
        assertFalse(emptyItem.isValid());
        
        // Test with null text
        SubtitleItem nullTextItem = new SubtitleItem(null, style);
        assertFalse(nullTextItem.isValid());
        
        // Test with null style
        SubtitleItem nullStyleItem = new SubtitleItem(TEST_TEXT, null);
        assertFalse(nullStyleItem.isValid());
    }
    
    @Test
    public void testDrawWithDifferentDimensions() {
        double customWidth = 1024.0;
        double customHeight = 768.0;
        
        // Call draw method with different dimensions
        subtitleItem.draw(graphicsContext, customWidth, customHeight);
        
        // Verify that the drawing methods were called with the same fixed positions
        // regardless of dimensions (current implementation uses fixed positions)
        verify(graphicsContext).setFill(Color.DARKGRAY);
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).fillText(TEST_TEXT, 50, 150);
    }
} 