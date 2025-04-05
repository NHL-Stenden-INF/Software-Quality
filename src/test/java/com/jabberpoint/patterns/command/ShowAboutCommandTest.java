package com.jabberpoint.patterns.command;

import com.jabberpoint.ui.view.TestAboutBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowAboutCommandTest {

    @BeforeEach
    void setUp() {
        TestAboutBox.reset();
    }

    @AfterEach
    void tearDown() {
        TestAboutBox.reset();
    }

    @Test
    void execute_ShouldCallAboutBoxShow() {
        TestShowAboutCommand command = new TestShowAboutCommand();

        command.execute();

        assertTrue(TestAboutBox.wasShowCalled(), "AboutBox.show() should have been called");
    }
} 