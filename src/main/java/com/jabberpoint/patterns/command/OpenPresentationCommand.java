package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.SlideViewerFrame;

import java.io.IOException;

public class OpenPresentationCommand implements Command {
    private final PresentationInterface presentation;
    private final SlideViewerFrame viewerFrame;
    private final XMLAccessor xmlAccessor;
    private final String filename;

    public OpenPresentationCommand(PresentationInterface presentation, SlideViewerFrame viewerFrame, XMLAccessor xmlAccessor, String filename) {
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
        this.xmlAccessor = xmlAccessor;
        this.filename = filename;
    }

    @Override
    public void execute() {
        try {
            xmlAccessor.loadPresentation(presentation, filename);
            viewerFrame.update(presentation);
        } catch (IOException e) {
            System.err.println("Error loading presentation: " + e.getMessage());
        }
    }
} 