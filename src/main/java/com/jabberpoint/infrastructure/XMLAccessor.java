package com.jabberpoint.infrastructure;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.TextItem;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLAccessor implements Accessor {

    @Override
    public Presentation loadPresentation(String source) {
        Presentation presentation = new Presentation();
        try {
            File xmlFile = new File(source);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Set presentation title from attribute "title"
            Element presElem = doc.getDocumentElement();
            String presTitle = presElem.getAttribute("title");
            presentation.setTitle(presTitle);

            // Get all slide nodes
            NodeList slideNodes = doc.getElementsByTagName("slide");
            for (int i = 0; i < slideNodes.getLength(); i++) {
                Node slideNode = slideNodes.item(i);
                if (slideNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element slideElem = (Element) slideNode;
                    String slideTitle = slideElem.getAttribute("title");
                    Slide slide = new Slide(slideTitle);

                    // Read the background attribute
                    String background = slideElem.getAttribute("background");
                    if (background != null && !background.isEmpty()) {
                        slide.setBackgroundImage(background);
                    }
                    
                    // Read text items
                    NodeList textNodes = slideElem.getElementsByTagName("text");
                    for (int j = 0; j < textNodes.getLength(); j++) {
                        Node textNode = textNodes.item(j);
                        if (textNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element textElem = (Element) textNode;
                            String content = textElem.getTextContent().trim();
                            if (!content.isEmpty()) {
                                slide.addItem(new TextItem(content));
                            }
                        }
                    }
                    presentation.addSlide(slide);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null; // In case of error, return null
        }
        return presentation;
    }

    @Override
    public void savePresentation(Presentation presentation, String destination) {
        // WIP 
        System.out.println("Saving presentation not implemented.");
    }
}
