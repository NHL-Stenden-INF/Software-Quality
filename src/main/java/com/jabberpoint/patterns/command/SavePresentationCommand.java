package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;

import java.io.IOException;

public class SavePresentationCommand implements Command {
    private final PresentationInterface presentation;
    private final XMLAccessor xmlAccessor;
    private final String filename;

    public SavePresentationCommand(PresentationInterface presentation, XMLAccessor xmlAccessor, String filename) {
        this.presentation = presentation;
        this.xmlAccessor = xmlAccessor;
        this.filename = filename;
    }

    @Override
    public void execute() {
        try {
            xmlAccessor.savePresentation(presentation, filename);
        } catch (IOException e) {
            System.err.println("Error saving presentation: " + e.getMessage());
        }
    }
} 