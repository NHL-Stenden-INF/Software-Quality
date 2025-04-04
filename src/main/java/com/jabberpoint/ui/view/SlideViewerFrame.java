package com.jabberpoint.ui.view;

import com.jabberpoint.patterns.composite.Presentation;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class SlideViewerFrame extends BorderPane {
    private final SlideViewerComponent viewerComponent;
    private final Presentation presentation;

    public SlideViewerFrame(Presentation presentation) {
        this.presentation = presentation;
        this.viewerComponent = new SlideViewerComponent();
        setPadding(new Insets(0));
        this.setCenter(viewerComponent);
        updateView();
        setFocusTraversable(true);
        requestFocus();
    }

    public void updateView() {
        viewerComponent.setSlide(presentation.getCurrentSlide());
    }
    
}