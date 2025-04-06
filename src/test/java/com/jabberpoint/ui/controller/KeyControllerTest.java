package com.jabberpoint.ui.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@ExtendWith(MockitoExtension.class)
class KeyControllerTest {

    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private SlideViewerFrame viewerFrame;

    private KeyController keyController;

    @BeforeEach
    void setup() {
        // Capture the event handler when it's set on the viewerFrame
        keyController = new KeyController(presentation, viewerFrame);
    }
    
    /**
     * Helper method to create a mock KeyEvent
     */
    private KeyEvent createKeyEvent(KeyCode keyCode) {
        KeyEvent event = mock(KeyEvent.class);
        when(event.getCode()).thenReturn(keyCode);
        return event;
    }

    @Test
    void testNextSlideKeyCodes() {
        // Get the event handler that was set on the viewerFrame
        ArgumentCaptor<EventHandler<KeyEvent>> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
        verify(viewerFrame).setOnKeyPressed(handlerCaptor.capture());
        EventHandler<KeyEvent> keyHandler = handlerCaptor.getValue();
        
        // Test each key that should trigger next slide
        KeyCode[] nextKeys = {KeyCode.PAGE_DOWN, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.SPACE, KeyCode.N};
        
        for (KeyCode keyCode : nextKeys) {
            // Reset presentation before each test
            reset(presentation);
            
            // Create a mock KeyEvent with the key code
            KeyEvent event = createKeyEvent(keyCode);
            
            // Call the handler with the event
            keyHandler.handle(event);
            
            // Verify presentation.nextSlide was called
            verify(presentation).nextSlide();
        }
    }

    @Test
    void testPreviousSlideKeyCodes() {
        // Get the event handler that was set on the viewerFrame
        ArgumentCaptor<EventHandler<KeyEvent>> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
        verify(viewerFrame).setOnKeyPressed(handlerCaptor.capture());
        EventHandler<KeyEvent> keyHandler = handlerCaptor.getValue();
        
        // Test keys that should trigger previous slide
        KeyCode[] prevKeys = {KeyCode.PAGE_UP, KeyCode.LEFT, KeyCode.UP, KeyCode.P};
        
        for (KeyCode keyCode : prevKeys) {
            // Reset presentation before each test
            reset(presentation);
            
            // Create a mock KeyEvent with the key code
            KeyEvent event = createKeyEvent(keyCode);
            
            // Call the handler with the event
            keyHandler.handle(event);
            
            // Verify presentation.previousSlide was called
            verify(presentation).previousSlide();
        }
    }
}
