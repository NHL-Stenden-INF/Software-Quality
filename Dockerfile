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

# Download and extract JavaFX SDK
RUN wget -O javafx-sdk.zip https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_linux-x64_bin-sdk.zip && \
    unzip javafx-sdk.zip -d /usr/share/javafx && \
    rm javafx-sdk.zip

# Copy project files into the container
COPY . .

# Ensure JavaFX test project exists if missing
RUN if [ ! -f src/main/java/com/example/MainApp.java ]; then \
    mkdir -p src/main/java/com/example && \
    echo 'package com.example;' > src/main/java/com/example/MainApp.java && \
    echo 'import javafx.application.Application;' >> src/main/java/com/example/MainApp.java && \
    echo 'import javafx.scene.Scene;' >> src/main/java/com/example/MainApp.java && \
    echo 'import javafx.scene.control.Label;' >> src/main/java/com/example/MainApp.java && \
    echo 'import javafx.stage.Stage;' >> src/main/java/com/example/MainApp.java && \
    echo 'public class MainApp extends Application {' >> src/main/java/com/example/MainApp.java && \
    echo '    @Override' >> src/main/java/com/example/MainApp.java && \
    echo '    public void start(Stage stage) {' >> src/main/java/com/example/MainApp.java && \
    echo '        Label label = new Label(\"Hello, JavaFX in Docker!\");' >> src/main/java/com/example/MainApp.java && \
    echo '        Scene scene = new Scene(label, 400, 200);' >> src/main/java/com/example/MainApp.java && \
    echo '        stage.setTitle(\"JavaFX App\");' >> src/main/java/com/example/MainApp.java && \
    echo '        stage.setScene(scene);' >> src/main/java/com/example/MainApp.java && \
    echo '        stage.show();' >> src/main/java/com/example/MainApp.java && \
    echo '    }' >> src/main/java/com/example/MainApp.java && \
    echo '    public static void main(String[] args) { launch(args); }' >> src/main/java/com/example/MainApp.java && \
    echo '}' >> src/main/java/com/example/MainApp.java; \
fi

# At runtime, ensure the app is built and launch it with the correct DISPLAY settings
CMD ["bash", "-c", "\
echo 'Checking DISPLAY variable...' && \
if [ -z \"$DISPLAY\" ]; then export DISPLAY=host.docker.internal:0.0; fi && \
echo 'Using DISPLAY=' $DISPLAY && \
if [ ! -f target/app.jar ]; then echo 'Building JavaFX app...'; mvn clean package; fi && \
echo 'Launching JavaFX...' && \
java --module-path /usr/share/javafx/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -jar target/app.jar"]
