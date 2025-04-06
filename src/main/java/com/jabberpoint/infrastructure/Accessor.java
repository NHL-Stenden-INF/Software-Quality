package com.jabberpoint.infrastructure;

import com.jabberpoint.patterns.composite.PresentationInterface;

import java.io.IOException;

public abstract class Accessor {
    public static final String DEMO_NAME = "Demo presentation";
    public static final String DEFAULT_EXTENSION = ".xml";

    public void loadPresentation(PresentationInterface presentation, String filename) throws IOException {
        presentation.setTitle(DEMO_NAME);
    }

    public void savePresentation(PresentationInterface presentation, String filename) throws IOException {
        // Empty implementation
    }
}
