package com.jabberpoint.view;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.observer.SlideObserver;

import javafx.scene.layout.BorderPane;

public class SlideViewerFrame extends BorderPane implements SlideObserver {

    private Presentation presentation;
    private SlideViewerComponent slideViewerComponent;

    public SlideViewerFrame(Presentation presentation) {
        this.presentation = presentation;
        slideViewerComponent = new SlideViewerComponent(presentation);
        setCenter(slideViewerComponent);
    }

    @Override
    public void update() {
        slideViewerComponent.updateView();
    }
}
