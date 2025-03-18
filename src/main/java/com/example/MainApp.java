package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, JavaFX in Docker for project Jabberpoint!");
        Scene scene = new Scene(label, 1280, 720);
        stage.setTitle("Jabberpoint");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
