package com.jabberpoint;

import javafx.application.Platform;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base test class for all test classes.
 * This class provides common functionality for all test classes.
 */
@ExtendWith(BaseTest.class)
public class BaseTest implements BeforeAllCallback {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final CountDownLatch initLatch = new CountDownLatch(1);

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!initialized.get()) {
            try {
                // Set headless mode and other JavaFX properties
                System.setProperty("java.awt.headless", "true");
                System.setProperty("javafx.animation.framerate", "60");
                System.setProperty("javafx.animation.framerate.max", "60");
                System.setProperty("prism.order", "sw");
                System.setProperty("prism.text", "t2k");
                System.setProperty("javafx.verbose", "true");

                // Initialize JavaFX in a separate thread
                Thread fxThread = new Thread(() -> {
                    try {
                        if (!Platform.isFxApplicationThread()) {
                            Platform.startup(() -> {
                                System.out.println("JavaFX Platform started successfully");
                                initLatch.countDown();
                            });
                        } else {
                            System.out.println("Already on JavaFX thread");
                            initLatch.countDown();
                        }
                    } catch (Exception e) {
                        System.err.println("Error initializing JavaFX: " + e.getMessage());
                        e.printStackTrace();
                        initLatch.countDown();
                    }
                });
                fxThread.setDaemon(true);
                fxThread.start();

                // Wait for initialization with timeout
                if (!initLatch.await(15, TimeUnit.SECONDS)) {
                    System.err.println("Warning: JavaFX initialization timed out");
                }
                
                initialized.set(true);
                System.out.println("BaseTest initialization completed");
            } catch (InterruptedException e) {
                System.err.println("Error during JavaFX initialization: " + e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    protected void runAndWait(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    action.run();
                } finally {
                    latch.countDown();
                }
            });
            try {
                if (!latch.await(5, TimeUnit.SECONDS)) {
                    System.err.println("Warning: Action execution timed out");
                }
            } catch (InterruptedException e) {
                System.err.println("Error waiting for action execution: " + e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
} 