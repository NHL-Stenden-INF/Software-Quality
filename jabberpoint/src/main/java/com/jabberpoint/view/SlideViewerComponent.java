package com.jabberpoint.view;

import com.jabberpoint.model.BitmapItem;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.SlideItem;
import com.jabberpoint.model.TextItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            Label titleLabel = new Label(slide.getTitle());
            this.getChildren().add(titleLabel);
            for (SlideItem item : slide.getItems()) {
                if (item instanceof TextItem) {
                    Label textLabel = new Label(((TextItem) item).getText());
                    this.getChildren().add(textLabel);
                } else if (item instanceof BitmapItem) {
                    ImageView imageView = new ImageView(new Image(((BitmapItem) item).getImagePath()));
                    this.getChildren().add(imageView);
                }
            }
        } else {
            this.getChildren().add(new Label("No slide available, sorry!"));
        }
    }
}
