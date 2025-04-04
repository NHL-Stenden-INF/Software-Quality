package com.jabberpoint.patterns.command;

import java.util.ArrayList;
import java.util.List;

import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.patterns.composite.SlideItem;

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
