package com.jabberpoint;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Base test class for all test classes.
 * This class provides common functionality for all test classes.
 */
public class BaseTest implements BeforeAllCallback {
    private static boolean initialized = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!initialized) {
            new JFXPanel(); // Initialize JavaFX
            initialized = true;
        }
    }
} 