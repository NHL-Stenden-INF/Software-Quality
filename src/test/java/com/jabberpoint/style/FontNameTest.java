package com.jabberpoint.style;

import javafx.scene.text.Font;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FontName enum.
 */
public class FontNameTest {

    @Test
    public void testGetName() {
        assertEquals("Arial", FontName.ARIAL.getName());
        assertEquals("Calibri", FontName.CALIBRI.getName());
        assertEquals("Comic Sans MS", FontName.COMIC_SANS.getName());
        assertEquals("Courier New", FontName.COURIER.getName());
        assertEquals("Georgia", FontName.GEORGIA.getName());
        assertEquals("Impact", FontName.IMPACT.getName());
        assertEquals("Tahoma", FontName.TAHOMA.getName());
        assertEquals("Times New Roman", FontName.TIMES_NEW_ROMAN.getName());
        assertEquals("Verdana", FontName.VERDANA.getName());
    }
    
    @Test
    public void testGetFont() {
        // Test that getFont returns a valid Font object with the correct size
        Font font = FontName.ARIAL.getFont(20.0);
        assertNotNull(font);
        assertEquals(20.0, font.getSize());
        
        // Note: We can't reliably test the font family name as it depends on the system
        // and JavaFX's font handling. Instead, we'll just verify the font is created.
    }
    
    @Test
    public void testFontConsistency() {
        // Test that all font names return valid Font objects
        for (FontName fontName : FontName.values()) {
            Font font = fontName.getFont(20.0);
            assertNotNull(font);
            assertEquals(20.0, font.getSize());
            // We can't reliably test the font family name
        }
    }
} 