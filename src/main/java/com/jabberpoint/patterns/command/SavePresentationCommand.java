package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.Presentation;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SavePresentationCommand implements Command {
    private final Stage stage;
    private final Presentation presentation;
    private final XMLAccessor xmlAccessor;

    public SavePresentationCommand(Stage stage, Presentation presentation) {
        this.stage = stage;
        this.presentation = presentation;
        this.xmlAccessor = new XMLAccessor();
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            xmlAccessor.savePresentation(presentation, file.getAbsolutePath());
        }
    }
} 