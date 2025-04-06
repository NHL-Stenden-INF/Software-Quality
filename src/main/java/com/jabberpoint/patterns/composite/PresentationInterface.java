package com.jabberpoint.patterns.composite;

import java.util.List;
import com.jabberpoint.patterns.observer.SlideObserver;

/**
 * Interface for the Presentation class to follow the Dependency Inversion Principle.
 * This allows high-level modules to depend on abstractions rather than concrete implementations.
 */
public interface PresentationInterface {
    /**
     * Adds a slide to the presentation
     * @param slide The slide to add
     */
    void addSlide(Slide slide);

    /**
     * Gets all slides in the presentation
     * @return List of slides
     */
    List<Slide> getSlides();

    /**
     * Sets the current slide index
     * @param index The index to set
     */
    void setCurrentSlideIndex(int index);

    /**
     * Gets the current slide
     * @return The current slide
     */
    Slide getCurrentSlide();

    /**
     * Moves to the next slide
     */
    void nextSlide();

    /**
     * Moves to the previous slide
     */
    void previousSlide();

    /**
     * Gets the title of the presentation
     * @return The title
     */
    String getTitle();

    /**
     * Sets the title of the presentation
     * @param title The title to set
     */
    void setTitle(String title);

    /**
     * Adds an observer to the presentation
     * @param observer The observer to add
     */
    void addObserver(SlideObserver observer);

    /**
     * Removes an observer from the presentation
     * @param observer The observer to remove
     */
    void removeObserver(SlideObserver observer);

    /**
     * Gets the number of slides in the presentation
     * @return The number of slides
     */
    int getSlideCount();

    /**
     * Copies data from another presentation
     * @param other The presentation to copy from
     */
    void copyFrom(PresentationInterface other);
} 