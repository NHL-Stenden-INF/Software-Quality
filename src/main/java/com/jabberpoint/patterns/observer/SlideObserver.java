package com.jabberpoint.patterns.observer;

import com.jabberpoint.patterns.composite.PresentationInterface;

public interface SlideObserver {
    /**
     * Called when the presentation state changes
     * @param presentation The presentation that changed
     */
    void update(PresentationInterface presentation);
}
