package com.jabberpoint.patterns.command;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;

import com.jabberpoint.ui.view.AboutBox;
import com.jabberpoint.ui.view.TestAboutBox;

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
    
    @Test
    void execute_RealShowAboutCommand_ShouldCallAboutBoxShow() {
        ShowAboutCommand command = new ShowAboutCommand();
        
        try (MockedStatic<AboutBox> mockedAboutBox = Mockito.mockStatic(AboutBox.class)) {
            // Execute the command
            command.execute();
            
            // Verify AboutBox.show was called
            mockedAboutBox.verify(AboutBox::show, times(1));
        }
    }
} 