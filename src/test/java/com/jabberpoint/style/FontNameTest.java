package com.jabberpoint.style;

import javafx.scene.text.Font;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FontName enum.
 */
public class FontNameTest {

    private static final double TITLE_FONT_SIZE = 48.0;
    private static final double BODY_FONT_SIZE = 24.0;

    @Test
    public void testGetName() {
        assertEquals("Arial", FontName.ARIAL.getName());
        assertEquals("Times New Roman", FontName.TIMES_NEW_ROMAN.getName());
        assertEquals("Courier New", FontName.COURIER.getName());
        assertEquals("Verdana", FontName.VERDANA.getName());
        assertEquals("Georgia", FontName.GEORGIA.getName());
        assertEquals("Comic Sans MS", FontName.COMIC_SANS.getName());
        assertEquals("Calibri", FontName.CALIBRI.getName());
        assertEquals("Impact", FontName.IMPACT.getName());
        assertEquals("Tahoma", FontName.TAHOMA.getName());
    }
    
    @Test
    public void testGetFont() {
        // Test title font size
        Font titleFont = FontName.ARIAL.getFont(TITLE_FONT_SIZE);
        assertEquals("Arial", titleFont.getFamily());
        assertEquals(TITLE_FONT_SIZE, titleFont.getSize());
        
        // Test body font size
        Font bodyFont = FontName.ARIAL.getFont(BODY_FONT_SIZE);
        assertEquals("Arial", bodyFont.getFamily());
        assertEquals(BODY_FONT_SIZE, bodyFont.getSize());
        
        // Test custom font size
        double customSize = 36.0;
        Font customFont = FontName.ARIAL.getFont(customSize);
        assertEquals("Arial", customFont.getFamily());
        assertEquals(customSize, customFont.getSize());
    }
    
    @Test
    public void testFontConsistency() {
        // Test that all font names return valid Font objects
        for (FontName fontName : FontName.values()) {
            Font font = fontName.getFont(TITLE_FONT_SIZE);
            assertNotNull(font);
            assertEquals(fontName.getName(), font.getFamily());
            assertEquals(TITLE_FONT_SIZE, font.getSize());
        }
    }
} 