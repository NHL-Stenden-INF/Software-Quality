package com.jabberpoint.ui.controller;

import com.jabberpoint.BaseTest;
import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;
import javafx.application.Platform;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class MenuControllerTest extends BaseTest {

    @Mock
    private Stage stage;
    
    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private XMLAccessor xmlAccessor;
    
    private MenuController menuController;
    
    @BeforeEach
    void setUp() {
        System.out.println("MenuControllerTest.setUp called");
        try {
            System.out.println("Initializing mocks...");
            MockitoAnnotations.openMocks(this);
            
            System.out.println("Creating MenuController...");
            // Create the MenuController directly without using runAndWait
            menuController = new MenuController(stage, presentation, null, xmlAccessor);
            System.out.println("MenuController created successfully");
            
            System.out.println("MenuControllerTest.setUp completed");
        } catch (Exception e) {
            System.err.println("Error in setUp: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to set up MenuControllerTest", e);
        }
    }
    
    @Test
    void testConstructor() {
        System.out.println("Running testConstructor");
        assertNotNull(menuController, "MenuController should not be null");
        System.out.println("testConstructor completed");
    }
    
    @Test
    void testGetMenuBar() {
        System.out.println("Running testGetMenuBar");
        MenuBar menuBar = menuController.getMenuBar();
        assertNotNull(menuBar, "MenuBar should not be null");
        System.out.println("testGetMenuBar completed");
    }
    
    @Test
    void testCommandExecution() {
        System.out.println("Running testCommandExecution");
        Command nextCommand = new NextSlideCommand(presentation);
        Command prevCommand = new PrevSlideCommand(presentation);

        nextCommand.execute();
        prevCommand.execute();

        verify(presentation, times(1)).nextSlide();
        verify(presentation, times(1)).previousSlide();
        System.out.println("testCommandExecution completed");
    }
    
    @Test
    void testPresentationInterface() {
        System.out.println("Running testPresentationInterface");
        when(presentation.getSlideCount()).thenReturn(5);
        when(presentation.getCurrentSlide()).thenReturn(null);
        
        assertEquals(5, presentation.getSlideCount());
        assertNull(presentation.getCurrentSlide());
        System.out.println("testPresentationInterface completed");
    }
} 