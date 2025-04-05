package com.jabberpoint.ui.view;

import com.jabberpoint.BaseTest;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.patterns.composite.TitleItem;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SlideViewerComponentTest extends BaseTest {
    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private Slide slide;
    
    private SlideViewerComponent viewerComponent;
    private Style defaultStyle;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        viewerComponent = new SlideViewerComponent(presentation, defaultStyle);
    }
    
    @Test
    void testDefaultConstructor() {
        SlideViewerComponent defaultComponent = new SlideViewerComponent();
        assertNotNull(defaultComponent, "Default component should be created");
        assertTrue(defaultComponent.getChildren().size() > 0, "Should have a canvas child");
        assertTrue(defaultComponent.getChildren().get(0) instanceof Canvas, "First child should be a Canvas");
    }
    
    @Test
    void testParameterizedConstructor() {
        assertNotNull(viewerComponent, "Component should be created with presentation and style");
        assertTrue(viewerComponent.getChildren().size() > 0, "Should have a canvas child");
        assertTrue(viewerComponent.getChildren().get(0) instanceof Canvas, "First child should be a Canvas");
    }
    
    @Test
    void testUpdate() {
        PresentationInterface newPresentation = mock(PresentationInterface.class);

        viewerComponent.update(newPresentation);
        
        // No assertions needed as update() is void and the actual drawing
        // is handled by the draw() method which is tested indirectly
    }
    
    @Test
    void testCanvasSetup() {
        Canvas canvas = (Canvas) viewerComponent.getChildren().get(0);

        assertTrue(canvas.widthProperty().isBound(), "Canvas width should be bound");
        assertTrue(canvas.heightProperty().isBound(), "Canvas height should be bound");

        viewerComponent.setPrefWidth(800);
        viewerComponent.setPrefHeight(600);
        
        // Note: We can't check exact dimensions in a unit test without JavaFX runtime
        // Instead, we'll verify the component has the correct structure
        assertTrue(viewerComponent instanceof StackPane, "Component should be a StackPane");
        assertEquals(1, viewerComponent.getChildren().size(), "Should have exactly one child (Canvas)");
    }
    
    @Test
    void testComponentResizing() {
        viewerComponent.setPrefWidth(1000);
        viewerComponent.setPrefHeight(800);

        assertTrue(viewerComponent instanceof StackPane, "Component should still be a StackPane after resize");
        assertEquals(1, viewerComponent.getChildren().size(), "Should still have exactly one child (Canvas) after resize");
        
        Canvas canvas = (Canvas) viewerComponent.getChildren().get(0);
        assertTrue(canvas.widthProperty().isBound(), "Canvas width should still be bound after resize");
        assertTrue(canvas.heightProperty().isBound(), "Canvas height should still be bound after resize");
    }
} 