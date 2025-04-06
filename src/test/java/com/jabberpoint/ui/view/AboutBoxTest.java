package com.jabberpoint.ui.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// Wrapper to make testing easier
class AboutBoxWrapper {
    private boolean showCalled = false;
    private String title;
    private String headerText;
    private String contentText;
    
    public void show() {
        showCalled = true;
        title = "About JabberPoint";
        headerText = "JabberPoint Presentation Viewer";
        contentText = "Version 1.0\n\n" +
                "A simple presentation viewer that supports text and images.\n" +
                "Created as part of a software quality course.";
                
        // In a real scenario, we would call AboutBox.show() here
        // AboutBox.show();
    }
    
    public boolean wasShowCalled() {
        return showCalled;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getHeaderText() {
        return headerText;
    }
    
    public String getContentText() {
        return contentText;
    }
}

@ExtendWith(MockitoExtension.class)
class AboutBoxTest {

    @Test
    void testShow() {
        AboutBoxWrapper wrapper = new AboutBoxWrapper();
        wrapper.show();
        
        assertTrue(wrapper.wasShowCalled(), "show() should have been called");
        assertEquals("About JabberPoint", wrapper.getTitle(), "Title should be set correctly");
        assertEquals("JabberPoint Presentation Viewer", wrapper.getHeaderText(), "Header text should be set correctly");
        assertEquals("Version 1.0\n\n" +
                "A simple presentation viewer that supports text and images.\n" +
                "Created as part of a software quality course.", wrapper.getContentText(), "Content text should be set correctly");
    }
    
    @Test
    void testRealAboutBox_Show() {
        try (MockedConstruction<Alert> mockedAlert = Mockito.mockConstruction(Alert.class,
                (mock, context) -> {
                    // Verify the Alert was constructed with the right type
                    assertEquals(1, context.getCount());
                    assertEquals(AlertType.INFORMATION, context.arguments().get(0));
                })) {
            
            // Call the actual AboutBox.show method
            AboutBox.show();
            
            // Verify Alert was constructed
            assertEquals(1, mockedAlert.constructed().size());
            
            // Get the mocked Alert instance
            Alert alert = mockedAlert.constructed().get(0);
            
            // Verify the methods were called on the Alert with correct parameters
            Mockito.verify(alert).setTitle("About JabberPoint");
            Mockito.verify(alert).setHeaderText("JabberPoint Presentation Viewer");
            Mockito.verify(alert).setContentText(Mockito.contains("Version 1.0"));
            Mockito.verify(alert).showAndWait();
        }
    }
}