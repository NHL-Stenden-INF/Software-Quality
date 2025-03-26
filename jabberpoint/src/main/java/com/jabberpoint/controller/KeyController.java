package com.jabberpoint.controller;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.view.SlideViewerFrame;
import com.jabberpoint.NextSlideCommand;
import com.jabberpoint.PrevSlideCommand;
import javafx.scene.Scene;

public class KeyController {

    public KeyController(Presentation presentation, SlideViewerFrame viewerFrame) {
        // Attach key event handler to the scene to control slide navigation
        Scene scene = viewerFrame.getScene();
        if (scene != null) {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case RIGHT:
                    case DOWN:
                        new NextSlideCommand(presentation).execute();
                        viewerFrame.update();
                        break;
                    case LEFT:
                    case UP:
                        new PrevSlideCommand(presentation).execute();
                        viewerFrame.update();
                        break;
                    default:
                        break;
                }
            });
        }
    }
}
