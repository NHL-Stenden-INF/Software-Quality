package com.jabberpoint.presentation.command;

import com.jabberpoint.command.Command;
import com.jabberpoint.model.Presentation;

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
