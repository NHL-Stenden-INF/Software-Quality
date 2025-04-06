package com.jabberpoint.ui.controller;

import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.GoToSlideCommand;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PreviousSlideCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyController {
    private final PresentationInterface presentation;
    private final Command nextSlideCommand;
    private final Command previousSlideCommand;

    public KeyController(PresentationInterface presentation, SlideViewerFrame viewerFrame) {
        this.presentation = presentation;
        this.nextSlideCommand = new NextSlideCommand(presentation);
        this.previousSlideCommand = new PreviousSlideCommand(presentation);
        viewerFrame.setOnKeyPressed(this::handleKeyPressed);
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.PAGE_DOWN || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.N) {
            nextSlideCommand.execute();
        } else if (event.getCode() == KeyCode.PAGE_UP || event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.P) {
            previousSlideCommand.execute();
        } else if (event.getCode().isDigitKey()) {
            int slideNumber = event.getCode().getCode() - KeyCode.DIGIT0.getCode();
            new GoToSlideCommand(presentation, slideNumber).execute();
        }
    }
}
