package com.jabberpoint.presentation.command;

import com.jabberpoint.command.Command;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.SlideItem;
import java.util.ArrayList;
import java.util.List;

public class GoToSlideCommand implements Command {

    private Presentation presentation;
    private int slideNumber;

    public GoToSlideCommand(Presentation presentation, int slideNumber) {
        this.presentation = presentation;
        this.slideNumber = slideNumber;
    }

    @Override
    public void execute() {
        if (slideNumber >= 0 && slideNumber < presentation.getSlides().size()) {
            presentation.setCurrentSlideIndex(slideNumber);
        }
    }

    public List<SlideItem> getItems() {
        return new ArrayList<>(presentation.getSlides().get(slideNumber).getItems());  // Defensive copy
    }
}
