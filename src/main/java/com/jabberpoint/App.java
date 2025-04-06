package com.jabberpoint;

import java.io.IOException;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.Style;
import com.jabberpoint.ui.controller.KeyController;
import com.jabberpoint.ui.controller.MenuController;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private final XMLAccessor xmlAccessor = new XMLAccessor();
    private PresentationInterface presentation;
    private SlideViewerFrame viewerFrame;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        // Add favicon to the application window
        Image favicon = new Image(getClass().getResourceAsStream("/resources/images/favicon.png"));
        primaryStage.getIcons().add(favicon);
        
        presentation = new Presentation();
        try {
            // Try to load default presentation first (primary)
            xmlAccessor.loadPresentation(presentation, "resources/default.xml");
        } catch (IOException e) {
            try {
                // Try portrait if default fails
                xmlAccessor.loadPresentation(presentation, "resources/portrait.xml");
            } catch (IOException e2) {
                try {
                    // Try landscape if portrait fails
                    xmlAccessor.loadPresentation(presentation, "resources/landscape.xml");
                } catch (IOException e3) {
                    try {
                        // Try street if landscape fails
                        xmlAccessor.loadPresentation(presentation, "resources/street.xml");
                    } catch (IOException e4) {
                        System.err.println("Error loading any presentation: " + e4.getMessage());
                        // If loading fails, create a demo presentation
                        presentation = createDemoPresentation();
                    }
                }
            }
        }

        viewerFrame = new SlideViewerFrame(presentation);
        BorderPane.setAlignment(viewerFrame, Pos.CENTER);
        
        MenuBar menuBar = new MenuController(primaryStage, presentation, viewerFrame, xmlAccessor).getMenuBar();
        
        // Initialize the KeyController - we intentionally don't store the reference
        // as it registers itself as a listener and doesn't need to be referenced later
        KeyController keyController = new KeyController(presentation, viewerFrame);

        root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(viewerFrame);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JabberPoint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Clean up any resources when the application is closing
        if (presentation != null) {
            // Clear any references that might prevent garbage collection
            presentation = null;
        }
        if (viewerFrame != null) {
            viewerFrame = null;
        }
        root = null;
        // Explicitly suggest garbage collection
        System.gc();
    }

    private Presentation createDemoPresentation() {
        Presentation demo = new Presentation();
        demo.setTitle("Portrait Photography Demo");

        // Create a default Style to use for BodyTextItems
        Style defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);

        Slide slide1 = new Slide("The Art of Portrait Photography");
        slide1.addItem(new BodyTextItem("Capturing Beauty in People", defaultStyle));
        demo.addSlide(slide1);
        
        Slide slide2 = new Slide("Essential Techniques");
        slide2.addItem(new BodyTextItem("Lighting is key: Natural vs. Studio", defaultStyle));
        slide2.addItem(new BodyTextItem("Perfect focal length: 85-135mm", defaultStyle));
        slide2.addItem(new BodyTextItem("Depth of field: Creating separation", defaultStyle));
        demo.addSlide(slide2);
        
        Slide slide3 = new Slide("Lighting Setups");
        slide3.addItem(new BodyTextItem("Loop lighting: The most versatile", defaultStyle));
        slide3.addItem(new BodyTextItem("Rembrandt: Classic with triangle of light", defaultStyle));
        slide3.addItem(new BodyTextItem("Butterfly: Glamour lighting", defaultStyle));
        demo.addSlide(slide3);
        
        return demo;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
