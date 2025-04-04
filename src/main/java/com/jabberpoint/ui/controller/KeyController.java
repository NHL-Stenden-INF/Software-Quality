package com.jabberpoint.ui.controller;

import com.jabberpoint.patterns.command.NextSlideCommand;
import com.jabberpoint.patterns.command.PrevSlideCommand;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.ui.view.SlideViewerFrame;

import javafx.scene.input.KeyEvent;

public class KeyController {
    public KeyController(Presentation presentation, SlideViewerFrame viewerFrame) {
        viewerFrame.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case RIGHT:
                case DOWN:
                    new NextSlideCommand(presentation).execute();
                    viewerFrame.updateView();
                    break;
                case LEFT:
                case UP:
                    new PrevSlideCommand(presentation).execute();
                    viewerFrame.updateView();
                    break;
                default:
                    // Ignore all other key codes
                    break;
            }
        });
    }
}
