package com.jabberpoint.ui.view;

import com.jabberpoint.patterns.composite.*;
import com.jabberpoint.style.Style;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.infrastructure.GraphicsContextWrapper;
import javafx.scene.text.TextAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class SlideViewerComponentTest {
    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private Slide slide;
    
    @Mock
    private GraphicsContextWrapper graphicsContext;
    
    private SlideViewerComponent viewerComponent;
    private Style defaultStyle;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
        viewerComponent = new SlideViewerComponent(presentation, defaultStyle);
    }
    
    @Test
    void testDrawEmptySlide() {
        when(presentation.getCurrentSlide()).thenReturn(null);
        viewerComponent.draw();
        verify(graphicsContext, never()).clearRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }
    
    @Test
    void testDrawSlideWithTitle() {
        TitleItem titleItem = new TitleItem("Test Title", defaultStyle);
        when(presentation.getCurrentSlide()).thenReturn(slide);
        when(slide.getItems()).thenReturn(java.util.Collections.singletonList(titleItem));
        
        viewerComponent.draw();
        
        verify(graphicsContext).setFont(any());
        verify(graphicsContext).setFill(any());
        verify(graphicsContext).setTextAlign(TextAlignment.CENTER);
        verify(graphicsContext).fillText(eq("Test Title"), anyDouble(), anyDouble());
    }
    
    @Test
    void testDrawSlideWithSubtitle() {
        SubtitleItem subtitleItem = new SubtitleItem("Test Subtitle", defaultStyle);
        when(presentation.getCurrentSlide()).thenReturn(slide);
        when(slide.getItems()).thenReturn(java.util.Collections.singletonList(subtitleItem));
        
        viewerComponent.draw();
        
        verify(graphicsContext).setFont(any());
        verify(graphicsContext).setFill(any());
        verify(graphicsContext).setTextAlign(TextAlignment.LEFT);
        verify(graphicsContext).fillText(eq("Test Subtitle"), anyDouble(), anyDouble());
    }
    
    @Test
    void testDrawSlideWithBodyText() {
        BodyTextItem bodyItem = new BodyTextItem("Test Body", defaultStyle);
        when(presentation.getCurrentSlide()).thenReturn(slide);
        when(slide.getItems()).thenReturn(java.util.Collections.singletonList(bodyItem));
        
        viewerComponent.draw();
        
        verify(graphicsContext).setFont(any());
        verify(graphicsContext).setFill(any());
        verify(graphicsContext).setTextAlign(TextAlignment.LEFT);
        verify(graphicsContext).fillText(eq("Test Body"), anyDouble(), anyDouble());
    }
    
    @Test
    void testDrawSlideWithBulletPoint() {
        BulletPointItem bulletItem = new BulletPointItem("Test Bullet", defaultStyle);
        when(presentation.getCurrentSlide()).thenReturn(slide);
        when(slide.getItems()).thenReturn(java.util.Collections.singletonList(bulletItem));
        
        viewerComponent.draw();
        
        verify(graphicsContext).setFont(any());
        verify(graphicsContext).setFill(any());
        verify(graphicsContext).setTextAlign(TextAlignment.LEFT);
        verify(graphicsContext).fillText(eq("• Test Bullet"), anyDouble(), anyDouble());
    }
    
    @Test
    void testDrawSlideWithBackground() {
        when(presentation.getCurrentSlide()).thenReturn(slide);
        when(slide.getBackground()).thenReturn("test-background.jpg");
        when(slide.getItems()).thenReturn(java.util.Collections.emptyList());
        
        viewerComponent.draw();
        
        verify(graphicsContext).setFill(any());
        verify(graphicsContext).fillRect(eq(0.0), eq(0.0), anyDouble(), anyDouble());
    }
} 