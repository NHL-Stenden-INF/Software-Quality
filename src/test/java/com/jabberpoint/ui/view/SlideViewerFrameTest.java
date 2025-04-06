package com.jabberpoint.ui.view;

import com.jabberpoint.BaseTest;
import com.jabberpoint.patterns.composite.PresentationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SlideViewerFrameTest extends BaseTest {
    
    @Mock
    private PresentationInterface presentation;
    
    private SlideViewerFrame viewerFrame;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewerFrame = new SlideViewerFrame(presentation);
    }
    
    @Test
    void testConstructor() {
        assertNotNull(viewerFrame, "ViewerFrame should be created");
        verify(presentation).addObserver(viewerFrame);
    }
    
    @Test
    void testUpdate() {
        viewerFrame.update(presentation);
        
        // No assertions needed as update() is void and the actual drawing
        // is handled by SlideViewerComponent which has its own tests
    }
    
    @Test
    void testUpdateView() {
        viewerFrame.updateView();
        
        // No assertions needed as updateView() is void and the actual drawing
        // is handled by SlideViewerComponent which has its own tests
    }
} 