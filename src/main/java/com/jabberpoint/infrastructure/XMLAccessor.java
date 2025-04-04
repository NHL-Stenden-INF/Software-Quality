package com.jabberpoint.infrastructure;

import com.jabberpoint.patterns.composite.BackgroundItem;
import com.jabberpoint.patterns.composite.BitmapItem;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.patterns.composite.BulletPointItem;
import com.jabberpoint.patterns.composite.Presentation;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.patterns.composite.SlideItem;
import com.jabberpoint.patterns.composite.SubtitleItem;
import com.jabberpoint.patterns.composite.TitleItem;
import com.jabberpoint.patterns.factory.ItemType;
import com.jabberpoint.patterns.factory.SlideItemFactory;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontSize;
import com.jabberpoint.style.Style;
import com.jabberpoint.style.StyleManager;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class XMLAccessor implements Accessor {
    private static final Logger LOGGER = Logger.getLogger(XMLAccessor.class.getName());

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
            LOGGER.log(Level.SEVERE, "Error loading presentation: " + e.getMessage(), e);
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
        Style style = loadStyleFromElement(elem);

        switch (tagName) {
            case "title":
                slide.addItem(SlideItemFactory.createSlideItem(ItemType.TITLE, content, style));
                break;
            case "subtitle":
                slide.addItem(SlideItemFactory.createSlideItem(ItemType.SUBTITLE, content, style));
                break;
            case "bodyText":
                slide.addItem(SlideItemFactory.createSlideItem(ItemType.TEXT, content, style));
                break;
            case "bulletPoints":
                processBulletPoints(elem, slide);
                break;
            case "bitmap":
                slide.addItem(SlideItemFactory.createSlideItem(ItemType.BITMAP, content, style));
                break;
        }
    }

    private void processBulletPoints(Element bulletPointsElem, Slide slide) {
        NodeList bullets = bulletPointsElem.getElementsByTagName("bullet");
        for (int i = 0; i < bullets.getLength(); i++) {
            Element bulletElem = (Element) bullets.item(i);
            String text = bulletElem.getTextContent().trim();
            Style style = loadStyleFromElement(bulletElem);
            slide.addItem(SlideItemFactory.createSlideItem(ItemType.BULLET, text, style));
        }
    }

    private Style loadStyleFromElement(Element elem) {
        String fontName = elem.getAttribute("fontName");
        String fontSize = elem.getAttribute("fontSize");
        String fontColor = elem.getAttribute("fontColor");

        FontName fontNameEnum = parseEnum(FontName.class, fontName, FontName.ARIAL);
        FontSize fontSizeEnum = parseEnum(FontSize.class, fontSize, FontSize.MEDIUM);
        FontColor fontColorEnum = (fontColor == null || fontColor.isEmpty())
                ? FontColor.BLACK
                : FontColor.fromString(fontColor);

        return new Style(fontNameEnum, fontSizeEnum, fontColorEnum);
    }

    private <T extends Enum<T>> T parseEnum(Class<T> enumType, String value, T defaultValue) {
        if (value == null || value.isEmpty()) return defaultValue;
        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.warning(String.format("'%s' is not a valid value for %s. Using default: %s",
                    value, enumType.getSimpleName(), defaultValue));
            return defaultValue;
        }
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
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving presentation: " + e.getMessage(), e);
        }
    }

    private void writeSlide(FileWriter writer, Slide slide) throws IOException {
        writer.write(String.format("  <slide title=\"%s\"", escapeXML(slide.getTitle())));

        // Write background if present
        slide.getItems().stream()
                .filter(item -> item instanceof BackgroundItem)
                .findFirst()
                .ifPresent(bg -> {
                    try {
                        writer.write(String.format(" background=\"%s\"", escapeXML(((BackgroundItem) bg).getImagePath())));
                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, "Error writing background: " + e.getMessage(), e);
                    }
                });

        writer.write(">\n");

        // Write content items
        for (SlideItem item : slide.getItems()) {
            if (item instanceof BackgroundItem) continue;

            if (item instanceof BulletPointItem) {
                writeBulletPoints(writer, slide);
                break;
            }
            if (item instanceof BitmapItem) {
                writeBitmapItem(writer, (BitmapItem) item);
            } else {
                writeStandardItem(writer, item);
            }
        }
        writer.write("  </slide>\n");
    }

    private void writeBulletPoints(FileWriter writer, Slide slide) throws IOException {
        writer.write("    <bulletPoints>\n");
        slide.getItems().stream()
                .filter(item -> item instanceof BulletPointItem)
                .forEach(item -> {
                    try {
                        writer.write(String.format("      <bullet>%s</bullet>\n", escapeXML(item.getText())));
                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, "Error writing bullet point: " + e.getMessage(), e);
                    }
                });
        writer.write("    </bulletPoints>\n");
    }

    private void writeBitmapItem(FileWriter writer, BitmapItem item) throws IOException {
        Style style = item.getStyle();
        writer.write("    <bitmap");
        if (style != null) {
            writer.write(String.format(" fontName=\"%s\"", style.getFontName().name()));
            writer.write(String.format(" fontSize=\"%s\"", style.getFontSize().name()));
            writer.write(String.format(" fontColor=\"%s\"", style.getFontColor().name()));
        }
        writer.write(String.format(">%s</bitmap>\n", escapeXML(item.getText())));
    }

    private void writeStandardItem(FileWriter writer, SlideItem item) throws IOException {
        String tag = getItemTagName(item);
        if (tag != null) {
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

    private void loadStyle(Element styleElem) {
        Style style = loadStyleFromElement(styleElem);
        StyleManager.setCurrentStyle(style);
    }
}
