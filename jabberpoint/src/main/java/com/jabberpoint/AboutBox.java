package com.jabberpoint;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AboutBox {

    public static void display() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About JabberPoint");
        alert.setHeaderText("JabberPoint 2.0");
        alert.setContentText("Made shitty code a lill bit less shitty.");
        alert.showAndWait();
    }
}
