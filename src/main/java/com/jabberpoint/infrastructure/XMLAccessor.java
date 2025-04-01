package com.jabberpoint.infrastructure;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.SlideItem;
import com.jabberpoint.model.TextItem;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;

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

    // Helper method to escape XML special characters
    private String escapeXML(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    @Override
    public void savePresentation(Presentation presentation, String destination) {
        File file = new File(destination);

        try (FileWriter writer = new FileWriter(file)) {
            // Write XML declaration
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<presentation title=\"" + escapeXML(presentation.getTitle()) + "\">\n");

            // Save slides
            for (Slide slide : presentation.getSlides()) {
                writer.write("  <slide title=\"" + escapeXML(slide.getTitle()) + "\"");

                // Add background attribute if it exists
                if (slide.getBackgroundImage() != null && !slide.getBackgroundImage().isEmpty()) {
                    writer.write(" background=\"" + escapeXML(slide.getBackgroundImage()) + "\"");
                }

                writer.write(">\n");

                // Save slide items
                for (SlideItem item : slide.getItems()) {
                    // Only saves text items
                    if (item instanceof TextItem) {
                        TextItem textItem = (TextItem) item;
                        writer.write("    <text>" + escapeXML(textItem.getText()) + "</text>\n");
                    }
                }

                writer.write("  </slide>\n");
            }

            writer.write("</presentation>");

            System.out.println("File saved: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error saving presentation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
