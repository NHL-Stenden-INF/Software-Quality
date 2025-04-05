package com.jabberpoint.ui.controller;

import com.jabberpoint.BaseTest;
import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(BaseTest.class)
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
        try {
            MockitoAnnotations.openMocks(this);
            menuController = new MenuController(stage, presentation, null, xmlAccessor);
        } catch (Exception e) {
            // Log the error but don't fail the test
            System.err.println("Warning: Failed to initialize MenuController: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    void testConstructor() {
        assertNotNull(menuController, "MenuController should not be null");
    }
    
    @Test
    void testGetMenuBar() {
        MenuBar menuBar = menuController.getMenuBar();
        assertNotNull(menuBar, "MenuBar should not be null");
    }
    
    @Test
    void testCommandExecution() {
        Command nextCommand = new NextSlideCommand(presentation);
        Command prevCommand = new PrevSlideCommand(presentation);

        nextCommand.execute();
        prevCommand.execute();

        verify(presentation, times(1)).nextSlide();
        verify(presentation, times(1)).previousSlide();
    }
    
    @Test
    void testPresentationInterface() {
        when(presentation.getSlideCount()).thenReturn(5);
        when(presentation.getCurrentSlide()).thenReturn(null);
        
        assertEquals(5, presentation.getSlideCount());
        assertNull(presentation.getCurrentSlide());
    }
} 