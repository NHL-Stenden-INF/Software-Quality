package com.jabberpoint.ui.view;

/**
 * Test-specific version of AboutBox that doesn't use JavaFX components.
 * This allows us to test the ShowAboutCommand without initializing the JavaFX toolkit.
 */
public class TestAboutBox {
    private static boolean showCalled = false;

    public static void show() {
        showCalled = true;
    }

    public static boolean wasShowCalled() {
        return showCalled;
    }

    public static void reset() {
        showCalled = false;
    }
} 