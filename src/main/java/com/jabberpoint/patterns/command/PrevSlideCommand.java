package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.Presentation;

public class PrevSlideCommand implements Command {

    private Presentation presentation;

    public PrevSlideCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        presentation.previousSlide();
    }
}
