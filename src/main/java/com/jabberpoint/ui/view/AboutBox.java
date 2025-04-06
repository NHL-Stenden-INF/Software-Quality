package com.jabberpoint.ui.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AboutBox {

    public static void show() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About JabberPoint");
        alert.setHeaderText("JabberPoint Presentation Viewer");
        alert.setContentText("Version 1.0\n\n" +
                "A simple presentation viewer that supports text and images.\n" +
                "Created as part of a software quality course.");
        alert.showAndWait();
    }
}
