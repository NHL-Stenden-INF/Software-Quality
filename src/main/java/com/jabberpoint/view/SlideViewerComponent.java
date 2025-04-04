package com.jabberpoint.view;

import com.jabberpoint.model.Slide;
import com.jabberpoint.model.BackgroundItem;
import com.jabberpoint.model.SlideItem;
import com.jabberpoint.model.TitleItem;
import com.jabberpoint.model.SubtitleItem;
import com.jabberpoint.model.BodyTextItem;
import com.jabberpoint.model.BulletPointItem;
import com.jabberpoint.model.BitmapItem;
import com.jabberpoint.presentation.StyleManager;
import com.jabberpoint.entities.Style;
import javafx.scene.text.Text;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SlideViewerComponent extends BorderPane {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private Slide currentSlide;

    public SlideViewerComponent() {
        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        this.setCenter(canvas);

        // Resize the canvas to fit the window size when it's resized
        widthProperty().addListener((obs, oldVal, newVal) -> resizeCanvas());
        heightProperty().addListener((obs, oldVal, newVal) -> resizeCanvas());
    }

    public void setSlide(Slide slide) {
        this.currentSlide = slide;
        drawSlide();
    }

    private void drawSlide() {
        if (currentSlide == null) return;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw background first
        currentSlide.getItems().stream()
                .filter(item -> item instanceof BackgroundItem)
                .findFirst()
                .ifPresent(bg -> drawBackground((BackgroundItem) bg));

        // Draw each slide item based on its type
        double yPosition = 100; // starting y for text items

        for (SlideItem item : currentSlide.getItems()) {
            if (item instanceof BitmapItem) {
                // Draw BitmapItem, passing full canvas dimensions if needed
                item.draw(gc, canvas.getWidth(), canvas.getHeight());
            } else if (item instanceof TitleItem) {
                Style itemStyle = (item.getStyle() != null) ? item.getStyle() : StyleManager.getCurrentStyle();
                gc.setFill(itemStyle.getFontColor().getColor());
                gc.setFont(Font.font(itemStyle.getFontName().getName(), FontWeight.BOLD, itemStyle.getFontSize().getSize() * 2));
                Text titleText = new Text(item.getText());
                titleText.setFont(gc.getFont());
                double textWidth = titleText.getLayoutBounds().getWidth();
                double xPosition = (canvas.getWidth() - textWidth) / 2;
                double yCenter = canvas.getHeight() / 2;
                gc.fillText(item.getText(), xPosition, yCenter);
            } else if (item instanceof SubtitleItem) {
                Style itemStyle = (item.getStyle() != null) ? item.getStyle() : StyleManager.getCurrentStyle();
                gc.setFill(itemStyle.getFontColor().getColor());
                gc.setFont(Font.font(itemStyle.getFontName().getName(), FontWeight.BOLD, itemStyle.getFontSize().getSize() * 1.5));
                gc.fillText(item.getText(), 50, yPosition);
                yPosition += 40;
            } else if (item instanceof BodyTextItem) {
                Style itemStyle = (item.getStyle() != null) ? item.getStyle() : StyleManager.getCurrentStyle();
                gc.setFill(itemStyle.getFontColor().getColor());
                gc.setFont(new Font(itemStyle.getFontName().getName(), itemStyle.getFontSize().getSize()));
                gc.fillText(item.getText(), 50, yPosition);
                yPosition += 30;
            } else if (item instanceof BulletPointItem) {
                Style itemStyle = (item.getStyle() != null) ? item.getStyle() : StyleManager.getCurrentStyle();
                gc.setFill(itemStyle.getFontColor().getColor());
                gc.setFont(new Font(itemStyle.getFontName().getName(), itemStyle.getFontSize().getSize()));
                gc.fillText("• " + item.getText(), 70, yPosition);
                yPosition += 25;
            }
        }
    }

    private void resizeCanvas() {
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
        drawSlide();
    }

    private void drawBackground(BackgroundItem bg) {
        Image bgImage = new Image(bg.getImagePath());
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double imageWidth = bgImage.getWidth();
        double imageHeight = bgImage.getHeight();

        double scaleX = canvasWidth / imageWidth;
        double scaleY = canvasHeight / imageHeight;

        double scale = Math.max(scaleX, scaleY);
        double newWidth = imageWidth * scale;
        double newHeight = imageHeight * scale;

        double xOffset = (canvasWidth - newWidth) / 2;
        double yOffset = (canvasHeight - newHeight) / 2;

        gc.drawImage(bgImage, xOffset, yOffset, newWidth, newHeight);
    }
}
