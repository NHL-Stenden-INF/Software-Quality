package com.jabberpoint;

import com.jabberpoint.controller.KeyController;
import com.jabberpoint.controller.MenuController;
import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.BodyTextItem;
import com.jabberpoint.view.SlideViewerFrame;
import com.jabberpoint.entities.Style;
import com.jabberpoint.entities.FontName;
import com.jabberpoint.entities.FontSize;
import com.jabberpoint.entities.FontColor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private Presentation presentation;
    private SlideViewerFrame viewerFrame;
    private BorderPane root;
    private XMLAccessor xmlAccessor = new XMLAccessor();

    @Override
    public void start(Stage primaryStage) {
        // Load or create presentation
        String defaultFilePath = "resources/default.xml";
        presentation = xmlAccessor.loadPresentation(defaultFilePath);
        if (presentation == null) {
            presentation = createDemoPresentation();
        }
        presentation.setTitle("JabberPoint 2.0");

        // Initialize main components
        root = new BorderPane();
        viewerFrame = new SlideViewerFrame(presentation);
        BorderPane.setAlignment(viewerFrame, javafx.geometry.Pos.CENTER);
        root.setCenter(viewerFrame);

        // Initialize controllers
        MenuController menuController = new MenuController(primaryStage, presentation, viewerFrame);
        root.setTop(menuController.createMenuBar());
        new KeyController(presentation, viewerFrame);

        // Setup stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle(presentation.getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Presentation createDemoPresentation() {
        Presentation demo = new Presentation();

        // Create a default Style to use for BodyTextItems
        Style defaultStyle = new Style(FontName.ARIAL, FontSize.MEDIUM, FontColor.BLACK);

        demo.addSlide(new Slide("Slide 1: Welcome")
            .addItem(new BodyTextItem("Hello, Lucas! This is the first demo slide!!", defaultStyle)));
        demo.addSlide(new Slide("Slide 2: Instructions")
            .addItem(new BodyTextItem("Use the View menu or arrow keys to navigate.", defaultStyle)));
        demo.addSlide(new Slide("Slide 3: Farewell")
            .addItem(new BodyTextItem("This is the last slide ;-;", defaultStyle)));
        return demo;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
