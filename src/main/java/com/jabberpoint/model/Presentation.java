package com.jabberpoint.model;

import com.jabberpoint.observer.SlideObserver;
import java.util.ArrayList;
import java.util.List;

public class Presentation {
    private String title;
    private List<Slide> slides;
    private int currentSlideIndex;
    private List<SlideObserver> observers;

    public Presentation() {
        slides = new ArrayList<>();
        observers = new ArrayList<>();
        currentSlideIndex = 0;
    }

    public void addSlide(Slide slide) {
        slides.add(slide);
        notifyObservers();
    }

    public List<Slide> getSlides() {
        return slides;
    }   

    public void setCurrentSlideIndex(int index) {
        if (index >= 0 && index < slides.size()) {
            this.currentSlideIndex = index;
            notifyObservers();
        }
    }

    public Slide getCurrentSlide() {
        if (slides.isEmpty()) {
            return null;
        }
        return slides.get(currentSlideIndex);
    }

    public void nextSlide() {
        if (currentSlideIndex < slides.size() - 1) {
            currentSlideIndex++;
            notifyObservers();
        }
    }

    public void previousSlide() {
        if (currentSlideIndex > 0) {
            currentSlideIndex--;
            notifyObservers();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public void addObserver(SlideObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SlideObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (SlideObserver observer : observers) {
            observer.update();
        }
    }
}
