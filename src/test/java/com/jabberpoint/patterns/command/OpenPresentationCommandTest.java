package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class OpenPresentationCommandTest {

    @Mock
    private PresentationInterface presentation;

    @Mock
    private XMLAccessor xmlAccessor;

    private TestOpenPresentationCommand command;
    private static final String FILENAME = "test.xml";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new TestOpenPresentationCommand(presentation, xmlAccessor, FILENAME);
        TestOpenPresentationCommand.reset();
    }

    @Test
    void execute_ShouldLoadPresentation() throws IOException {
        command.execute();

        verify(presentation).getSlides();
        verify(presentation).setTitle("");
        verify(xmlAccessor).loadPresentation(presentation, FILENAME);
        assert TestOpenPresentationCommand.wasLoadCalled();
    }

    @Test
    void execute_ShouldHandleIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(xmlAccessor).loadPresentation(presentation, FILENAME);

        command.execute();

        verify(presentation).getSlides();
        verify(presentation).setTitle("");
        verify(xmlAccessor).loadPresentation(presentation, FILENAME);
    }
} 