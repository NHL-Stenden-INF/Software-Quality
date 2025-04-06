package com.jabberpoint.patterns.composite;

import java.util.ArrayList;
import java.util.List;

import com.jabberpoint.patterns.observer.SlideObserver;

public class Presentation implements PresentationInterface {
    private String title;
    private List<Slide> slides;
    private int currentSlideIndex;
    private List<SlideObserver> observers;
    private boolean notifyEnabled = true;

    public Presentation() {
        slides = new ArrayList<>();
        observers = new ArrayList<>();
        currentSlideIndex = 0;
    }

    @Override
    public void addSlide(Slide slide) {
        slides.add(slide);
        notifyObservers();
    }

    @Override
    public List<Slide> getSlides() {
        return slides;
    }   

    @Override
    public void setCurrentSlideIndex(int index) {
        if (index >= 0 && index < slides.size()) {
            this.currentSlideIndex = index;
            notifyObservers();
        }
    }

    @Override
    public Slide getCurrentSlide() {
        if (slides.isEmpty()) {
            return null;
        }
        
        // Make sure currentSlideIndex is valid
        if (currentSlideIndex < 0 || currentSlideIndex >= slides.size()) {
            currentSlideIndex = 0; // Reset to first slide if out of bounds
        }
        
        return slides.get(currentSlideIndex);
    }

    @Override
    public void nextSlide() {
        if (currentSlideIndex < slides.size() - 1) {
            currentSlideIndex++;
            notifyObservers();
        }
    }

    @Override
    public void previousSlide() {
        if (currentSlideIndex > 0) {
            currentSlideIndex--;
            notifyObservers();
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    @Override
    public void addObserver(SlideObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(SlideObserver observer) {
        observers.remove(observer);
    }

    @Override
    public boolean isNotifyEnabled() {
        return notifyEnabled;
    }

    @Override
    public void setNotifyEnabled(boolean enabled) {
        this.notifyEnabled = enabled;
    }

    @Override
    public void notifyObservers() {
        if (!notifyEnabled) {
            return;
        }
        
        for (SlideObserver observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public int getSlideCount() {
        return slides.size();
    }

    public void replaceWith(Presentation other) {
        this.slides.clear();
        this.slides.addAll(other.getSlides());
        this.title = other.getTitle();
        this.currentSlideIndex = 0;
        notifyObservers();
    }

    @Override
    public void copyFrom(PresentationInterface other) {
        this.slides.clear();
        this.slides.addAll(other.getSlides());
        this.currentSlideIndex = 0;
        this.title = other.getTitle();
        notifyObservers();
    }
}
