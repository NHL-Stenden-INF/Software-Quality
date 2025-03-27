package com.jabberpoint;

import com.jabberpoint.controller.KeyController;
import com.jabberpoint.controller.MenuController;
import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.presentation.AboutBox;
import com.jabberpoint.presentation.command.NextSlideCommand;
import com.jabberpoint.presentation.command.PrevSlideCommand;
import com.jabberpoint.view.SlideViewerFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;

public class App extends Application {

    private Presentation presentation;
    private SlideViewerFrame viewerFrame;
    private XMLAccessor xmlAccessor = new XMLAccessor();

    @Override
    public void start(Stage primaryStage) {
        // Attempt to load the default presentation from an XML file.
        String defaultFilePath = "resources/default.xml";
        presentation = xmlAccessor.loadPresentation(defaultFilePath);
        if (presentation == null) {
            // If not found, create a demo presentation with multiple slides.
            presentation = createDemoPresentation();
        }
        presentation.setTitle("JabberPoint 2.0");

        // Main layout
        BorderPane root = new BorderPane();

        // Create the menu bar and set it at the top.
        MenuBar menuBar = createMenuBar(primaryStage);
        root.setTop(menuBar);

        // Create the viewer frame and set it in the center.
        viewerFrame = new SlideViewerFrame(presentation);
        root.setCenter(viewerFrame);

        // Set up additional controllers.
        new MenuController(presentation, viewerFrame);
        new KeyController(presentation, viewerFrame);

        // Set up scene and stage.
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle(presentation.getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Presentation createDemoPresentation() {
        Presentation demo = new Presentation();
        // Create multiple demo slides.
        demo.addSlide(new com.jabberpoint.model.Slide("Slide 1: Welcome")
            .addItem(new com.jabberpoint.model.TextItem("Hello, Lucas! This is the first demo slide!!")));
        demo.addSlide(new com.jabberpoint.model.Slide("Slide 2: Instructions")
            .addItem(new com.jabberpoint.model.TextItem("Use the View menu or arrow keys to navigate. But you came this far, so you probably already know XD.")));
        demo.addSlide(new com.jabberpoint.model.Slide("Slide 3: Farewell")
            .addItem(new com.jabberpoint.model.TextItem("This is the last slide ;-;")));
        return demo;
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        // File menu.
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Presentation File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                Presentation loadedPresentation = xmlAccessor.loadPresentation(selectedFile.getAbsolutePath());
                if (loadedPresentation != null) {
                    presentation = loadedPresentation;
                    viewerFrame.update();
                    stage.setTitle(presentation.getTitle());
                }
            }
        });

        MenuItem exportItem = new MenuItem("Export");
        exportItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Presentation File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File destinationFile = fileChooser.showSaveDialog(stage);
            if (destinationFile != null) {
                xmlAccessor.savePresentation(presentation, destinationFile.getAbsolutePath());
            }
        });
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(openItem, exportItem, exitItem);

        // View menu.
        Menu viewMenu = new Menu("View");
        MenuItem nextSlideItem = new MenuItem("Next Slide");
        nextSlideItem.setOnAction(e -> {
            new NextSlideCommand(presentation).execute();
            viewerFrame.update();
        });
        MenuItem prevSlideItem = new MenuItem("Previous Slide");
        prevSlideItem.setOnAction(e -> {
            new PrevSlideCommand(presentation).execute();
            viewerFrame.update();
        });
        MenuItem goToSlideItem = new MenuItem("Go To Slide");
        goToSlideItem.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Go To Slide");
            dialog.setHeaderText("Enter slide number (starting at 1):");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int slideNum = Integer.parseInt(result.get()) - 1;
                    if (slideNum >= 0 && slideNum < presentation.getSlides().size()) {
                        presentation.setCurrentSlideIndex(slideNum);
                        viewerFrame.update();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid slide number!");
                }
            }
        });
        viewMenu.getItems().addAll(nextSlideItem, prevSlideItem, goToSlideItem);

        // Help menu.
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> AboutBox.display());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
