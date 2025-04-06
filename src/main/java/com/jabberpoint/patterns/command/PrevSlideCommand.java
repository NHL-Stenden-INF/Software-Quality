package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.PresentationInterface;

public class PrevSlideCommand implements Command {

    private PresentationInterface presentation;

    public PrevSlideCommand(PresentationInterface presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.previousSlide();
    }
}
