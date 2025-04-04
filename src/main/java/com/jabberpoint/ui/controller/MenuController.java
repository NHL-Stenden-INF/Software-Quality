package com.jabberpoint.ui.controller;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.command.GoToSlideCommand;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.ui.view.AboutBox;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;

public class MenuController {

    private final Stage stage;
    private final Presentation presentation;
    private final SlideViewerFrame viewerFrame;
    private final XMLAccessor xmlAccessor = new XMLAccessor();

    public MenuController(Stage stage, Presentation presentation, SlideViewerFrame viewerFrame) {
        this.stage = stage;
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
    }

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // File Menu
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
            createOpenMenuItem(),
            createSaveMenuItem(),
            new SeparatorMenuItem(),
            createExitMenuItem()
        );

        // View Menu
        Menu viewMenu = new Menu("View");
        viewMenu.getItems().addAll(
            createNextSlideMenuItem(),
            createPrevSlideMenuItem(),
            new SeparatorMenuItem(),
            createGotoSlideMenuItem()
        );

        // Help Menu
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(createAboutMenuItem());

        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        return menuBar;
    }

    private MenuItem createOpenMenuItem() {
        MenuItem item = new MenuItem("Open");
        item.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Presentation loaded = xmlAccessor.loadPresentation(file.getAbsolutePath());
                if (loaded != null) {
                    presentation.copyFrom(loaded);
                    viewerFrame.updateView();
                    stage.setTitle(presentation.getTitle());
                } else {
                    showAlert("Load Error", "Failed to load presentation from file");
                }
            }
        });
        return item;
    }

    private MenuItem createSaveMenuItem() {
        MenuItem item = new MenuItem("Save");
        item.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                xmlAccessor.savePresentation(presentation, file.getAbsolutePath());
            }
        });
        return item;
    }

    private MenuItem createExitMenuItem() {
        MenuItem item = new MenuItem("Exit");
        item.setOnAction(e -> System.exit(0));
        return item;
    }

    private MenuItem createNextSlideMenuItem() {
        MenuItem item = new MenuItem("Next Slide");
        item.setOnAction(e -> {
            new NextSlideCommand(presentation).execute();
            viewerFrame.updateView();
        });
        return item;
    }

    private MenuItem createPrevSlideMenuItem() {
        MenuItem item = new MenuItem("Previous Slide");
        item.setOnAction(e -> {
            new PrevSlideCommand(presentation).execute();
            viewerFrame.updateView();
        });
        return item;
    }

    private MenuItem createGotoSlideMenuItem() {
        MenuItem item = new MenuItem("Go To Slide");
        item.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Navigate to Slide");
            dialog.setHeaderText("Enter slide number (1-" + presentation.getSlideCount() + "):");
            
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(input -> {
                try {
                    int slideNumber = Integer.parseInt(input) - 1;
                    if (slideNumber >= 0 && slideNumber < presentation.getSlideCount()) {
                        new GoToSlideCommand(presentation, slideNumber).execute();
                        viewerFrame.updateView();
                    } else {
                        showAlert("Invalid Slide", "Slide number out of range");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter a valid number");
                }
            });
        });
        return item;
    }

    private MenuItem createAboutMenuItem() {
        MenuItem item = new MenuItem("About");
        item.setOnAction(e -> AboutBox.display());
        return item;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
