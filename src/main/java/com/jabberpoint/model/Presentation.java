package com.jabberpoint.model;

import java.util.ArrayList;
import java.util.List;

public class Presentation {
    private String title;
    private List<Slide> slides;
    private int currentSlideIndex;

    public Presentation() {
        slides = new ArrayList<>();
        currentSlideIndex = 0;
    }

    public void addSlide(Slide slide) {
        slides.add(slide);
    }

    public List<Slide> getSlides() {
        return slides;
    }   

    public void setCurrentSlideIndex(int index) {
        if (index >= 0 && index < slides.size()) {
            this.currentSlideIndex = index;
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
        }
    }

    public void previousSlide() {
        if (currentSlideIndex > 0) {
            currentSlideIndex--;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
