package com.jabberpoint;

import com.jabberpoint.model.Presentation;

public interface Accessor {
    Presentation loadPresentation(String source);
    void savePresentation(Presentation presentation, String destination);
}
