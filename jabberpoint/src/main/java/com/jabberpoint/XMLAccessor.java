package com.jabberpoint;

import com.jabberpoint.model.Presentation;

public class XMLAccessor implements Accessor {

    @Override
    public Presentation loadPresentation(String source) {
        // load the presentation from an XML file
        // This be a stub – implement XML parsing as needed
        System.out.println("Loading presentation from: " + source);
        return new Presentation();
    }

    @Override
    public void savePresentation(Presentation presentation, String destination) {
        // Save the presentation to an XML file
        System.out.println("Saving presentation to: " + destination);
    }
}
