package com.jabberpoint.style;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FontColor enum.
 */
public class FontColorTest {

    @Test
    public void testGetColor() {
        assertEquals(Color.BLACK, FontColor.BLACK.getColor());
        assertEquals(Color.WHITE, FontColor.WHITE.getColor());
        assertEquals(Color.RED, FontColor.RED.getColor());
        assertEquals(Color.GREEN, FontColor.GREEN.getColor());
        assertEquals(Color.BLUE, FontColor.BLUE.getColor());
        assertEquals(Color.YELLOW, FontColor.YELLOW.getColor());
        assertEquals(Color.CYAN, FontColor.CYAN.getColor());
        assertEquals(Color.MAGENTA, FontColor.MAGENTA.getColor());
    }
    
    @Test
    public void testFromStringValid() {
        assertEquals(FontColor.BLACK, FontColor.fromString("BLACK"));
        assertEquals(FontColor.WHITE, FontColor.fromString("WHITE"));
        assertEquals(FontColor.RED, FontColor.fromString("RED"));
        assertEquals(FontColor.GREEN, FontColor.fromString("GREEN"));
        assertEquals(FontColor.BLUE, FontColor.fromString("BLUE"));
        assertEquals(FontColor.YELLOW, FontColor.fromString("YELLOW"));
        assertEquals(FontColor.CYAN, FontColor.fromString("CYAN"));
        assertEquals(FontColor.MAGENTA, FontColor.fromString("MAGENTA"));
        
        // Test case insensitivity
        assertEquals(FontColor.BLACK, FontColor.fromString("black"));
        assertEquals(FontColor.WHITE, FontColor.fromString("White"));
    }
    
    @Test
    public void testFromStringInvalid() {
        // Invalid color should default to BLACK
        assertEquals(FontColor.BLACK, FontColor.fromString("INVALID_COLOR"));
        assertEquals(FontColor.BLACK, FontColor.fromString(""));
        assertEquals(FontColor.BLACK, FontColor.fromString(null));
    }
} 