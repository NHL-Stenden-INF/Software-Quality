package com.jabberpoint.infrastructure;

import com.jabberpoint.patterns.composite.PresentationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class AccessorTest {

    @Mock
    private PresentationInterface presentation;

    private Accessor accessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create an anonymous subclass of Accessor since it's abstract
        accessor = new Accessor() {};
    }

    @Test
    void loadPresentation_ShouldSetDemoTitle() throws IOException {
        String filename = "test.xml";

        accessor.loadPresentation(presentation, filename);

        verify(presentation).setTitle(Accessor.DEMO_NAME);
    }

    @Test
    void savePresentation_ShouldNotThrowException() throws IOException {
        String filename = "test.xml";

        accessor.savePresentation(presentation, filename);
    }
} 