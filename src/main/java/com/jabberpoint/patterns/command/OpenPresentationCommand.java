package com.jabberpoint.patterns.command;

import java.io.IOException;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.ViewInterface;

public class OpenPresentationCommand implements Command {
    private final PresentationInterface presentation;
    private final ViewInterface viewerFrame;
    private final XMLAccessor accessor;
    private final String fileName;

    public OpenPresentationCommand(PresentationInterface presentation, ViewInterface viewerFrame, XMLAccessor accessor, String fileName) {
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
        this.accessor = accessor;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        try {
            // Clear the current presentation
            presentation.getSlides().clear();
            presentation.setTitle("");
            
            // Load the new presentation
            accessor.loadPresentation(presentation, fileName);
            
            // Ensure we're starting at the first slide after loading is complete
            presentation.setCurrentSlideIndex(0);
            
            // Update the view to show the new presentation
            viewerFrame.update(presentation);
        }
        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }
} 