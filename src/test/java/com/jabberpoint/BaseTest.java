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
        if (!initialized.get()) {
            try {
                System.setProperty("java.awt.headless", "true");
                System.setProperty("javafx.animation.framerate", "60");
                System.setProperty("javafx.animation.framerate.max", "60");
                System.setProperty("prism.order", "sw");
                System.setProperty("prism.text", "t2k");
                System.setProperty("javafx.verbose", "true");

                try {
                    Platform.startup(() -> {
                        initLatch.countDown();
                    });
                } catch (Exception e) {
                    System.err.println("Error starting JavaFX platform: " + e.getMessage());
                    e.printStackTrace();
                    initLatch.countDown();
                }
                
                if (!initLatch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    System.err.println("Warning: JavaFX initialization timed out");
                }
                
                initialized.set(true);
            } catch (Exception e) {
                System.err.println("Error during JavaFX initialization: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("JavaFX initialization failed", e);
            }
        }
    }

    protected void runAndWait(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            try {
                Thread actionThread = new Thread(() -> {
                    try {
                        action.run();
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