package com.jabberpoint.infrastructure;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GraphicsContextWrapperTest {

    private MockGraphicsContext mockGraphicsContext;
    private GraphicsContextWrapper wrapper;
    @Mock
    private Image mockImage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockGraphicsContext = new MockGraphicsContext();
        wrapper = mockGraphicsContext;
    }

    @Test
    void setFill_ShouldStoreColor() {
        Color color = Color.BLACK;

        wrapper.setFill(color);

        assertEquals(color, mockGraphicsContext.getLastSetFill());
    }

    @Test
    void setStroke_ShouldStoreColor() {
        Color color = Color.RED;

        wrapper.setStroke(color);

        assertEquals(color, mockGraphicsContext.getLastSetStroke());
    }

    @Test
    void setLineWidth_ShouldStoreWidth() {
        double width = 2.0;

        wrapper.setLineWidth(width);

        assertEquals(width, mockGraphicsContext.getLastSetLineWidth());
    }

    @Test
    void setFont_ShouldStoreFont() {
        Font font = Font.font("Arial", 12);

        wrapper.setFont(font);

        assertEquals(font, mockGraphicsContext.getLastSetFont());
    }

    @Test
    void setTextAlign_ShouldStoreAlignment() {
        TextAlignment alignment = TextAlignment.CENTER;

        wrapper.setTextAlign(alignment);

        assertEquals(alignment, mockGraphicsContext.getLastSetTextAlign());
    }

    @Test
    void fillText_ShouldStoreParameters() {
        String text = "Test";
        double x = 10.0;
        double y = 20.0;

        wrapper.fillText(text, x, y);

        assertEquals(text, mockGraphicsContext.getLastFillText());
        assertEquals(x, mockGraphicsContext.getLastFillTextX());
        assertEquals(y, mockGraphicsContext.getLastFillTextY());
    }

    @Test
    void strokeText_ShouldStoreParameters() {
        String text = "Test";
        double x = 10.0;
        double y = 20.0;

        wrapper.strokeText(text, x, y);

        assertEquals(text, mockGraphicsContext.getLastStrokeText());
        assertEquals(x, mockGraphicsContext.getLastStrokeTextX());
        assertEquals(y, mockGraphicsContext.getLastStrokeTextY());
    }

    @Test
    void fillRect_ShouldStoreParameters() {
        double x = 10.0;
        double y = 20.0;
        double width = 100.0;
        double height = 50.0;

        wrapper.fillRect(x, y, width, height);

        assertEquals(x, mockGraphicsContext.getLastFillRectX());
        assertEquals(y, mockGraphicsContext.getLastFillRectY());
        assertEquals(width, mockGraphicsContext.getLastFillRectWidth());
        assertEquals(height, mockGraphicsContext.getLastFillRectHeight());
    }

    @Test
    void strokeRect_ShouldStoreParameters() {
        double x = 10.0;
        double y = 20.0;
        double width = 100.0;
        double height = 50.0;

        wrapper.strokeRect(x, y, width, height);

        assertEquals(x, mockGraphicsContext.getLastStrokeRectX());
        assertEquals(y, mockGraphicsContext.getLastStrokeRectY());
        assertEquals(width, mockGraphicsContext.getLastStrokeRectWidth());
        assertEquals(height, mockGraphicsContext.getLastStrokeRectHeight());
    }

    @Test
    void fillOval_ShouldStoreParameters() {
        double x = 10.0;
        double y = 20.0;
        double width = 100.0;
        double height = 50.0;

        wrapper.fillOval(x, y, width, height);

        assertEquals(x, mockGraphicsContext.getLastFillOvalX());
        assertEquals(y, mockGraphicsContext.getLastFillOvalY());
        assertEquals(width, mockGraphicsContext.getLastFillOvalWidth());
        assertEquals(height, mockGraphicsContext.getLastFillOvalHeight());
    }

    @Test
    void strokeOval_ShouldStoreParameters() {
        double x = 10.0;
        double y = 20.0;
        double width = 100.0;
        double height = 50.0;

        wrapper.strokeOval(x, y, width, height);

        assertEquals(x, mockGraphicsContext.getLastStrokeOvalX());
        assertEquals(y, mockGraphicsContext.getLastStrokeOvalY());
        assertEquals(width, mockGraphicsContext.getLastStrokeOvalWidth());
        assertEquals(height, mockGraphicsContext.getLastStrokeOvalHeight());
    }

    @Test
    void drawImage_ShouldStoreParameters() {
        Image image = mock(Image.class);
        double x = 10.0;
        double y = 20.0;

        wrapper.drawImage(image, x, y);

        assertEquals(image, mockGraphicsContext.getLastDrawImage());
        assertEquals(x, mockGraphicsContext.getLastDrawImageX());
        assertEquals(y, mockGraphicsContext.getLastDrawImageY());
        assertNull(mockGraphicsContext.getLastDrawImageWidth());
        assertNull(mockGraphicsContext.getLastDrawImageHeight());
    }

    @Test
    void drawImageWithDimensions_ShouldStoreParameters() {
        Image image = mock(Image.class);
        double x = 10.0;
        double y = 20.0;
        double width = 100.0;
        double height = 50.0;

        wrapper.drawImage(image, x, y, width, height);

        assertEquals(image, mockGraphicsContext.getLastDrawImage());
        assertEquals(x, mockGraphicsContext.getLastDrawImageX());
        assertEquals(y, mockGraphicsContext.getLastDrawImageY());
        assertEquals(width, mockGraphicsContext.getLastDrawImageWidth());
        assertEquals(height, mockGraphicsContext.getLastDrawImageHeight());
    }

    @Test
    void clearRect_ShouldStoreParameters() {
        double x = 10.0;
        double y = 20.0;
        double width = 100.0;
        double height = 50.0;

        wrapper.clearRect(x, y, width, height);

        assertEquals(x, mockGraphicsContext.getLastClearRectX());
        assertEquals(y, mockGraphicsContext.getLastClearRectY());
        assertEquals(width, mockGraphicsContext.getLastClearRectWidth());
        assertEquals(height, mockGraphicsContext.getLastClearRectHeight());
    }

    // Mock implementation of GraphicsContextWrapper for testing
    private static class MockGraphicsContext implements GraphicsContextWrapper {
        private Color lastSetFill;
        private Color lastSetStroke;
        private double lastSetLineWidth;
        private Font lastSetFont;
        private TextAlignment lastSetTextAlign;
        private String lastFillText;
        private double lastFillTextX;
        private double lastFillTextY;
        private String lastStrokeText;
        private double lastStrokeTextX;
        private double lastStrokeTextY;
        private double lastFillRectX;
        private double lastFillRectY;
        private double lastFillRectWidth;
        private double lastFillRectHeight;
        private double lastStrokeRectX;
        private double lastStrokeRectY;
        private double lastStrokeRectWidth;
        private double lastStrokeRectHeight;
        private double lastFillOvalX;
        private double lastFillOvalY;
        private double lastFillOvalWidth;
        private double lastFillOvalHeight;
        private double lastStrokeOvalX;
        private double lastStrokeOvalY;
        private double lastStrokeOvalWidth;
        private double lastStrokeOvalHeight;
        private Image lastDrawImage;
        private double lastDrawImageX;
        private double lastDrawImageY;
        private Double lastDrawImageWidth;
        private Double lastDrawImageHeight;
        private double lastClearRectX;
        private double lastClearRectY;
        private double lastClearRectWidth;
        private double lastClearRectHeight;

        @Override
        public void setFill(Color color) {
            this.lastSetFill = color;
        }

        @Override
        public void setStroke(Color color) {
            this.lastSetStroke = color;
        }

        @Override
        public void setLineWidth(double width) {
            this.lastSetLineWidth = width;
        }

        @Override
        public void setFont(Font font) {
            this.lastSetFont = font;
        }

        @Override
        public void setTextAlign(TextAlignment align) {
            this.lastSetTextAlign = align;
        }

        @Override
        public void fillText(String text, double x, double y) {
            this.lastFillText = text;
            this.lastFillTextX = x;
            this.lastFillTextY = y;
        }

        @Override
        public void strokeText(String text, double x, double y) {
            this.lastStrokeText = text;
            this.lastStrokeTextX = x;
            this.lastStrokeTextY = y;
        }

        @Override
        public void fillRect(double x, double y, double width, double height) {
            this.lastFillRectX = x;
            this.lastFillRectY = y;
            this.lastFillRectWidth = width;
            this.lastFillRectHeight = height;
        }

        @Override
        public void strokeRect(double x, double y, double width, double height) {
            this.lastStrokeRectX = x;
            this.lastStrokeRectY = y;
            this.lastStrokeRectWidth = width;
            this.lastStrokeRectHeight = height;
        }

        @Override
        public void fillOval(double x, double y, double width, double height) {
            this.lastFillOvalX = x;
            this.lastFillOvalY = y;
            this.lastFillOvalWidth = width;
            this.lastFillOvalHeight = height;
        }

        @Override
        public void strokeOval(double x, double y, double width, double height) {
            this.lastStrokeOvalX = x;
            this.lastStrokeOvalY = y;
            this.lastStrokeOvalWidth = width;
            this.lastStrokeOvalHeight = height;
        }

        @Override
        public void drawImage(Image image, double x, double y) {
            this.lastDrawImage = image;
            this.lastDrawImageX = x;
            this.lastDrawImageY = y;
            this.lastDrawImageWidth = null;
            this.lastDrawImageHeight = null;
        }

        @Override
        public void drawImage(Image image, double x, double y, double width, double height) {
            this.lastDrawImage = image;
            this.lastDrawImageX = x;
            this.lastDrawImageY = y;
            this.lastDrawImageWidth = width;
            this.lastDrawImageHeight = height;
        }

        @Override
        public void clearRect(double x, double y, double w, double h) {
            this.lastClearRectX = x;
            this.lastClearRectY = y;
            this.lastClearRectWidth = w;
            this.lastClearRectHeight = h;
        }

        // Getters for test verification
        public Color getLastSetFill() {
            return lastSetFill;
        }

        public Color getLastSetStroke() {
            return lastSetStroke;
        }

        public double getLastSetLineWidth() {
            return lastSetLineWidth;
        }

        public Font getLastSetFont() {
            return lastSetFont;
        }

        public TextAlignment getLastSetTextAlign() {
            return lastSetTextAlign;
        }

        public String getLastFillText() {
            return lastFillText;
        }

        public double getLastFillTextX() {
            return lastFillTextX;
        }

        public double getLastFillTextY() {
            return lastFillTextY;
        }

        public String getLastStrokeText() {
            return lastStrokeText;
        }

        public double getLastStrokeTextX() {
            return lastStrokeTextX;
        }

        public double getLastStrokeTextY() {
            return lastStrokeTextY;
        }

        public double getLastFillRectX() {
            return lastFillRectX;
        }

        public double getLastFillRectY() {
            return lastFillRectY;
        }

        public double getLastFillRectWidth() {
            return lastFillRectWidth;
        }

        public double getLastFillRectHeight() {
            return lastFillRectHeight;
        }

        public double getLastStrokeRectX() {
            return lastStrokeRectX;
        }

        public double getLastStrokeRectY() {
            return lastStrokeRectY;
        }

        public double getLastStrokeRectWidth() {
            return lastStrokeRectWidth;
        }

        public double getLastStrokeRectHeight() {
            return lastStrokeRectHeight;
        }

        public double getLastFillOvalX() {
            return lastFillOvalX;
        }

        public double getLastFillOvalY() {
            return lastFillOvalY;
        }

        public double getLastFillOvalWidth() {
            return lastFillOvalWidth;
        }

        public double getLastFillOvalHeight() {
            return lastFillOvalHeight;
        }

        public double getLastStrokeOvalX() {
            return lastStrokeOvalX;
        }

        public double getLastStrokeOvalY() {
            return lastStrokeOvalY;
        }

        public double getLastStrokeOvalWidth() {
            return lastStrokeOvalWidth;
        }

        public double getLastStrokeOvalHeight() {
            return lastStrokeOvalHeight;
        }

        public Image getLastDrawImage() {
            return lastDrawImage;
        }

        public double getLastDrawImageX() {
            return lastDrawImageX;
        }

        public double getLastDrawImageY() {
            return lastDrawImageY;
        }

        public Double getLastDrawImageWidth() {
            return lastDrawImageWidth;
        }

        public Double getLastDrawImageHeight() {
            return lastDrawImageHeight;
        }

        public double getLastClearRectX() {
            return lastClearRectX;
        }

        public double getLastClearRectY() {
            return lastClearRectY;
        }

        public double getLastClearRectWidth() {
            return lastClearRectWidth;
        }

        public double getLastClearRectHeight() {
            return lastClearRectHeight;
        }
    }
}
