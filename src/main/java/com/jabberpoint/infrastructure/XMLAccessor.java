package com.jabberpoint.infrastructure;

import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.patterns.composite.SlideItem;
import com.jabberpoint.patterns.composite.BitmapItem;
import com.jabberpoint.patterns.composite.TitleItem;
import com.jabberpoint.patterns.composite.SubtitleItem;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.patterns.composite.BulletPointItem;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.style.Style;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class XMLAccessor extends Accessor {
    private static final Logger LOGGER = Logger.getLogger(XMLAccessor.class.getName());
    private Style defaultStyle;

    @Override
    public void loadPresentation(PresentationInterface presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, "showtitle"));
            
            // Load global style
            Element styleElement = (Element) doc.getElementsByTagName("style").item(0);
            if (styleElement != null) {
                defaultStyle = createStyleFromElement(styleElement);
            } else {
                defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);
            }
            
            NodeList slides = doc.getElementsByTagName("slide");
            int max = slides.getLength();
            for (int i = 0; i < max; i++) {
                Element xmlSlide = (Element) slides.item(i);
                Slide slide = new Slide();
                String title = getTitle(xmlSlide, "title");
                slide.setTitle(title);
                
                // Load slide background
                String background = xmlSlide.getAttribute("background");
                if (background != null && !background.isEmpty()) {
                    slide.setBackground(background);
                }
                
                presentation.addSlide(slide);
                
                // Load title
                Element titleElement = (Element) xmlSlide.getElementsByTagName("title").item(0);
                if (titleElement != null) {
                    Style titleStyle = createStyleFromElement(titleElement, defaultStyle);
                    slide.addItem(new TitleItem(trimTextContent(titleElement.getTextContent()), titleStyle));
                }
                
                // Load subtitle
                Element subtitleElement = (Element) xmlSlide.getElementsByTagName("subtitle").item(0);
                if (subtitleElement != null) {
                    Style subtitleStyle = createStyleFromElement(subtitleElement, defaultStyle);
                    slide.addItem(new SubtitleItem(trimTextContent(subtitleElement.getTextContent()), subtitleStyle));
                }
                
                // Load body text
                Element bodyTextElement = (Element) xmlSlide.getElementsByTagName("bodyText").item(0);
                if (bodyTextElement != null) {
                    Style bodyStyle = createStyleFromElement(bodyTextElement, defaultStyle);
                    slide.addItem(new BodyTextItem(trimTextContent(bodyTextElement.getTextContent()), bodyStyle));
                }
                
                // Load bullet points
                Element bulletPointsElement = (Element) xmlSlide.getElementsByTagName("bulletPoints").item(0);
                if (bulletPointsElement != null) {
                    NodeList bullets = bulletPointsElement.getElementsByTagName("bullet");
                    for (int j = 0; j < bullets.getLength(); j++) {
                        Element bulletElement = (Element) bullets.item(j);
                        Style bulletStyle = createStyleFromElement(bulletElement, defaultStyle);
                        slide.addItem(new BulletPointItem(trimTextContent(bulletElement.getTextContent()), bulletStyle));
                    }
                }
                
                // Load bitmap
                Element bitmapElement = (Element) xmlSlide.getElementsByTagName("bitmap").item(0);
                if (bitmapElement != null) {
                    Style bitmapStyle = createStyleFromElement(bitmapElement, defaultStyle);
                    slide.addItem(new BitmapItem(trimTextContent(bitmapElement.getTextContent()), bitmapStyle));
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.log(Level.SEVERE, "Error loading presentation", e);
            throw new IOException("Error loading presentation", e);
        }
    }

    /**
     * Trims whitespace from text content and removes all newlines
     * @param text The text content to trim
     * @return The trimmed text content with all newlines removed
     */
    private String trimTextContent(String text) {
        if (text == null) {
            return "";
        }
        
        // Replace all newlines with spaces and trim
        return text.replaceAll("\\s+", " ").trim();
    }

    private Style createStyleFromElement(Element element) {
        return createStyleFromElement(element, null);
    }

    private Style createStyleFromElement(Element element, Style defaultStyle) {
        String fontName = element.getAttribute("fontName");
        String fontColor = element.getAttribute("fontColor");
        
        FontName titleFont = fontName != null && !fontName.isEmpty() ? 
            FontName.valueOf(fontName) : 
            (defaultStyle != null ? defaultStyle.getTitleFontName() : FontName.ARIAL);
            
        FontName bodyFont = fontName != null && !fontName.isEmpty() ? 
            FontName.valueOf(fontName) : 
            (defaultStyle != null ? defaultStyle.getBodyFontName() : FontName.ARIAL);
            
        FontColor titleColor = fontColor != null && !fontColor.isEmpty() ? 
            FontColor.valueOf(fontColor) : 
            (defaultStyle != null ? defaultStyle.getTitleColor() : FontColor.BLACK);
            
        FontColor bodyColor = fontColor != null && !fontColor.isEmpty() ? 
            FontColor.valueOf(fontColor) : 
            (defaultStyle != null ? defaultStyle.getBodyColor() : FontColor.BLACK);
        
        return new Style(titleFont, bodyFont, titleColor, bodyColor);
    }

    @Override
    public void savePresentation(PresentationInterface presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();
            Element doc = document.createElement("presentation");
            doc.setAttribute("title", presentation.getTitle());
            document.appendChild(doc);
            
            // Save global style
            Element styleElement = document.createElement("style");
            styleElement.setAttribute("fontName", defaultStyle.getTitleFontName().name());
            styleElement.setAttribute("fontColor", defaultStyle.getTitleColor().name());
            doc.appendChild(styleElement);
            
            for (int i = 0; i < presentation.getSlideCount(); i++) {
                Slide slide = presentation.getSlides().get(i);
                Element xmlSlide = document.createElement("slide");
                xmlSlide.setAttribute("title", slide.getTitle());
                
                if (slide.getBackground() != null) {
                    xmlSlide.setAttribute("background", slide.getBackground());
                }
                
                doc.appendChild(xmlSlide);
                
                for (SlideItem item : slide.getItems()) {
                    if (item instanceof BitmapItem) {
                        writeBitmapItem(document, xmlSlide, (BitmapItem) item);
                    } else if (item instanceof TitleItem) {
                        writeTitleItem(document, xmlSlide, (TitleItem) item);
                    } else if (item instanceof SubtitleItem) {
                        writeSubtitleItem(document, xmlSlide, (SubtitleItem) item);
                    } else if (item instanceof BodyTextItem) {
                        writeBodyTextItem(document, xmlSlide, (BodyTextItem) item);
                    } else if (item instanceof BulletPointItem) {
                        writeBulletPointItem(document, xmlSlide, (BulletPointItem) item);
                    }
                }
            }
            writeDocument(document, filename);
        } catch (ParserConfigurationException | TransformerException e) {
            LOGGER.log(Level.SEVERE, "Error saving presentation", e);
            throw new IOException("Error saving presentation", e);
        }
    }

    private void writeDocument(Document document, String filename) throws TransformerException, IOException {
        try (FileOutputStream output = new FileOutputStream(filename)) {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(output));
        }
    }

    private void writeBitmapItem(Document document, Element xmlSlide, BitmapItem item) {
        Element xmlItem = document.createElement("bitmap");
        xmlItem.setAttribute("fontName", item.getStyle().getTitleFontName().name());
        xmlItem.setAttribute("fontColor", item.getStyle().getTitleColor().name());
        xmlItem.setTextContent(item.getText());
        xmlSlide.appendChild(xmlItem);
    }

    private void writeTitleItem(Document document, Element xmlSlide, TitleItem item) {
        Element xmlItem = document.createElement("title");
        xmlItem.setAttribute("fontName", item.getStyle().getTitleFontName().name());
        xmlItem.setAttribute("fontColor", item.getStyle().getTitleColor().name());
        xmlItem.setTextContent(item.getText());
        xmlSlide.appendChild(xmlItem);
    }

    private void writeSubtitleItem(Document document, Element xmlSlide, SubtitleItem item) {
        Element xmlItem = document.createElement("subtitle");
        xmlItem.setAttribute("fontName", item.getStyle().getTitleFontName().name());
        xmlItem.setAttribute("fontColor", item.getStyle().getTitleColor().name());
        xmlItem.setTextContent(item.getText());
        xmlSlide.appendChild(xmlItem);
    }

    private void writeBodyTextItem(Document document, Element xmlSlide, BodyTextItem item) {
        Element xmlItem = document.createElement("bodyText");
        xmlItem.setAttribute("fontName", item.getStyle().getBodyFontName().name());
        xmlItem.setAttribute("fontColor", item.getStyle().getBodyColor().name());
        xmlItem.setTextContent(item.getText());
        xmlSlide.appendChild(xmlItem);
    }

    private void writeBulletPointItem(Document document, Element xmlSlide, BulletPointItem item) {
        Element bulletPointsElement = (Element) xmlSlide.getElementsByTagName("bulletPoints").item(0);
        if (bulletPointsElement == null) {
            bulletPointsElement = document.createElement("bulletPoints");
            xmlSlide.appendChild(bulletPointsElement);
        }
        
        Element xmlItem = document.createElement("bullet");
        xmlItem.setAttribute("fontName", item.getStyle().getBodyFontName().name());
        xmlItem.setAttribute("fontColor", item.getStyle().getBodyColor().name());
        xmlItem.setTextContent(item.getText());
        bulletPointsElement.appendChild(xmlItem);
    }

    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.getLength() > 0 ? titles.item(0).getTextContent() : "";
    }
}
