package com.jabberpoint.ui.view;

import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.observer.SlideObserver;
import com.jabberpoint.style.Style;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class SlideViewerFrame extends BorderPane implements SlideObserver {
    private final SlideViewerComponent viewerComponent;
    private final PresentationInterface presentation;

    public SlideViewerFrame(PresentationInterface presentation) {
        this.presentation = presentation;
        this.viewerComponent = new SlideViewerComponent(presentation, new Style());
        setPadding(new Insets(0));
        this.setCenter(viewerComponent);
        updateView();
        setFocusTraversable(true);
        requestFocus();
        
        // Register as an observer
        presentation.addObserver(this);
    }

    public void updateView() {
        viewerComponent.update(presentation);
    }
    
    @Override
    public void update(PresentationInterface presentation) {
        updateView();
    }
}