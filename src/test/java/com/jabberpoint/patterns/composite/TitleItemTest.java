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
 * Tests for the TitleItem class.
 */
public class TitleItemTest extends BaseTest {
    
    @Mock
    protected GraphicsContextWrapper graphicsContext;
    
    @Mock
    protected Style style;
    
    private TitleItem titleItem;
    private static final String TEST_TEXT = "Test Title";
    
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