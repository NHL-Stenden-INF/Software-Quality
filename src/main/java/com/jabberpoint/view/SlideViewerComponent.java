package com.jabberpoint.view;

import com.jabberpoint.entities.Style;
import com.jabberpoint.model.BitmapItem;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.SlideItem;
import com.jabberpoint.model.TextItem;
import com.jabberpoint.presentation.StyleManager;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

public class SlideViewerComponent extends VBox {

    private Presentation presentation;

    public SlideViewerComponent(Presentation presentation) {
        this.presentation = presentation;
        updateView();
    }

    public void updateView() {
        this.getChildren().clear();
        Slide slide = presentation.getCurrentSlide();
        if (slide != null) {
            // Set background image if available
            if (slide.getBackgroundImage() != null && !slide.getBackgroundImage().isEmpty()) {
                Image bgImage = new Image(slide.getBackgroundImage());
                BackgroundImage bg = new BackgroundImage(bgImage,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, false));
                this.setBackground(new Background(bg));
            } else {
                this.setBackground(null);
            }

            // Get current style from yer StyleManager
            Style style = StyleManager.getCurrentStyle();
            // Build CSS strings based on yer style object.
            // Adjust these method calls if your Style class differs.
            String titleStyle = "-fx-font-size: " + style.getFontSize().getTitleSize() + "px; " +
                                "-fx-font-family: '" + style.getFontName().name() + "'; " +
                                "-fx-text-fill: " + style.getFontColor().toCss() + "; " +
                                "-fx-font-weight: bold;";
            String textStyle = "-fx-font-size: " + style.getFontSize().getTextSize() + "px; " +
                               "-fx-font-family: '" + style.getFontName().name() + "'; " +
                               "-fx-text-fill: " + style.getFontColor().toCss() + ";";

            // Create and style the slide title
            Label titleLabel = new Label(slide.getTitle());
            titleLabel.setStyle(titleStyle);
            titleLabel.setPadding(new Insets(10, 10, 10, 10));
            this.getChildren().add(titleLabel);

            // Render each slide item
            for (SlideItem item : slide.getItems()) {
                if (item instanceof TextItem) {
                    Label textLabel = new Label(((TextItem) item).getText());
                    textLabel.setStyle(textStyle);
                    textLabel.setPadding(new Insets(5, 5, 5, 15));
                    this.getChildren().add(textLabel);
                } else if (item instanceof BitmapItem) {
                    ImageView imageView = new ImageView(new Image(((BitmapItem) item).getImagePath()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(400);
                    this.getChildren().add(imageView);
                }
            }
        } else {
            this.getChildren().add(new Label("No slide available!"));
        }
    }
}
