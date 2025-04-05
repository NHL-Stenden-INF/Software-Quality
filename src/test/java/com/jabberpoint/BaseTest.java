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

                // Initialize JavaFX in a separate thread
                Thread fxThread = new Thread(() -> {
                    try {
                        System.out.println("Starting JavaFX initialization thread...");
                        if (!Platform.isFxApplicationThread()) {
                            System.out.println("Not on JavaFX thread, starting platform...");
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
                System.out.println("Waiting for JavaFX initialization...");
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
            System.out.println("Not on JavaFX thread, using Platform.runLater");
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    System.out.println("Running action on JavaFX thread");
                    action.run();
                } catch (Exception e) {
                    System.err.println("Error in runAndWait action: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    System.out.println("Action completed, counting down latch");
                    latch.countDown();
                }
            });
            try {
                System.out.println("Waiting for action completion...");
                if (!latch.await(5, TimeUnit.SECONDS)) {
                    System.err.println("Warning: Action execution timed out");
                }
                System.out.println("Action execution completed");
            } catch (InterruptedException e) {
                System.err.println("Error waiting for action execution: " + e.getMessage());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
} 