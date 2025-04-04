package com.jabberpoint;

import javafx.application.Platform;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class BaseTest {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    static {
        if (!initialized.get()) {
            try {
                // Initialize JavaFX
                CountDownLatch latch = new CountDownLatch(1);
                Platform.startup(() -> latch.countDown());
                latch.await();
                Platform.setImplicitExit(false);
                initialized.set(true);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize JavaFX", e);
            }
        }
    }
} 