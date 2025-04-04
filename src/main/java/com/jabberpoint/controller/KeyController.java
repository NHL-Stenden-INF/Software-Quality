package com.jabberpoint.controller;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.presentation.command.NextSlideCommand;
import com.jabberpoint.presentation.command.PrevSlideCommand;
import com.jabberpoint.view.SlideViewerFrame;
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
            }
        });
    }
}
