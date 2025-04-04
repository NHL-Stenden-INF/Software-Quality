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
 * Tests for the SubtitleItem class.
 */
public class SubtitleItemTest extends BaseTest {
    
    @Mock
    protected GraphicsContextWrapper graphicsContext;
    
    @Mock
    protected Style style;
    
    private SubtitleItem subtitleItem;
    private static final String TEST_TEXT = "Test Subtitle";
    
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
} 