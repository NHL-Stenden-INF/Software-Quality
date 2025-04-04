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
 * Tests for the BulletPointItem class.
 */
public class BulletPointItemTest {
    
    @Mock
    private GraphicsContextWrapper graphicsContext;
    
    private BulletPointItem bulletPointItem;
    private Style style;
    private static final String TEST_TEXT = "Test Bullet Point";
    private static final double TEST_X = 100.0;
    private static final double TEST_Y = 200.0;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        style = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        bulletPointItem = new BulletPointItem(TEST_TEXT, style);
    }
    
    @Test
    public void testConstructor() {
        assertEquals(TEST_TEXT, bulletPointItem.getText());
        assertEquals(style, bulletPointItem.getStyle());
        assertEquals(1, bulletPointItem.getLevel());
    }
    
    @Test
    public void testDraw() {
        // Call draw method
        bulletPointItem.draw(graphicsContext, TEST_X, TEST_Y);
        
        // Verify that the correct methods were called on the GraphicsContext
        verify(graphicsContext, times(2)).setFill(Color.BLACK); // Once for bullet, once for text
        verify(graphicsContext).fillOval(TEST_X - 10, TEST_Y - 5, 5, 5); // Bullet point
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).fillText(TEST_TEXT, TEST_X, TEST_Y);
        
        // Verify order of operations
        inOrder(graphicsContext).verify(graphicsContext).setFill(Color.BLACK); // For bullet
        inOrder(graphicsContext).verify(graphicsContext).fillOval(TEST_X - 10, TEST_Y - 5, 5, 5);
        inOrder(graphicsContext).verify(graphicsContext).setFont(any(Font.class));
        inOrder(graphicsContext).verify(graphicsContext).fillText(TEST_TEXT, TEST_X, TEST_Y);
    }
    
    @Test
    public void testDrawWithDifferentStyle() {
        // Create a bullet point item with different style
        Style redStyle = new Style(FontName.TIMES_NEW_ROMAN, FontName.ARIAL, FontColor.RED, FontColor.BLACK);
        BulletPointItem redBulletPointItem = new BulletPointItem(TEST_TEXT, redStyle);
        
        // Call draw method
        redBulletPointItem.draw(graphicsContext, TEST_X, TEST_Y);
        
        // Verify that the correct methods were called with the right color
        verify(graphicsContext, times(2)).setFill(Color.RED); // Once for bullet, once for text
        verify(graphicsContext).fillOval(TEST_X - 10, TEST_Y - 5, 5, 5);
        verify(graphicsContext).setFont(any(Font.class));
        verify(graphicsContext).fillText(TEST_TEXT, TEST_X, TEST_Y);
    }
    
    @Test
    public void testIsValid() {
        assertTrue(bulletPointItem.isValid());
        
        // Test with empty text
        BulletPointItem emptyItem = new BulletPointItem("", style);
        assertFalse(emptyItem.isValid());
        
        // Test with null text
        BulletPointItem nullTextItem = new BulletPointItem(null, style);
        assertFalse(nullTextItem.isValid());
        
        // Test with null style
        BulletPointItem nullStyleItem = new BulletPointItem(TEST_TEXT, null);
        assertFalse(nullStyleItem.isValid());
    }
} 