package com.jabberpoint.ui.controller;

import java.io.File;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.GoToSlideCommand;
import com.jabberpoint.patterns.command.LoadDefaultCommand;
import com.jabberpoint.patterns.command.LoadLandscapeCommand;
import com.jabberpoint.patterns.command.LoadPortraitCommand;
import com.jabberpoint.patterns.command.LoadStreetCommand;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.OpenPresentationCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.command.SavePresentationCommand;
import com.jabberpoint.patterns.command.ShowAboutCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.ViewInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {
    private final Stage stage;
    private final PresentationInterface presentation;
    private final ViewInterface viewerFrame;
    private final XMLAccessor xmlAccessor;
    private final MenuBar menuBar;

    public MenuController(Stage stage, PresentationInterface presentation, ViewInterface viewerFrame, XMLAccessor xmlAccessor) {
        this.stage = stage;
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
        this.xmlAccessor = xmlAccessor;
        this.menuBar = createMenuBar();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> handleOpen());
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> handleSave());
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> handleExit());
        fileMenu.getItems().addAll(openItem, saveItem, exitItem);

        // Presentations menu
        Menu presentationsMenu = new Menu("Presentations");
        MenuItem portraitItem = new MenuItem("Portrait Photography");
        portraitItem.setOnAction(e -> executeCommand(new LoadPortraitCommand(presentation, viewerFrame, xmlAccessor)));
        MenuItem landscapeItem = new MenuItem("Landscape Photography");
        landscapeItem.setOnAction(e -> executeCommand(new LoadLandscapeCommand(presentation, viewerFrame, xmlAccessor)));
        MenuItem streetItem = new MenuItem("Street Photography");
        streetItem.setOnAction(e -> executeCommand(new LoadStreetCommand(presentation, viewerFrame, xmlAccessor)));
        MenuItem defaultItem = new MenuItem("Default Presentation");
        defaultItem.setOnAction(e -> executeCommand(new LoadDefaultCommand(presentation, viewerFrame, xmlAccessor)));
        presentationsMenu.getItems().addAll(portraitItem, landscapeItem, streetItem, defaultItem);

        // View menu
        Menu viewMenu = new Menu("View");
        MenuItem nextItem = new MenuItem("Next");
        nextItem.setOnAction(e -> executeCommand(new NextSlideCommand(presentation)));
        MenuItem prevItem = new MenuItem("Previous");
        prevItem.setOnAction(e -> executeCommand(new PrevSlideCommand(presentation)));
        MenuItem goToItem = new MenuItem("Go to...");
        goToItem.setOnAction(e -> handleGoTo());
        viewMenu.getItems().addAll(nextItem, prevItem, goToItem);

        // Help menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> executeCommand(new ShowAboutCommand()));
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, presentationsMenu, viewMenu, helpMenu);
        return menuBar;
    }

    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Presentation");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Command command = new OpenPresentationCommand(presentation, viewerFrame, xmlAccessor, file.getAbsolutePath());
            executeCommand(command);
        }
    }

    private void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Presentation");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );
        
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Command command = new SavePresentationCommand(presentation, xmlAccessor, file.getAbsolutePath());
            executeCommand(command);
        }
    }

    private void handleExit() {
        stage.close();
    }

    private void handleGoTo() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Go to Slide");
        dialog.setHeaderText("Enter slide number");
        dialog.setContentText("Slide number:");

        dialog.showAndWait().ifPresent(result -> {
            try {
                int slideNumber = Integer.parseInt(result);
                executeCommand(new GoToSlideCommand(presentation, slideNumber - 1));
            } catch (NumberFormatException e) {
                showError("Invalid Input", "Please enter a valid slide number.");
            }
        });
    }

    private void executeCommand(Command command) {
        command.execute();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
