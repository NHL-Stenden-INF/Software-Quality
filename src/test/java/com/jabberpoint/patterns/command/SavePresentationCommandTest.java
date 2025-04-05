package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class SavePresentationCommandTest {

    @Mock
    private PresentationInterface presentation;

    @Mock
    private XMLAccessor xmlAccessor;

    private SavePresentationCommand command;
    private static final String FILENAME = "test.xml";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new SavePresentationCommand(presentation, xmlAccessor, FILENAME);
    }

    @Test
    void execute_ShouldCallSavePresentation() throws IOException {
        // Act
        command.execute();

        // Assert
        verify(xmlAccessor).savePresentation(presentation, FILENAME);
    }

    @Test
    void execute_ShouldHandleIOException() throws IOException {
        // Arrange
        doThrow(new IOException("Test exception")).when(xmlAccessor).savePresentation(presentation, FILENAME);

        // Act
        command.execute();

        // Assert
        verify(xmlAccessor).savePresentation(presentation, FILENAME);
        // The command should handle the exception and not propagate it
    }
} 