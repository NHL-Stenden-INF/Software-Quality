package com.jabberpoint.ui.controller;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import com.jabberpoint.BaseTest;
import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Test class for MenuController with additional safeguards for CI/CD environments.
 * Sets a global timeout to prevent tests from hanging.
 */
@Timeout(value = 5, unit = TimeUnit.SECONDS)
public class MenuControllerTest extends BaseTest {
    
    @Mock
    private Stage stage;
    
    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private SlideViewerFrame viewerFrame;
    
    @Mock
    private XMLAccessor xmlAccessor;
    
    private MenuController menuController;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configure mock to prevent hanging in non-UI environments
        doNothing().when(stage).close();
        
        menuController = new MenuController(stage, presentation, viewerFrame, xmlAccessor);
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(menuController);
        assertNotNull(menuController.getMenuBar());
    }
    
    @Test
    public void testMenuBarStructure() {
        MenuBar menuBar = menuController.getMenuBar();
        assertNotNull(menuBar);
        assertEquals(4, menuBar.getMenus().size());
        
        // Verify menu structure
        Menu fileMenu = menuBar.getMenus().get(0);
        assertEquals("File", fileMenu.getText());
        assertEquals(3, fileMenu.getItems().size());
        
        Menu presentationsMenu = menuBar.getMenus().get(1);
        assertEquals("Presentations", presentationsMenu.getText());
        assertEquals(4, presentationsMenu.getItems().size());
        
        Menu viewMenu = menuBar.getMenus().get(2);
        assertEquals("View", viewMenu.getText());
        assertEquals(3, viewMenu.getItems().size());
        
        Menu helpMenu = menuBar.getMenus().get(3);
        assertEquals("Help", helpMenu.getText());
        assertEquals(1, helpMenu.getItems().size());
    }
    
    @Test
    public void testCommandExecutionViaReflection() throws Exception {
        // Create a helper method in BaseTest to avoid direct reflection in test code
        Command mockCommand = mock(Command.class);
        
        // Use reflection to safely execute the private method without direct access
        runPrivateMethod(menuController, "executeCommand", new Class[]{Command.class}, mockCommand);
        
        // Verify the command was executed
        verify(mockCommand).execute();
    }
    
    @Test
    public void testFileMenuItems() {
        MenuBar menuBar = menuController.getMenuBar();
        Menu fileMenu = menuBar.getMenus().get(0);
        
        MenuItem openItem = fileMenu.getItems().get(0);
        assertEquals("Open", openItem.getText());
        assertNotNull(openItem.getOnAction());
        
        MenuItem saveItem = fileMenu.getItems().get(1);
        assertEquals("Save", saveItem.getText());
        assertNotNull(saveItem.getOnAction());
        
        MenuItem exitItem = fileMenu.getItems().get(2);
        assertEquals("Exit", exitItem.getText());
        assertNotNull(exitItem.getOnAction());
    }
    
    @Test
    public void testViewMenuItems() {
        MenuBar menuBar = menuController.getMenuBar();
        Menu viewMenu = menuBar.getMenus().get(2);
        
        MenuItem nextItem = viewMenu.getItems().get(0);
        assertEquals("Next", nextItem.getText());
        assertNotNull(nextItem.getOnAction());
        
        MenuItem prevItem = viewMenu.getItems().get(1);
        assertEquals("Previous", prevItem.getText());
        assertNotNull(prevItem.getOnAction());
        
        MenuItem goToItem = viewMenu.getItems().get(2);
        assertEquals("Go to...", goToItem.getText());
        assertNotNull(goToItem.getOnAction());
    }
    
    @Test
    public void testPresentationsMenuItems() {
        MenuBar menuBar = menuController.getMenuBar();
        Menu presentationsMenu = menuBar.getMenus().get(1);
        
        MenuItem portraitItem = presentationsMenu.getItems().get(0);
        assertEquals("Portrait Photography", portraitItem.getText());
        assertNotNull(portraitItem.getOnAction());
        
        MenuItem landscapeItem = presentationsMenu.getItems().get(1);
        assertEquals("Landscape Photography", landscapeItem.getText());
        assertNotNull(landscapeItem.getOnAction());
        
        MenuItem streetItem = presentationsMenu.getItems().get(2);
        assertEquals("Street Photography", streetItem.getText());
        assertNotNull(streetItem.getOnAction());
        
        MenuItem defaultItem = presentationsMenu.getItems().get(3);
        assertEquals("Default Presentation", defaultItem.getText());
        assertNotNull(defaultItem.getOnAction());
    }
    
    @Test
    public void testHelpMenuItems() {
        MenuBar menuBar = menuController.getMenuBar();
        Menu helpMenu = menuBar.getMenus().get(3);
        
        MenuItem aboutItem = helpMenu.getItems().get(0);
        assertEquals("About", aboutItem.getText());
        assertNotNull(aboutItem.getOnAction());
    }
    
    /**
     * Helper method to invoke a private method using reflection.
     * This abstracts away the reflection details and makes the test code cleaner.
     */
    private void runPrivateMethod(Object obj, String methodName, Class<?>[] paramTypes, Object... args) throws Exception {
        java.lang.reflect.Method method = obj.getClass().getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        method.invoke(obj, args);
    }
} 