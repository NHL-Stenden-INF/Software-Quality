package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class OpenPresentationCommand implements Command {
    private final Stage stage;
    private final Presentation presentation;
    private final SlideViewerFrame viewerFrame;
    private final XMLAccessor xmlAccessor;

    public OpenPresentationCommand(Stage stage, Presentation presentation, SlideViewerFrame viewerFrame) {
        this.stage = stage;
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
        this.xmlAccessor = new XMLAccessor();
    }

    @Override
    public void execute() {
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
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 