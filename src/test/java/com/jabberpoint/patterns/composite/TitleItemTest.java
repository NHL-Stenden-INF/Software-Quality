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
 * Tests for the TitleItem class.
 */
public class TitleItemTest {
    
    @Mock
    private GraphicsContext graphicsContext;
    
    private TitleItem titleItem;
    private Style style;
    private static final String TEST_TEXT = "Test Title";
    private static final double TEST_WIDTH = 800.0;
    private static final double TEST_HEIGHT = 600.0;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        titleItem = new TitleItem(TEST_TEXT, style);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(TEST_TEXT, titleItem.getText());
        assertEquals(style, titleItem.getStyle());
        assertEquals(1, titleItem.getLevel());
    }
    
    @Test
    public void testDraw() {
        // Call draw method
        titleItem.draw(graphicsContext, TEST_WIDTH, TEST_HEIGHT);
        
        // Verify that the correct methods were called on the GraphicsContext
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).setFill(Color.BLACK);
        verify(graphicsContext).fillText(TEST_TEXT, TEST_WIDTH / 2, TEST_HEIGHT / 4);
        
        // Verify order of operations
        inOrder(graphicsContext).verify(graphicsContext).setFont(any(Font.class));
        inOrder(graphicsContext).verify(graphicsContext).setFill(Color.BLACK);
        inOrder(graphicsContext).verify(graphicsContext).fillText(TEST_TEXT, TEST_WIDTH / 2, TEST_HEIGHT / 4);
    }
    
    @Test
    public void testDrawWithDifferentStyle() {
        // Create a title item with different style
        Style redStyle = new Style(FontName.TIMES_NEW_ROMAN, FontName.ARIAL, FontColor.RED, FontColor.BLACK);
        TitleItem redTitleItem = new TitleItem(TEST_TEXT, redStyle);
        
        // Call draw method
        redTitleItem.draw(graphicsContext, TEST_WIDTH, TEST_HEIGHT);
        
        // Verify that the correct methods were called with the right color
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).setFill(Color.RED);
        verify(graphicsContext).fillText(TEST_TEXT, TEST_WIDTH / 2, TEST_HEIGHT / 4);
    }
    
    @Test
    public void testIsValid() {
        assertTrue(titleItem.isValid());
        
        // Test with empty text
        TitleItem emptyItem = new TitleItem("", style);
        assertFalse(emptyItem.isValid());
        
        // Test with null text
        TitleItem nullTextItem = new TitleItem(null, style);
        assertFalse(nullTextItem.isValid());
        
        // Test with null style
        TitleItem nullStyleItem = new TitleItem(TEST_TEXT, null);
        assertFalse(nullStyleItem.isValid());
    }
} 