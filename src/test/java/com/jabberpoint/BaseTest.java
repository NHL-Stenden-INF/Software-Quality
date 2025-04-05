package com.jabberpoint;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
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
                // Initialize JavaFX
                if (!Platform.isFxApplicationThread()) {
                    CountDownLatch latch = new CountDownLatch(1);
                    new Thread(() -> {
                        try {
                            // Set headless mode for CI environments
                            System.setProperty("java.awt.headless", "true");
                            System.setProperty("javafx.animation.framerate", "60");
                            System.setProperty("javafx.animation.framerate.max", "60");
                            
                            // Initialize JavaFX
                            Platform.startup(() -> {});
                            latch.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                            latch.countDown();
                        }
                    }).start();
                    
                    // Wait for initialization with a timeout
                    if (!latch.await(10, TimeUnit.SECONDS)) {
                        throw new RuntimeException("JavaFX initialization timed out");
                    }
                }
                initialized = true;
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to initialize JavaFX", e);
            }
        }
    }
} 