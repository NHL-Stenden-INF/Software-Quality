package com.jabberpoint.ui.view;

/**
 * Test-specific version of AboutBox that doesn't use JavaFX components.
 * This allows us to test the ShowAboutCommand without initializing the JavaFX toolkit.
 */
public class TestAboutBox {
    private static boolean showCalled = false;
    private static String title;
    private static String headerText;
    private static String contentText;

    public static void show() {
        showCalled = true;
        title = "About JabberPoint";
        headerText = "JabberPoint Presentation Viewer";
        contentText = "Version 1.0\n\n" +
                "A simple presentation viewer that supports text and images.\n" +
                "Created as part of a software quality course.";
    }

    public static boolean wasShowCalled() {
        return showCalled;
    }

    public static String getTitle() {
        return title;
    }

    public static String getHeaderText() {
        return headerText;
    }

    public static String getContentText() {
        return contentText;
    }

    public static void reset() {
        showCalled = false;
        title = null;
        headerText = null;
        contentText = null;
    }
} 