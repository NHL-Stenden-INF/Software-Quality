package com.jabberpoint.patterns.command;

import java.util.ArrayList;
import java.util.List;

import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.SlideItem;

public class GoToSlideCommand implements Command {

    private PresentationInterface presentation;
    private int slideNumber;

    public GoToSlideCommand(PresentationInterface presentation, int slideNumber) {
        this.presentation = presentation;
        this.slideNumber = slideNumber;
    }

    @Override
    public void execute() {
        presentation.setCurrentSlideIndex(slideNumber);
    }

    public List<SlideItem> getItems() {
        return new ArrayList<>(presentation.getSlides().get(slideNumber).getItems());  // Defensive copy
    }
}
