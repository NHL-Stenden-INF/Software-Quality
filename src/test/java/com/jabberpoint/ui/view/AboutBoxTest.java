package com.jabberpoint.ui.view;

import com.jabberpoint.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AboutBoxTest extends BaseTest {
    
    @BeforeEach
    void setUp() {
        TestAboutBox.reset();
    }
    
    @Test
    void testShow() {
        TestAboutBox.show();

        assertTrue(TestAboutBox.wasShowCalled(), "show() should have been called");
        assertEquals("About JabberPoint", TestAboutBox.getTitle(), "Title should be set correctly");
        assertEquals("JabberPoint Presentation Viewer", TestAboutBox.getHeaderText(), "Header text should be set correctly");
        assertEquals("Version 1.0\n\n" +
                "A simple presentation viewer that supports text and images.\n" +
                "Created as part of a software quality course.", 
                TestAboutBox.getContentText(), 
                "Content text should be set correctly");
    }
}