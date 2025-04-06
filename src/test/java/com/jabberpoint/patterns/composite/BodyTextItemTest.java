package com.jabberpoint.patterns.composite;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests for the BodyTextItem class.
 */
public class BodyTextItemTest {
    
    @Mock
    protected GraphicsContextWrapper graphicsContext;
    
    @Mock
    protected Style style;
    
    private BodyTextItem bodyTextItem;
    private static final String TEST_TEXT = "Test Body Text";
    private static final double TEST_WIDTH = 800.0;
    private static final double TEST_HEIGHT = 600.0;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        bodyTextItem = new BodyTextItem(TEST_TEXT, style);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(TEST_TEXT, bodyTextItem.getText());
        assertEquals(style, bodyTextItem.getStyle());
        assertEquals(1, bodyTextItem.getLevel());
    }
    
    @Test
    public void testDraw() {
        // Call draw method
        bodyTextItem.draw(graphicsContext, TEST_WIDTH, TEST_HEIGHT);
        
        // Verify that the correct methods were called on the GraphicsContext
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).setFill(Color.BLACK);
        verify(graphicsContext).fillText(TEST_TEXT, TEST_WIDTH / 2, TEST_HEIGHT / 2);
        
        // Verify order of operations
        inOrder(graphicsContext).verify(graphicsContext).setFont(any(Font.class));
        inOrder(graphicsContext).verify(graphicsContext).setFill(Color.BLACK);
        inOrder(graphicsContext).verify(graphicsContext).fillText(TEST_TEXT, TEST_WIDTH / 2, TEST_HEIGHT / 2);
    }
    
    @Test
    public void testIsValid() {
        assertTrue(bodyTextItem.isValid());
        
        // Test with empty text
        BodyTextItem emptyItem = new BodyTextItem("", style);
        assertFalse(emptyItem.isValid());
        
        // Test with null text
        BodyTextItem nullTextItem = new BodyTextItem(null, style);
        assertFalse(nullTextItem.isValid());
        
        // Test with null style
        BodyTextItem nullStyleItem = new BodyTextItem(TEST_TEXT, null);
        assertFalse(nullStyleItem.isValid());
    }
} 