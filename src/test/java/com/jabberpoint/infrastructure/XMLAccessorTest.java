package com.jabberpoint.infrastructure;

import com.jabberpoint.patterns.composite.*;
import com.jabberpoint.style.FontName;
import com.jabberpoint.style.FontColor;
import com.jabberpoint.style.Style;
import com.jabberpoint.patterns.observer.SlideObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class XMLAccessorTest {

    private XMLAccessor xmlAccessor;
    private PresentationInterface presentation;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        xmlAccessor = new XMLAccessor();
        presentation = createPresentation();
        
        // Initialize defaultStyle by loading a presentation with style
        File initFile = createTestXmlFile();
        xmlAccessor.loadPresentation(presentation, initFile.getAbsolutePath());
        presentation = createPresentation(); // Create a fresh presentation for tests
    }

    private PresentationInterface createPresentation() {
        return new PresentationInterface() {
            private String title = "";
            private List<Slide> slides = new ArrayList<>();
            private int currentSlideIndex = 0;
            private List<SlideObserver> observers = new ArrayList<>();

            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public void addSlide(Slide slide) {
                slides.add(slide);
            }

            @Override
            public int getSlideCount() {
                return slides.size();
            }

            @Override
            public List<Slide> getSlides() {
                return slides;
            }

            @Override
            public void addObserver(SlideObserver observer) {
                observers.add(observer);
            }

            @Override
            public void removeObserver(SlideObserver observer) {
                observers.remove(observer);
            }

            @Override
            public Slide getCurrentSlide() {
                return slides.isEmpty() ? null : slides.get(currentSlideIndex);
            }

            @Override
            public void previousSlide() {
                if (currentSlideIndex > 0) {
                    currentSlideIndex--;
                    notifyObservers();
                }
            }

            @Override
            public void nextSlide() {
                if (currentSlideIndex < slides.size() - 1) {
                    currentSlideIndex++;
                    notifyObservers();
                }
            }

            @Override
            public void setCurrentSlideIndex(int index) {
                if (index >= 0 && index < slides.size()) {
                    currentSlideIndex = index;
                    notifyObservers();
                }
            }

            @Override
            public void copyFrom(PresentationInterface other) {
                this.title = other.getTitle();
                this.slides.clear();
                this.slides.addAll(other.getSlides());
                this.currentSlideIndex = 0;
                notifyObservers();
            }

            private void notifyObservers() {
                for (SlideObserver observer : observers) {
                    observer.update(this);
                }
            }
        };
    }

    @Test
    void loadPresentation_ShouldLoadTitleAndSlides() throws IOException {
        File xmlFile = createTestXmlFile();
        presentation.setTitle(""); // Ensure title is empty before loading

        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());

        assertEquals("Test Presentation", presentation.getTitle());
        assertEquals(1, presentation.getSlideCount());
        Slide slide = presentation.getSlides().get(0);
        assertEquals("Test Slide", slide.getTitle());
        assertEquals("blue", slide.getBackground());
        assertEquals(5, slide.getItems().size());
    }

    @Test
    void loadPresentation_ShouldHandleMissingStyle() throws IOException {
        File xmlFile = createTestXmlFileWithoutStyle();
        presentation.setTitle(""); // Ensure title is empty before loading

        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());

        assertEquals("Test Presentation", presentation.getTitle());
        assertEquals(1, presentation.getSlideCount());
        Slide slide = presentation.getSlides().get(0);
        assertEquals("Test Slide", slide.getTitle());
        assertEquals(5, slide.getItems().size());
    }

    @Test
    void savePresentation_ShouldSaveTitleAndSlides() throws IOException {
        File xmlFile = new File(tempDir.toFile(), "test.xml");
        presentation.setTitle("Test Presentation");
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.setBackground("blue");

        Style defaultStyle = new Style(FontName.ARIAL, FontName.ARIAL, FontColor.BLACK, FontColor.BLACK);

        slide.addItem(new TitleItem("Test Title", defaultStyle));
        slide.addItem(new SubtitleItem("Test Subtitle", defaultStyle));
        slide.addItem(new BodyTextItem("Test Body", defaultStyle));
        slide.addItem(new BulletPointItem("Test Bullet", defaultStyle));

        File testImage = new File(tempDir.toFile(), "test.jpg");
        java.nio.file.Files.write(testImage.toPath(), "Test image content".getBytes());
        slide.addItem(new BitmapItem(testImage.getAbsolutePath(), defaultStyle));
        
        presentation.addSlide(slide);

        xmlAccessor.savePresentation(presentation, xmlFile.getAbsolutePath());

        assertTrue(xmlFile.exists());
        Document document = parseXmlFile(xmlFile);
        Element root = document.getDocumentElement();
        assertEquals("Test Presentation", root.getAttribute("title"));
        Element styleElement = (Element) root.getElementsByTagName("style").item(0);
        assertNotNull(styleElement);
        assertEquals(FontName.ARIAL.name(), styleElement.getAttribute("fontName"));
        assertEquals(FontColor.BLACK.name(), styleElement.getAttribute("fontColor"));
        Element slideElement = (Element) root.getElementsByTagName("slide").item(0);
        assertNotNull(slideElement);

        assertEquals("Test Slide", slideElement.getAttribute("title"));

        Element titleItemElement = (Element) slideElement.getElementsByTagName("title").item(0);
        assertNotNull(titleItemElement);
        assertEquals("Test Title", titleItemElement.getTextContent());
        assertEquals(FontName.ARIAL.name(), titleItemElement.getAttribute("fontName"));
        assertEquals(FontColor.BLACK.name(), titleItemElement.getAttribute("fontColor"));
        
        assertEquals("blue", slideElement.getAttribute("background"));
    }

    private File createTestXmlFile() throws IOException {
        File xmlFile = new File(tempDir.toFile(), "test.xml");
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<presentation>\n" +
                "    <showtitle>Test Presentation</showtitle>\n" +
                "    <style fontName=\"ARIAL\" fontColor=\"BLACK\"/>\n" +
                "    <slide background=\"blue\">\n" +
                "        <title>Test Slide</title>\n" +
                "        <title fontName=\"ARIAL\" fontColor=\"BLACK\">Test Title</title>\n" +
                "        <subtitle fontName=\"ARIAL\" fontColor=\"BLACK\">Test Subtitle</subtitle>\n" +
                "        <bodyText fontName=\"ARIAL\" fontColor=\"BLACK\">Test Body</bodyText>\n" +
                "        <bulletPoints>\n" +
                "            <bullet fontName=\"ARIAL\" fontColor=\"BLACK\">Test Bullet</bullet>\n" +
                "        </bulletPoints>\n" +
                "        <bitmap fontName=\"ARIAL\" fontColor=\"BLACK\">test.jpg</bitmap>\n" +
                "    </slide>\n" +
                "</presentation>";
        java.nio.file.Files.write(xmlFile.toPath(), xmlContent.getBytes());
        return xmlFile;
    }

    private File createTestXmlFileWithoutStyle() throws IOException {
        File xmlFile = new File(tempDir.toFile(), "test_no_style.xml");
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<presentation>\n" +
                "    <showtitle>Test Presentation</showtitle>\n" +
                "    <slide>\n" +
                "        <title>Test Slide</title>\n" +
                "        <title>Test Title</title>\n" +
                "        <subtitle>Test Subtitle</subtitle>\n" +
                "        <bodyText>Test Body</bodyText>\n" +
                "        <bulletPoints>\n" +
                "            <bullet>Test Bullet</bullet>\n" +
                "        </bulletPoints>\n" +
                "        <bitmap>test.jpg</bitmap>\n" +
                "    </slide>\n" +
                "</presentation>";
        java.nio.file.Files.write(xmlFile.toPath(), xmlContent.getBytes());
        return xmlFile;
    }

    private Document parseXmlFile(File file) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML file", e);
        }
    }
} 