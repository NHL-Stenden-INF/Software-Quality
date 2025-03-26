package com.jabberpoint;

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
            // Directly set the slide index if you have a setter, or simulate via next/previous commands
            // For now, we just print out the command.
            System.out.println("Going to slide " + slideNumber);
        }
    }
}
