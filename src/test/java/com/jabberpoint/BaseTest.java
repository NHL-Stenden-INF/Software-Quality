package com.jabberpoint;

import javafx.application.Platform;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Base test class for all test classes.
 * This class provides common functionality for all test classes.
 */
@ExtendWith(BaseTest.class)
public class BaseTest implements BeforeAllCallback {
    private static boolean initialized = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!initialized) {
            try {
                // Set headless mode and other JavaFX properties
                System.setProperty("java.awt.headless", "true");
                System.setProperty("javafx.animation.framerate", "60");
                System.setProperty("javafx.animation.framerate.max", "60");
                System.setProperty("prism.order", "sw");
                System.setProperty("prism.text", "t2k");
                System.setProperty("javafx.verbose", "true");

                // Initialize JavaFX in a separate thread
                CountDownLatch latch = new CountDownLatch(1);
                Thread fxThread = new Thread(() -> {
                    try {
                        // Simple initialization without JFXPanel
                        Platform.startup(() -> {});
                        latch.countDown();
                    } catch (Exception e) {
                        System.err.println("Error initializing JavaFX: " + e.getMessage());
                        e.printStackTrace();
                        latch.countDown();
                    }
                });
                fxThread.setDaemon(true);
                fxThread.start();

                // Wait for initialization with timeout
                if (!latch.await(15, TimeUnit.SECONDS)) {
                    System.err.println("Warning: JavaFX initialization timed out");
                }
                
                initialized = true;
            } catch (InterruptedException e) {
                System.err.println("Error during JavaFX initialization: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
} 