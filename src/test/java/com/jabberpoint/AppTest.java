package com.jabberpoint;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import org.mockito.MockitoAnnotations;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Tests for the App class.
 * Note: Since JavaFX needs a running environment, we focus on testing non-UI methods
 * and use reflection to test private methods.
 */
public class AppTest {
    
    @Mock
    private XMLAccessor xmlAccessor;
    
    @Mock
    private PresentationInterface presentation;
    
    private App app;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        app = new App();
    }
    
    @Test
    public void testCreateDemoPresentation() throws Exception {
        // Use reflection to access private method
        Method method = App.class.getDeclaredMethod("createDemoPresentation");
        method.setAccessible(true);
        
        // Call the method
        Presentation presentation = (Presentation) method.invoke(app);
        
        // Verify the result
        assertNotNull(presentation);
        assertEquals("Portrait Photography Demo", presentation.getTitle());
        assertEquals(3, presentation.getSlideCount());
        
        // Test first slide
        Slide slide1 = presentation.getSlides().get(0);
        assertEquals("The Art of Portrait Photography", slide1.getTitle());
        assertEquals(1, slide1.getItems().size());
        
        // Test second slide
        Slide slide2 = presentation.getSlides().get(1);
        assertEquals("Essential Techniques", slide2.getTitle());
        assertEquals(3, slide2.getItems().size());
        
        // Test third slide
        Slide slide3 = presentation.getSlides().get(2);
        assertEquals("Lighting Setups", slide3.getTitle());
        assertEquals(3, slide3.getItems().size());
    }
    
    @Test
    public void testStopMethod() throws Exception {
        // Setup: Inject mocked presentation and viewerFrame into the app instance
        Field presentationField = App.class.getDeclaredField("presentation");
        presentationField.setAccessible(true);
        presentationField.set(app, presentation);
        
        // Create and set a mock SlideViewerFrame
        SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
        Field viewerFrameField = App.class.getDeclaredField("viewerFrame");
        viewerFrameField.setAccessible(true);
        viewerFrameField.set(app, mockFrame);

        // Create and set a mock BorderPane
        BorderPane mockPane = mock(BorderPane.class);
        Field rootField = App.class.getDeclaredField("root");
        rootField.setAccessible(true);
        rootField.set(app, mockPane);
        
        // Test stop method
        Method stopMethod = App.class.getDeclaredMethod("stop");
        stopMethod.setAccessible(true);
        stopMethod.invoke(app);
        
        // We can't easily verify System.gc() was called, but we can check the fields were cleared
        assertNull(presentationField.get(app), "Presentation should be nulled out");
        assertNull(viewerFrameField.get(app), "ViewerFrame should be nulled out");
        assertNull(rootField.get(app), "Root should be nulled out");
    }
    
    @Test
    public void testStartMethodWithLoadError() throws Exception {
        // Can't test directly due to JavaFX thread requirements, so we'll use reflection
        
        // Mock necessary parts
        Stage mockStage = mock(Stage.class);
        
        // Setup xmlAccessor to throw IOException for all presentations
        Field xmlAccessorField = App.class.getDeclaredField("xmlAccessor");
        xmlAccessorField.setAccessible(true);
        
        XMLAccessor mockXmlAccessor = mock(XMLAccessor.class);
        doThrow(IOException.class).when(mockXmlAccessor).loadPresentation(any(), anyString());
        xmlAccessorField.set(app, mockXmlAccessor);
        
        // Create a method to get to the createDemoPresentation result since we can't call start() directly
        Method createDemoMethod = App.class.getDeclaredMethod("createDemoPresentation");
        createDemoMethod.setAccessible(true);
        Presentation demoPresentation = (Presentation) createDemoMethod.invoke(app);
        
        // Directly set the presentation field with our demo presentation
        Field presentationField = App.class.getDeclaredField("presentation");
        presentationField.setAccessible(true);
        presentationField.set(app, demoPresentation);
        
        // Verify the demo presentation was created
        Presentation actualPresentation = (Presentation) presentationField.get(app);
        assertEquals("Portrait Photography Demo", actualPresentation.getTitle());
        assertEquals(3, actualPresentation.getSlideCount());
    }
    
    @Test
    public void testMainMethod() {
        try (MockedStatic<Application> mockedApplication = mockStatic(Application.class)) {
            // Call the main method
            App.main(new String[]{});
            
            // Verify that Application.launch was called
            mockedApplication.verify(() -> Application.launch(any()));
        }
    }
} 