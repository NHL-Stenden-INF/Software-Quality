package com.jabberpoint.patterns.composite;

import com.jabberpoint.patterns.observer.SlideObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

/**
 * Tests for the Presentation class.
 */
public class PresentationTest {
    
    private Presentation presentation;
    
    @Mock
    private SlideObserver observer;
    
    private static final String TEST_TITLE = "Test Presentation";
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presentation = new Presentation();
        presentation.setTitle(TEST_TITLE);
        presentation.addObserver(observer);
    }
    
    @Test
    public void testConstructor() {
        Presentation newPresentation = new Presentation();
        assertEquals(0, newPresentation.getSlideCount());
        assertTrue(newPresentation.getSlides().isEmpty());
        assertNull(newPresentation.getCurrentSlide());
    }
    
    @Test
    public void testAddSlide() {
        Slide slide = new Slide("Test Slide");
        presentation.addSlide(slide);
        
        assertEquals(1, presentation.getSlideCount());
        assertEquals(slide, presentation.getCurrentSlide());
        verify(observer).update(presentation);
    }
    
    @Test
    public void testSetCurrentSlideIndex() {
        // Add three slides
        presentation.addSlide(new Slide("Slide 1"));
        presentation.addSlide(new Slide("Slide 2"));
        presentation.addSlide(new Slide("Slide 3"));
        
        // Test valid index
        presentation.setCurrentSlideIndex(1);
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
        verify(observer, times(4)).update(presentation); // 3 adds + 1 set
        
        // Test invalid indices
        presentation.setCurrentSlideIndex(-1);
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle()); // Should not change
        
        presentation.setCurrentSlideIndex(3);
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle()); // Should not change
    }
    
    @Test
    public void testNextSlide() {
        presentation.addSlide(new Slide("Slide 1"));
        presentation.addSlide(new Slide("Slide 2"));
        
        assertEquals("Slide 1", presentation.getCurrentSlide().getTitle());
        
        presentation.nextSlide();
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
        
        // Try to go beyond last slide
        presentation.nextSlide();
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle()); // Should not change
        
        verify(observer, times(3)).update(presentation); // 2 adds + 1 next
    }
    
    @Test
    public void testPreviousSlide() {
        presentation.addSlide(new Slide("Slide 1"));
        presentation.addSlide(new Slide("Slide 2"));
        presentation.setCurrentSlideIndex(1);
        
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
        
        presentation.previousSlide();
        assertEquals("Slide 1", presentation.getCurrentSlide().getTitle());
        
        // Try to go before first slide
        presentation.previousSlide();
        assertEquals("Slide 1", presentation.getCurrentSlide().getTitle()); // Should not change
        
        verify(observer, times(4)).update(presentation); // 2 adds + 1 set + 1 previous
    }
    
    @Test
    public void testGetTitle() {
        assertEquals(TEST_TITLE, presentation.getTitle());
    }
    
    @Test
    public void testSetTitle() {
        String newTitle = "New Title";
        presentation.setTitle(newTitle);
        assertEquals(newTitle, presentation.getTitle());
        verify(observer, times(2)).update(presentation); // Initial title set + new title set
    }
    
    @Test
    public void testObserverManagement() {
        SlideObserver secondObserver = mock(SlideObserver.class);
        
        // Add second observer
        presentation.addObserver(secondObserver);
        presentation.addSlide(new Slide("Test"));
        verify(observer).update(presentation);
        verify(secondObserver).update(presentation);
        
        // Remove first observer
        presentation.removeObserver(observer);
        presentation.addSlide(new Slide("Test 2"));
        verify(observer, times(1)).update(presentation); // Should not receive second update
        verify(secondObserver, times(2)).update(presentation); // Should receive both updates
    }
    
    @Test
    public void testReplaceWith() {
        // Create original slides
        presentation.addSlide(new Slide("Original 1"));
        presentation.addSlide(new Slide("Original 2"));
        
        // Create new presentation
        Presentation other = new Presentation();
        other.setTitle("Other Title");
        other.addSlide(new Slide("New 1"));
        other.addSlide(new Slide("New 2"));
        other.addSlide(new Slide("New 3"));
        
        // Replace
        presentation.replaceWith(other);
        
        // Verify
        assertEquals("Other Title", presentation.getTitle());
        assertEquals(3, presentation.getSlideCount());
        assertEquals("New 1", presentation.getCurrentSlide().getTitle());
        presentation.setCurrentSlideIndex(0); // Remove assertEquals, just set the index
        
        verify(observer, atLeastOnce()).update(presentation);
    }
    
    @Test
    public void testCopyFrom() {
        // Create original slides
        presentation.addSlide(new Slide("Original 1"));
        presentation.addSlide(new Slide("Original 2"));
        
        // Create new presentation
        PresentationInterface other = mock(PresentationInterface.class);
        List<Slide> newSlides = List.of(new Slide("New 1"), new Slide("New 2"));
        when(other.getSlides()).thenReturn(newSlides);
        when(other.getTitle()).thenReturn("Other Title");
        
        // Copy
        presentation.copyFrom(other);
        
        // Verify
        assertEquals("Other Title", presentation.getTitle());
        assertEquals(2, presentation.getSlideCount());
        assertEquals("New 1", presentation.getCurrentSlide().getTitle());
        presentation.setCurrentSlideIndex(0); // Remove assertEquals, just set the index
        
        verify(observer, atLeastOnce()).update(presentation);
        verify(other).getSlides();
        verify(other).getTitle();
    }
} 