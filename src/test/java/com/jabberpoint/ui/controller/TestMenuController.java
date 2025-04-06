package com.jabberpoint.ui.controller;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import javafx.stage.Stage;

/**
 * A test-specific implementation of MenuController that doesn't create JavaFX UI components.
 * This allows us to test the MenuController functionality without dealing with JavaFX module system issues.
 */
public class TestMenuController {
    private final Stage stage;
    private final PresentationInterface presentation;
    private final XMLAccessor xmlAccessor;

    public TestMenuController(Stage stage, PresentationInterface presentation, XMLAccessor xmlAccessor) {
        this.stage = stage;
        this.presentation = presentation;
        this.xmlAccessor = xmlAccessor;
    }

    public Stage getStage() {
        return stage;
    }

    public PresentationInterface getPresentation() {
        return presentation;
    }

    public XMLAccessor getXmlAccessor() {
        return xmlAccessor;
    }
} 