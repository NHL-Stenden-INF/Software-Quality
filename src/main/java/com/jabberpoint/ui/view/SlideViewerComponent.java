package com.jabberpoint.ui.view;

import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.patterns.composite.Slide;
import com.jabberpoint.patterns.composite.SlideItem;
import com.jabberpoint.patterns.composite.TitleItem;
import com.jabberpoint.patterns.composite.SubtitleItem;
import com.jabberpoint.patterns.composite.BodyTextItem;
import com.jabberpoint.patterns.composite.BulletPointItem;
import com.jabberpoint.patterns.composite.BitmapItem;
import com.jabberpoint.style.Style;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;

import java.io.File;

public class SlideViewerComponent extends StackPane {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int MARGIN = 50;
    private static final int TITLE_HEIGHT = 50;
    private static final int SUBTITLE_HEIGHT = 40;
    private static final int BODY_HEIGHT = 35;
    private static final int BULLET_HEIGHT = 30;
    private static final int BULLET_GROUP_SPACING = 20;
    private static final int INDENT = 20;

    private PresentationInterface presentation;
    private Style defaultStyle;
    
    // Base font sizes for scaling
    private static final double BASE_TITLE_FONT_SIZE = 40.0;
    private static final double BASE_BODY_FONT_SIZE = 20.0;
    
    // The canvas that will be used for drawing
    private Canvas canvas;

    public SlideViewerComponent() {
        setupResponsiveCanvas();
    }

    public SlideViewerComponent(PresentationInterface presentation, Style style) {
        this.presentation = presentation;
        this.defaultStyle = style;
        setupResponsiveCanvas();
    }
    
