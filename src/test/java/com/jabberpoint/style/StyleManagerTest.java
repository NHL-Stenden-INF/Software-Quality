package com.jabberpoint.style;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the StyleManager class.
 */
class StyleManagerTest {

    @BeforeEach
    void setUp() {
        // Reset the current style to default before each test
        StyleManager.setCurrentStyle(new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK));
    }

    @Test
    void testLoadStyleFromXML() {
        StyleManager.loadStyleFromXML("VERDANA", "20", "BLUE");
        
        Style currentStyle = StyleManager.getCurrentStyle();
        assertEquals(FontName.VERDANA, currentStyle.getTitleFontName());
        assertEquals(FontName.VERDANA, currentStyle.getBodyFontName());
        assertEquals(FontColor.BLUE, currentStyle.getTitleColor());
        assertEquals(FontColor.BLUE, currentStyle.getBodyColor());
    }

    @Test
    void testLoadStyleFromXMLWithInvalidFontName() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        StyleManager.loadStyleFromXML("INVALID_FONT", "20", "RED");

        assertTrue(errContent.toString().contains("Invalid style properties in XML"));

        Style currentStyle = StyleManager.getCurrentStyle();
        assertEquals(FontName.ARIAL, currentStyle.getTitleFontName());
        assertEquals(FontName.ARIAL, currentStyle.getBodyFontName());
    }

    @Test
    void testLoadStyleFromXMLWithInvalidFontColor() {
        StyleManager.loadStyleFromXML("CALIBRI", "20", "INVALID_COLOR");
        
        Style currentStyle = StyleManager.getCurrentStyle();
        assertEquals(FontName.CALIBRI, currentStyle.getTitleFontName());
        assertEquals(FontName.CALIBRI, currentStyle.getBodyFontName());
        assertEquals(FontColor.BLACK, currentStyle.getTitleColor());
        assertEquals(FontColor.BLACK, currentStyle.getBodyColor());
    }


}