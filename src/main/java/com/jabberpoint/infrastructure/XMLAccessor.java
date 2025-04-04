package com.jabberpoint.infrastructure;

import com.jabberpoint.model.*;
import com.jabberpoint.entities.Style;
import com.jabberpoint.entities.FontName;
import com.jabberpoint.entities.FontSize;
import com.jabberpoint.entities.FontColor;
import com.jabberpoint.presentation.StyleManager;

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

            Element presElem = doc.getDocumentElement();
            presentation.setTitle(presElem.getAttribute("title"));

            // Load style if present
            Element styleElem = (Element) presElem.getElementsByTagName("style").item(0);
            if (styleElem != null) {
                loadStyle(styleElem);
            }

            NodeList slideNodes = doc.getElementsByTagName("slide");
            for (int i = 0; i < slideNodes.getLength(); i++) {
                Element slideElem = (Element) slideNodes.item(i);
                Slide slide = processSlideElement(slideElem);
                presentation.addSlide(slide);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return presentation;
    }

    private Slide processSlideElement(Element slideElem) {
        Slide slide = new Slide(slideElem.getAttribute("title"));
    
        // Process background
        String background = slideElem.getAttribute("background");
        if (!background.isEmpty()) {
            Style style = loadStyleFromElement(slideElem);
            slide.addItem(new BackgroundItem(background, style));
        }
    
        // Process content items
        NodeList content = slideElem.getChildNodes();
        for (int i = 0; i < content.getLength(); i++) {
            Node node = content.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                processElement(elem, slide);
            }
        }
        return slide;
    }
    

    private void processElement(Element elem, Slide slide) {
        String tagName = elem.getTagName();
        String content = elem.getTextContent().trim();
    
        // Retrieve the style for each item
        Style style = loadStyleFromElement(elem);
    
        switch (tagName) {
            case "title":
                slide.addItem(new TitleItem(content, style));
                break;
            case "subtitle":
                slide.addItem(new SubtitleItem(content, style));
                break;
            case "bodyText":
                slide.addItem(new BodyTextItem(content, style));
                break;
            case "bulletPoints":
                processBulletPoints(elem, slide, style);
                break;
        }
    }

    private void processBulletPoints(Element bulletPointsElem, Slide slide, Style style) {
        NodeList bullets = bulletPointsElem.getElementsByTagName("bullet");
        for (int i = 0; i < bullets.getLength(); i++) {
            Element bulletElem = (Element) bullets.item(i);
            slide.addItem(new BulletPointItem(bulletElem.getTextContent().trim(), style));
        }
    }

    private Style loadStyleFromElement(Element elem) {
        String fontName = elem.getAttribute("fontName");
        String fontSize = elem.getAttribute("fontSize");
        String fontColor = elem.getAttribute("fontColor");
    
        // Use default values if any attribute is missing or empty
        FontName fontNameEnum = (fontName.isEmpty() ? FontName.ARIAL : FontName.valueOf(fontName.toUpperCase()));
        FontSize fontSizeEnum = (fontSize.isEmpty() ? FontSize.MEDIUM : FontSize.valueOf(fontSize.toUpperCase()));
        FontColor fontColorEnum = (fontColor.isEmpty() ? FontColor.BLACK : FontColor.valueOf(fontColor.toUpperCase()));
    
        return new Style(fontNameEnum, fontSizeEnum, fontColorEnum);
    }  

    @Override
    public void savePresentation(Presentation presentation, String destination) {
        try (FileWriter writer = new FileWriter(destination)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(String.format("<presentation title=\"%s\">\n", escapeXML(presentation.getTitle())));

            // Save style
            Style style = StyleManager.getCurrentStyle();
            writer.write("  <style");
            writer.write(String.format(" fontName=\"%s\"", style.getFontName().name()));
            writer.write(String.format(" fontSize=\"%s\"", style.getFontSize().name()));
            writer.write(String.format(" fontColor=\"%s\"/>\n", style.getFontColor().name()));

            for (Slide slide : presentation.getSlides()) {
                writeSlide(writer, slide);
            }
            writer.write("</presentation>");
        } catch (Exception e) {
            System.err.println("Error saving presentation: " + e.getMessage());
        }
    }

    private void writeSlide(FileWriter writer, Slide slide) throws Exception {
        writer.write(String.format("  <slide title=\"%s\"", escapeXML(slide.getTitle())));
    
        // Write background if present
        slide.getItems().stream()
            .filter(item -> item instanceof BackgroundItem)
            .findFirst()
            .ifPresent(bg -> {
                try {
                    writer.write(String.format(" background=\"%s\"", escapeXML(((BackgroundItem) bg).getImagePath()))); 
                } catch (Exception e) { /* handle error */ }
            });
    
        writer.write(">\n");
    
        // Write content items
        for (SlideItem item : slide.getItems()) {
            if (item instanceof BackgroundItem) continue;
    
            if (item instanceof BulletPointItem) {
                writeBulletPoints(writer, slide);
                break;
            }
            writeStandardItem(writer, item);
        }
        writer.write("  </slide>\n");
    }

    private void writeBulletPoints(FileWriter writer, Slide slide) throws Exception {
        writer.write("    <bulletPoints>\n");
        slide.getItems().stream()
            .filter(item -> item instanceof BulletPointItem)
            .forEach(item -> {
                try {
                    writer.write(String.format("      <bullet>%s</bullet>\n", escapeXML(item.getText())));
                } catch (Exception e) { /* handle error */ }
            });
        writer.write("    </bulletPoints>\n");
    }

    private void writeStandardItem(FileWriter writer, SlideItem item) throws Exception {
        String tag = getItemTagName(item);
        if (tag != null) {
            // Retrieve the style of the item
            Style style = item.getStyle();
            writer.write(String.format("    <%s", tag));
            if (style != null) {
                writer.write(String.format(" fontName=\"%s\"", style.getFontName().name()));
                writer.write(String.format(" fontSize=\"%s\"", style.getFontSize().name()));
                writer.write(String.format(" fontColor=\"%s\"", style.getFontColor().name()));
            }
            writer.write(String.format(">%s</%s>\n", escapeXML(item.getText()), tag));
        }
    }    

    private String getItemTagName(SlideItem item) {
        if (item instanceof TitleItem) return "title";
        if (item instanceof SubtitleItem) return "subtitle";
        if (item instanceof BodyTextItem) return "bodyText";
        return null;
    }

    private String escapeXML(String input) {
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;");
    }

    private Style loadStyle(Element elem) {
        String fontName = elem.getAttribute("fontName");
        String fontSize = elem.getAttribute("fontSize");
        String fontColor = elem.getAttribute("fontColor");
    
        // Default values if any attribute is missing or empty
        FontName fontNameEnum = (fontName.isEmpty() ? FontName.ARIAL : FontName.valueOf(fontName.toUpperCase()));
        FontSize fontSizeEnum = (fontSize.isEmpty() ? FontSize.MEDIUM : FontSize.valueOf(fontSize.toUpperCase()));
        FontColor fontColorEnum = (fontColor.isEmpty() ? FontColor.BLACK : FontColor.valueOf(fontColor.toUpperCase()));
    
        return new Style(fontNameEnum, fontSizeEnum, fontColorEnum);
    }
}
