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
    private static final int TIMEOUT_SECONDS = 10;

    @Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("BaseTest.beforeAll called for: " + context.getDisplayName());
        if (!initialized.get()) {
            try {
                System.out.println("Initializing JavaFX environment...");
                // Set headless mode and other JavaFX properties
                System.setProperty("java.awt.headless", "true");
                System.setProperty("javafx.animation.framerate", "60");
                System.setProperty("javafx.animation.framerate.max", "60");
                System.setProperty("prism.order", "sw");
                System.setProperty("prism.text", "t2k");
                System.setProperty("javafx.verbose", "true");

                // Initialize JavaFX directly
                System.out.println("Starting JavaFX platform directly...");
                try {
                    Platform.startup(() -> {
                        System.out.println("JavaFX Platform started successfully");
                        initLatch.countDown();
                    });
                } catch (Exception e) {
                    System.err.println("Error starting JavaFX platform: " + e.getMessage());
                    e.printStackTrace();
                    initLatch.countDown();
                }
                
                // Wait for initialization to complete
                if (!initLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    System.err.println("Warning: JavaFX initialization timed out");
                }
                
                initialized.set(true);
                System.out.println("BaseTest initialization completed");
            } catch (Exception e) {
                System.err.println("Error during JavaFX initialization: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("JavaFX initialization failed", e);
            }
        } else {
            System.out.println("BaseTest already initialized");
        }
    }

    protected void runAndWait(Runnable action) {
        System.out.println("runAndWait called from thread: " + Thread.currentThread().getName());
        if (Platform.isFxApplicationThread()) {
            System.out.println("Already on JavaFX thread, running action directly");
            action.run();
        } else {
            System.out.println("Not on JavaFX thread, using direct execution");
            try {
                // Create a new thread to run the action
                Thread actionThread = new Thread(() -> {
                    try {
                        System.out.println("Running action on new thread");
                        action.run();
                        System.out.println("Action completed successfully");
                    } catch (Exception e) {
                        System.err.println("Error in action execution: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
                actionThread.start();
                actionThread.join(TIMEOUT_SECONDS * 1000);
                
                if (actionThread.isAlive()) {
                    System.err.println("Warning: Action execution timed out after " + TIMEOUT_SECONDS + " seconds");
                    actionThread.interrupt();
                }
            } catch (InterruptedException e) {
                System.err.println("Action execution interrupted: " + e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
} 