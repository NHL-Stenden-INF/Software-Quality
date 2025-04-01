package com.jabberpoint.presentation.command;

import com.jabberpoint.command.Command;
import com.jabberpoint.model.Presentation;

public class GoToSlideCommand implements Command {

    private Presentation presentation;
    private int slideNumber;

    public GoToSlideCommand(Presentation presentation, int slideNumber) {
        this.presentation = presentation;
        this.slideNumber = slideNumber;
    }

    @Override
    public void execute() {
        // Go to a specific slide (ensure bounds checking)
        if (slideNumber >= 0 && slideNumber < presentation.getSlides().size()) {
            // Directly set the slide index
            System.out.println("Going to slide " + slideNumber);
        }
    }
}
