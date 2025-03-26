package com.jabberpoint;

import com.jabberpoint.controller.KeyController;
import com.jabberpoint.controller.MenuController;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.TextItem;
import com.jabberpoint.view.SlideViewerFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class App extends Application {

    private Presentation presentation;
    private SlideViewerFrame viewerFrame;
    private XMLAccessor xmlAccessor = new XMLAccessor();

    @Override
    public void start(Stage primaryStage) {
        // Initialize presentation with a default slide
        presentation = new Presentation();
        presentation.setTitle("JabberPoint 2.0");
        loadDefaultSlide();

        // Main layout
        BorderPane root = new BorderPane();

        // Create menu bar
        MenuBar menuBar = createMenuBar(primaryStage);
        root.setTop(menuBar);

        // Create the viewer frame and add it to the center
        viewerFrame = new SlideViewerFrame(presentation);
        root.setCenter(viewerFrame);

        // Set up controllers
        new MenuController(presentation, viewerFrame);
        new KeyController(presentation, viewerFrame);

        // Set up scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle(presentation.getTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadDefaultSlide() {
        // Create a default slide to always have something to show
        Slide defaultSlide = new Slide("Default Slide");
        defaultSlide.addItem(new TextItem("Welcome to JabberPoint!"));
        presentation.addSlide(defaultSlide);
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");

        // Open option
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

        // Export option
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

        // Exit option
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(openItem, exportItem, exitItem);

        // Help menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> AboutBox.display());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
