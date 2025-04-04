package com.jabberpoint.ui.view;

import com.jabberpoint.BaseTest;
import com.jabberpoint.patterns.composite.*;
import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SlideViewerComponentTest extends BaseTest {
    @Mock
    private PresentationInterface presentation;
    
    @Mock
    protected GraphicsContextWrapper graphicsContext;
    @Mock
    protected Canvas canvas;
    @Mock
    protected Slide slide;
    @Mock
    protected Style style;
    @Mock
    protected SlideViewerComponent slideViewerComponent;
    
    private SlideViewerComponent viewerComponent;
    private Style defaultStyle;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        viewerComponent = new SlideViewerComponent(presentation, defaultStyle);
    }
    
    @Test
    void testConstructor() {
        assertNotNull(viewerComponent);
    }
} 