    private void setupResponsiveCanvas() {
        // Create the canvas
        canvas = new Canvas(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        // Add the canvas to this StackPane
        getChildren().add(canvas);
        
        // Set preferred size
        setPrefWidth(DEFAULT_WIDTH);
        setPrefHeight(DEFAULT_HEIGHT);
        
        // Set minimum size
        setMinWidth(100);
        setMinHeight(100);
        
        // Bind the canvas size to the StackPane size
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        
        // Add listeners for width and height changes
        widthProperty().addListener((obs, oldVal, newVal) -> draw());
        heightProperty().addListener((obs, oldVal, newVal) -> draw());
        
        // Set the canvas to be resizable
        canvas.setOnMouseClicked(event -> requestFocus());
    }
    
    /**
     * Calculate the scaling factor based on the current canvas size
     * @return The scaling factor to apply to fonts and spacing
     */
    private double calculateScaleFactor() {
        double widthScale = canvas.getWidth() / DEFAULT_WIDTH;
        double heightScale = canvas.getHeight() / DEFAULT_HEIGHT;
        return Math.min(widthScale, heightScale);
    }
    
    /**
     * Create a scaled font based on the base font size and current scale factor
     * @param baseSize The base font size
     * @param fontName The font name
     * @return A scaled font
     */
    private Font createScaledFont(double baseSize, String fontName) {
        double scaleFactor = calculateScaleFactor();
        double scaledSize = baseSize * scaleFactor;
        return Font.font(fontName, scaledSize);
    }

    public void update(PresentationInterface presentation) {
        this.presentation = presentation;
        draw();
    }

    private Image loadImage(String path) {
        try {
            if (path.startsWith("file:")) {
                // Remove the file: prefix and create a proper URI
                String filePath = path.substring(5);
                File file = new File(filePath);
                if (file.exists()) {
                    return new Image(file.toURI().toString());
                }
            } else {
                // Try loading as a regular file path
                File file = new File(path);
                if (file.exists()) {
                    return new Image(file.toURI().toString());
                }
            }
            // If the file doesn't exist, try loading from classpath
            return new Image(getClass().getResourceAsStream("/" + path));
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        gc.clearRect(0, 0, width, height);

        if (presentation == null || presentation.getCurrentSlide() == null) {
            return;
        }

        Slide slide = presentation.getCurrentSlide();
        
        // Calculate scaling factors
        double scaleFactor = calculateScaleFactor();
        int scaledMargin = (int)(MARGIN * scaleFactor);
        int scaledTitleHeight = (int)(TITLE_HEIGHT * scaleFactor);
        int scaledSubtitleHeight = (int)(SUBTITLE_HEIGHT * scaleFactor);
        int scaledBodyHeight = (int)(BODY_HEIGHT * scaleFactor);
        int scaledBulletHeight = (int)(BULLET_HEIGHT * scaleFactor);
        int scaledBulletGroupSpacing = (int)(BULLET_GROUP_SPACING * scaleFactor);
        int scaledIndent = (int)(INDENT * scaleFactor);

        // Draw background
        String backgroundPath = slide.getBackground();
        if (backgroundPath != null && !backgroundPath.isEmpty()) {
            Image backgroundImage = loadImage(backgroundPath);
            if (backgroundImage != null) {
                gc.drawImage(backgroundImage, 0, 0, width, height);
            } else {
                // If background image fails to load, use a solid color
                gc.setFill(javafx.scene.paint.Color.BLACK);
                gc.fillRect(0, 0, width, height);
            }
        } else {
            // Default background
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillRect(0, 0, width, height);
        }

        int y = scaledMargin;
        boolean inBulletGroup = false;

        // Draw items in their original order
        for (SlideItem item : slide.getItems()) {
            Style itemStyle = item.getStyle() != null ? item.getStyle() : defaultStyle;
            
            if (item instanceof TitleItem) {
                // Add extra spacing if we were in a bullet group
                if (inBulletGroup) {
                    y += scaledBulletGroupSpacing;
                    inBulletGroup = false;
                }
                
                // Create a scaled font for the title
                Font scaledTitleFont = createScaledFont(BASE_TITLE_FONT_SIZE, itemStyle.getTitleFontName().name());
                gc.setFont(scaledTitleFont);
                gc.setFill(itemStyle.getTitleColor().getColor());
                gc.setTextAlign(TextAlignment.CENTER);
                
                // Center the title horizontally and vertically
                double titleX = width / 2;
                double titleY = height / 2; // True center of the slide
                
                // Draw the title centered
                gc.fillText(item.getText(), titleX, titleY);
                y = (int)titleY + scaledTitleHeight + 20; // Update y position after the title
            } else if (item instanceof SubtitleItem) {
                // Add extra spacing if we were in a bullet group
                if (inBulletGroup) {
                    y += scaledBulletGroupSpacing;
                    inBulletGroup = false;
                }
                
                // Create a scaled font for the subtitle
                Font scaledSubtitleFont = createScaledFont(BASE_TITLE_FONT_SIZE, itemStyle.getTitleFontName().name());
                gc.setFont(scaledSubtitleFont);
                gc.setFill(itemStyle.getTitleColor().getColor());
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(item.getText(), scaledMargin, y + scaledSubtitleHeight);
                y += scaledSubtitleHeight + 10;
            } else if (item instanceof BodyTextItem) {
                // Add extra spacing if we were in a bullet group
                if (inBulletGroup) {
                    y += scaledBulletGroupSpacing;
                    inBulletGroup = false;
                }
                
                // Create a scaled font for the body text
                Font scaledBodyFont = createScaledFont(BASE_BODY_FONT_SIZE, itemStyle.getBodyFontName().name());
                gc.setFont(scaledBodyFont);
                gc.setFill(itemStyle.getBodyColor().getColor());
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(item.getText(), scaledMargin, y + scaledBodyHeight);
                y += scaledBodyHeight + 10;
            } else if (item instanceof BulletPointItem) {
                if (!inBulletGroup) {
                    // First bullet point in a group
                    inBulletGroup = true;
                    y += 10; // Add extra space before bullet points
                }
                
                // Create a scaled font for the bullet points
                Font scaledBulletFont = createScaledFont(BASE_BODY_FONT_SIZE, itemStyle.getBodyFontName().name());
                gc.setFont(scaledBulletFont);
                gc.setFill(itemStyle.getBodyColor().getColor());
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText("• " + item.getText(), scaledMargin + scaledIndent, y + scaledBulletHeight);
                y += scaledBulletHeight;
                
                // Check if this is the last item
                boolean isLastItem = slide.getItems().indexOf(item) == slide.getItems().size() - 1;
                if (isLastItem) {
                    y += scaledBulletGroupSpacing;
                    inBulletGroup = false;
                }
            } else if (item instanceof BitmapItem) {
                // Add extra spacing if we were in a bullet group
                if (inBulletGroup) {
                    y += scaledBulletGroupSpacing;
                    inBulletGroup = false;
                }
                
                Image image = loadImage(item.getText());
                if (image != null) {
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    double scale = Math.min(
                        (width - 2 * scaledMargin) / imageWidth,
                        (height - y - scaledMargin) / imageHeight
                    );
                    double scaledWidth = imageWidth * scale;
                    double scaledHeight = imageHeight * scale;
                    double x = (width - scaledWidth) / 2;
                    gc.drawImage(image, x, y, scaledWidth, scaledHeight);
                    y += scaledHeight + scaledMargin;
                } else {
                    // If image fails to load, skip it
                    y += scaledBulletHeight;
                }
            }
        }
    }
}
