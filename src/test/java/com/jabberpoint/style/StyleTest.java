package com.jabberpoint.style;

import javafx.scene.text.Font;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Style class.
 */
public class StyleTest {

    @Test
    public void testDefaultConstructor() {
        Style style = new Style();
        
        assertEquals(FontName.ARIAL, style.getTitleFontName());
        assertEquals(FontName.ARIAL, style.getBodyFontName());
        assertEquals(FontColor.BLACK, style.getTitleColor());
        assertEquals(FontColor.BLACK, style.getBodyColor());
        
        // Test compatibility methods
        assertEquals(FontName.ARIAL, style.getFontName());
        assertEquals(20.0, style.getFontSize());
        assertEquals(FontColor.BLACK, style.getFontColor());
    }
    
    @Test
    public void testParameterizedConstructor() {
        Style style = new Style(FontName.CALIBRI, FontName.COMIC_SANS, FontColor.RED, FontColor.BLUE);
        
        assertEquals(FontName.CALIBRI, style.getTitleFontName());
        assertEquals(FontName.COMIC_SANS, style.getBodyFontName());
        assertEquals(FontColor.RED, style.getTitleColor());
        assertEquals(FontColor.BLUE, style.getBodyColor());
        
        // Test compatibility methods
        assertEquals(FontName.COMIC_SANS, style.getFontName());
        assertEquals(20.0, style.getFontSize());
        assertEquals(FontColor.BLUE, style.getFontColor());
    }
    
    @Test
    public void testSetters() {
        Style style = new Style();
        
        style.setTitleFontName(FontName.GEORGIA);
        style.setBodyFontName(FontName.IMPACT);
        style.setTitleColor(FontColor.GREEN);
        style.setBodyColor(FontColor.YELLOW);
        
        assertEquals(FontName.GEORGIA, style.getTitleFontName());
        assertEquals(FontName.IMPACT, style.getBodyFontName());
        assertEquals(FontColor.GREEN, style.getTitleColor());
        assertEquals(FontColor.YELLOW, style.getBodyColor());
    }
    
    @Test
    public void testGetTitleFont() {
        Style style = new Style(FontName.CALIBRI, FontName.COMIC_SANS, FontColor.RED, FontColor.BLUE);
        Font font = style.getTitleFont();
        
        assertNotNull(font);
        assertEquals(40.0, font.getSize());
        // We can't reliably test the font family name as it depends on the system
    }
    
    @Test
    public void testGetBodyFont() {
        Style style = new Style(FontName.CALIBRI, FontName.COMIC_SANS, FontColor.RED, FontColor.BLUE);
        Font font = style.getBodyFont();
        
        assertNotNull(font);
        assertEquals(20.0, font.getSize());
        // We can't reliably test the font family name as it depends on the system
    }
} 