package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.PresentationInterface;

public class PreviousSlideCommand implements Command {

    private PresentationInterface presentation;

    public PreviousSlideCommand(PresentationInterface presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.previousSlide();
    }
} 