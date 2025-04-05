package com.jabberpoint.ui.controller;

import com.jabberpoint.BaseTest;
import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;
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
    
    private TestMenuController menuController;
    
    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
            menuController = new TestMenuController(stage, presentation, xmlAccessor);
        } catch (Exception e) {
            System.err.println("Error in setUp: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to set up MenuControllerTest", e);
        }
    }
    
    @Test
    void testConstructor() {
        assertNotNull(menuController, "TestMenuController should not be null");
    }
    
    @Test
    void testGetters() {
        assertNotNull(menuController.getStage(), "Stage should not be null");
        assertNotNull(menuController.getPresentation(), "Presentation should not be null");
        assertNotNull(menuController.getXmlAccessor(), "XMLAccessor should not be null");
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