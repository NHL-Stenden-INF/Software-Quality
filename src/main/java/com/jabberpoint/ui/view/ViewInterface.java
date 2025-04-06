package com.jabberpoint.ui.view;

import com.jabberpoint.patterns.composite.PresentationInterface;

/**
 * Interface defining the contract for view components that display presentations.
 * This allows for easier mocking in tests.
 */
public interface ViewInterface {
    /**
     * Update the view with the provided presentation
     * 
     * @param presentation The presentation to display
     */
    void update(PresentationInterface presentation);
    
    /**
     * Updates the view
     */
    void updateView();
} 