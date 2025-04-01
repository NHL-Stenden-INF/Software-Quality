package com.jabberpoint.controller;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.presentation.command.NextSlideCommand;
import com.jabberpoint.presentation.command.PrevSlideCommand;
import com.jabberpoint.view.SlideViewerFrame;
import javafx.scene.input.KeyEvent;

public class KeyController {

    public KeyController(Presentation presentation, SlideViewerFrame viewerFrame) {
        // Ensure the viewerFrame is focusable and request focus
        viewerFrame.setFocusTraversable(true);
        viewerFrame.requestFocus();

        // Attach a key pressed handler directly to the viewerFrame
        viewerFrame.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case RIGHT:
                case UP:
                    new NextSlideCommand(presentation).execute();
                    viewerFrame.update();
                    break;
                case LEFT:
                case DOWN:
                    new PrevSlideCommand(presentation).execute();
                    viewerFrame.update();
                    break;
                default:
                    break;
            }
        });
    }
}
