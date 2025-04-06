package com.jabberpoint.ui.view;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.jabberpoint.BaseTest;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.patterns.composite.BulletPointItem;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.patterns.composite.SlideItem;
import com.jabberpoint.patterns.composite.SubtitleItem;
import com.jabberpoint.patterns.composite.TitleItem;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.Style;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

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
        
        // Setup slide with mock items
        when(presentation.getCurrentSlide()).thenReturn(slide);
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
        Slide newSlide = mock(Slide.class);
        when(newPresentation.getCurrentSlide()).thenReturn(newSlide);

        viewerComponent.update(newPresentation);
        
        // Verify getCurrentSlide is called during draw - use atLeastOnce() since draw() calls it multiple times
        verify(newPresentation, atLeastOnce()).getCurrentSlide();
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
    
    @Test
    void testDrawWithNullPresentation() {
        // Set presentation to null
        SlideViewerComponent component = new SlideViewerComponent(null, defaultStyle);
        
        // This should not throw exceptions
        assertDoesNotThrow(() -> component.draw(), "Drawing with null presentation should not throw exception");
    }
    
    @Test
    void testDrawWithNullSlide() {
        // Setup with null current slide
        when(presentation.getCurrentSlide()).thenReturn(null);
        
        // This should not throw exceptions
        assertDoesNotThrow(() -> viewerComponent.draw(), "Drawing with null slide should not throw exception");
    }
    
    @Test
    void testDrawWithBackground() {
        // Create a mock GraphicsContextWrapper and inject it into the component
        GraphicsContextWrapper gcWrapper = mock(GraphicsContextWrapper.class);
        
        // Setup slide with background
        when(slide.getBackground()).thenReturn("test-background.jpg");
        
        // We can't inject the mock directly, but we can verify indirectly
        // by checking that getCurrentSlide is called during draw()
        viewerComponent.draw();
        
        verify(presentation, atLeastOnce()).getCurrentSlide();
        verify(slide).getBackground();
    }
    
    @Test
    void testDrawWithSlideItems() {
        // Create mock slide items
        List<SlideItem> items = new ArrayList<>();
        
        // Add a title item
        TitleItem titleItem = mock(TitleItem.class);
        when(titleItem.getText()).thenReturn("Test Title");
        items.add(titleItem);
        
        // Add a subtitle item
        SubtitleItem subtitleItem = mock(SubtitleItem.class);
        when(subtitleItem.getText()).thenReturn("Test Subtitle");
        items.add(subtitleItem);
        
        // Add a body text item
        BodyTextItem bodyTextItem = mock(BodyTextItem.class);
        when(bodyTextItem.getText()).thenReturn("Test Body Text");
        items.add(bodyTextItem);
        
        // Add a bullet point item
        BulletPointItem bulletPointItem = mock(BulletPointItem.class);
        when(bulletPointItem.getText()).thenReturn("Test Bullet Point");
        items.add(bulletPointItem);
        
        // Setup the mocked slide
        when(slide.getItems()).thenReturn(items);
        
        // Setup getItemsByType to return appropriate items when called
        when(slide.getItemsByType(TitleItem.class)).thenReturn(List.of(titleItem));
        when(slide.getItemsByType(SubtitleItem.class)).thenReturn(List.of(subtitleItem));
        when(slide.getItemsByType(BodyTextItem.class)).thenReturn(List.of(bodyTextItem));
        when(slide.getItemsByType(BulletPointItem.class)).thenReturn(List.of(bulletPointItem));
        
        // Call draw which should process these items
        viewerComponent.draw();
        
        // Verify slide methods were called - use atLeastOnce() since draw() calls getCurrentSlide multiple times
        verify(presentation, atLeastOnce()).getCurrentSlide();
        verify(slide, atLeastOnce()).getItems();
        
        // Note: The actual drawing isn't directly verifiable in a unit test without more setup
    }
} 