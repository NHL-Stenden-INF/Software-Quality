package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.patterns.composite.SlideItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class GoToSlideCommandTest {

    @Mock
    private PresentationInterface presentation;

    @Mock
    private Slide slide1;
    
    @Mock
    private Slide slide2;
    
    @Mock
    private Slide slide3;

    private GoToSlideCommand command;
    private static final int SLIDE_NUMBER = 2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new GoToSlideCommand(presentation, SLIDE_NUMBER);
        
        // Set up the presentation to return a list with enough slides
        when(presentation.getSlides()).thenReturn(Arrays.asList(slide1, slide2, slide3));
    }

    @Test
    void execute_ShouldSetCurrentSlideIndex() {
        command.execute();

        verify(presentation).setCurrentSlideIndex(SLIDE_NUMBER);
    }

    @Test
    void getItems_ShouldReturnDefensiveCopyOfSlideItems() {
        List<SlideItem> expectedItems = new ArrayList<>();
        when(slide3.getItems()).thenReturn(expectedItems);

        List<SlideItem> result = command.getItems();

        verify(presentation).getSlides();
        verify(slide3).getItems();
        assert result != expectedItems : "Should return a defensive copy";
        assert result.size() == expectedItems.size() : "Should have same number of items";
    }
} 