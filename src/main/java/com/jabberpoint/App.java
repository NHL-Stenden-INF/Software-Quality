package com.jabberpoint;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.BodyTextItem;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class App extends Application {

    private final XMLAccessor xmlAccessor = new XMLAccessor();
    private PresentationInterface presentation;
    private SlideViewerFrame viewerFrame;
    private BorderPane root;

    @Override
    public void start(Stage stage) {
        presentation = new Presentation();
        try {
            xmlAccessor.loadPresentation(presentation, "resources/default.xml");
        } catch (IOException e) {
            System.err.println("Error loading default presentation: " + e.getMessage());
            // If loading fails, create a demo presentation
            presentation = createDemoPresentation();
        }

        viewerFrame = new SlideViewerFrame(presentation);
        BorderPane.setAlignment(viewerFrame, Pos.CENTER);
        
        MenuBar menuBar = new MenuController(stage, presentation, viewerFrame, xmlAccessor).getMenuBar();
        // Initialize the KeyController but don't store the reference since it's not used elsewhere
        new KeyController(presentation, viewerFrame);

        root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(viewerFrame);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("JabberPoint");
        stage.setScene(scene);
        stage.show();
    }

    private Presentation createDemoPresentation() {
        Presentation demo = new Presentation();

        // Create a default Style to use for BodyTextItems
        Style defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);

        Slide slide1 = new Slide("Slide 1: Welcome");
        slide1.addItem(new BodyTextItem("Hello, Lucas! This is the first demo slide!!", defaultStyle));
        demo.addSlide(slide1);
        
        Slide slide2 = new Slide("Slide 2: Instructions");
        slide2.addItem(new BodyTextItem("Use the View menu or arrow keys to navigate.", defaultStyle));
        demo.addSlide(slide2);
        
        Slide slide3 = new Slide("Slide 3: Farewell");
        slide3.addItem(new BodyTextItem("This is the last slide ;-;", defaultStyle));
        demo.addSlide(slide3);
        
        return demo;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
