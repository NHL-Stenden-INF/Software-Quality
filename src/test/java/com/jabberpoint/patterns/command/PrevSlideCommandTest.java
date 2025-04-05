package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.PresentationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class PrevSlideCommandTest {

    @Mock
    private PresentationInterface presentation;

    private PrevSlideCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new PrevSlideCommand(presentation);
    }

    @Test
    void execute_ShouldCallPreviousSlideOnPresentation() {
        // Act
        command.execute();

        // Assert
        verify(presentation).previousSlide();
    }
} 