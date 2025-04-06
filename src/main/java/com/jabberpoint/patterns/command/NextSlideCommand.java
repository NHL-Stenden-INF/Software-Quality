package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.PresentationInterface;

public class NextSlideCommand implements Command {
    private final PresentationInterface presentation;

    public NextSlideCommand(PresentationInterface presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.nextSlide();
    }
}
