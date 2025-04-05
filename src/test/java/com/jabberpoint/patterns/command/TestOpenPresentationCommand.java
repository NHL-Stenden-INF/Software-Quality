package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;

import java.io.IOException;

public class TestOpenPresentationCommand implements Command {
    private final PresentationInterface presentation;
    private final XMLAccessor xmlAccessor;
    private final String filename;
    private static boolean loadCalled = false;

    public TestOpenPresentationCommand(PresentationInterface presentation, XMLAccessor xmlAccessor, String filename) {
        this.presentation = presentation;
        this.xmlAccessor = xmlAccessor;
        this.filename = filename;
    }

    @Override
    public void execute() {
        try {
            presentation.getSlides();
            presentation.setTitle("");
            xmlAccessor.loadPresentation(presentation, filename);
            loadCalled = true;
        } catch (IOException e) {
            // In the test version, we'll just ignore the exception
        }
    }

    public static boolean wasLoadCalled() {
        return loadCalled;
    }

    public static void reset() {
        loadCalled = false;
    }
} 