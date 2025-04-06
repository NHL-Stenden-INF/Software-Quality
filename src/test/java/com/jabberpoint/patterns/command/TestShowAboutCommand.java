package com.jabberpoint.patterns.command;

import com.jabberpoint.ui.view.TestAboutBox;

/**
 * Test-specific version of ShowAboutCommand that uses TestAboutBox instead of the real AboutBox.
 */
public class TestShowAboutCommand implements Command {
    @Override
    public void execute() {
        TestAboutBox.show();
    }
} 