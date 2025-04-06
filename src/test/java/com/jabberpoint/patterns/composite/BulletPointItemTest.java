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
 * Tests for the BulletPointItem class.
 */
public class BulletPointItemTest extends BaseTest {
    
    @Mock
    protected GraphicsContextWrapper graphicsContext;
    
    @Mock
    protected Style style;
    
    private BulletPointItem bulletPointItem;
    private static final String TEST_TEXT = "Test Bullet Point";
    
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