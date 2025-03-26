package com.jabberpoint.controller;

import com.jabberpoint.App;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.view.SlideViewerFrame;

public class MenuController {

    private Presentation presentation;
    private SlideViewerFrame viewerFrame;

    public MenuController(Presentation presentation, SlideViewerFrame viewerFrame) {
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
    }

    public void showAbout() {
        com.jabberpoint.AboutBox.display();
    }
}
