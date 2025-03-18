# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Install necessary dependencies for JavaFX and GUI support
RUN apt-get update && apt-get install -y --fix-missing \
    openjfx \
    libgtk-3-0 \
    libgl1-mesa-glx \
    libx11-xcb1 \
    libxtst6 \
    libxi6 \
    libxrender1 \
    x11-utils \
    xvfb \
    wget \
    maven \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Ensure JavaFX SDK is available in case openjfx package is missing some dependencies
RUN wget -O javafx-sdk.zip https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_linux-x64_bin-sdk.zip && \
    unzip javafx-sdk.zip -d /usr/share/javafx && \
    rm javafx-sdk.zip

# Copy the project files (assuming Maven structure)
COPY . .

# If no JavaFX app exists, create a basic one
RUN if [ ! -d "src" ]; then \
    mkdir -p src/main/java/com/example && \
    echo 'package com.example; \
    import javafx.application.Application; \
    import javafx.scene.Scene; \
    import javafx.scene.control.Label; \
    import javafx.stage.Stage; \
    public class MainApp extends Application { \
        @Override public void start(Stage stage) { \
            Label label = new Label("Hello, JavaFX in Docker!"); \
            Scene scene = new Scene(label, 400, 200); \
            stage.setTitle("JavaFX App"); \
            stage.setScene(scene); \
            stage.show(); \
        } \
        public static void main(String[] args) { launch(args); } \
    }' > src/main/java/com/example/MainApp.java; \
    fi

# Ensure Maven builds the project
RUN if [ ! -f "target/app.jar" ]; then \
    mvn clean package; \
    fi

# Set up display and execute the application
CMD ["bash", "-c", "Xvfb :99 -screen 0 1024x768x24 & export DISPLAY=:99 && java --module-path /usr/share/javafx/lib --add-modules javafx.controls,javafx.fxml -jar target/app.jar"]
