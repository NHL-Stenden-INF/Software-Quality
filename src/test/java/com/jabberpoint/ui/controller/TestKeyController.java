package com.jabberpoint.ui.controller;

import com.jabberpoint.patterns.command.Command;
import com.jabberpoint.patterns.command.GoToSlideCommand;
import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PreviousSlideCommand;
import com.jabberpoint.patterns.composite.PresentationInterface;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A test-specific implementation of KeyController that doesn't depend on SlideViewerFrame.
 * This allows us to test the key handling logic without dealing with JavaFX module system issues.
 */
public class TestKeyController {
    private final PresentationInterface presentation;
    private final Command nextSlideCommand;
    private final Command previousSlideCommand;

    public TestKeyController(PresentationInterface presentation) {
        this.presentation = presentation;
        this.nextSlideCommand = new NextSlideCommand(presentation);
        this.previousSlideCommand = new PreviousSlideCommand(presentation);
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