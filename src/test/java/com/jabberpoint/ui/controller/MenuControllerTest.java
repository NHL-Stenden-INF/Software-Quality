package com.jabberpoint.ui.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.GoToSlideCommand;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;

/**
 * Test class for MenuController functionality
 * This test avoids any JavaFX initialization by directly testing 
 * the command pattern implementation rather than the actual MenuController class.
 * This approach allows the tests to run in CI/CD environments without 
 * requiring a JavaFX runtime.
 */
public class MenuControllerTest {

    /**
     * Tests the commands used by MenuController
     */
    @Test
    public void testNextSlideCommand() {
        // Create mock presentation
        PresentationInterface mockPresentation = mock(PresentationInterface.class);
        
        // Create and execute next slide command
        Command nextSlideCommand = new NextSlideCommand(mockPresentation);
        nextSlideCommand.execute();
        
        // Verify the next method was called
        verify(mockPresentation).nextSlide();
    }
    
    /**
     * Tests the previous slide command
     */
    @Test
    public void testPrevSlideCommand() {
        // Create mock presentation
        PresentationInterface mockPresentation = mock(PresentationInterface.class);
        
        // Create and execute prev slide command
        Command prevSlideCommand = new PrevSlideCommand(mockPresentation);
        prevSlideCommand.execute();
        
        // Verify the prev method was called
        verify(mockPresentation).previousSlide();
    }
    
    /**
     * Test the go to slide command
     */
    @Test
    public void testGoToSlideCommand() {
        // Create a mock presentation
        PresentationInterface mockPresentation = mock(PresentationInterface.class);
        
        // Create a command to go to slide 5 (index 4)
        Command goToCommand = new GoToSlideCommand(mockPresentation, 4);
        goToCommand.execute();
        
        // Verify the correct slide was selected
        verify(mockPresentation).setCurrentSlideIndex(4);
    }
} 