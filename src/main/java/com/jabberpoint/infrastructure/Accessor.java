package com.jabberpoint.infrastructure;

import com.jabberpoint.patterns.composite.Presentation;

public interface Accessor {
    Presentation loadPresentation(String source);
    void savePresentation(Presentation presentation, String destination);
}
