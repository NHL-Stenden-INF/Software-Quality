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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;

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
    
    @BeforeAll
    static void initializeJavaFX() throws InterruptedException {
        // Initialize JavaFX
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await();
    }
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuController = new MenuController(stage, presentation, null, xmlAccessor);
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