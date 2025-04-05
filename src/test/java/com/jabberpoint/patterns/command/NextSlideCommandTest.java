package com.jabberpoint.patterns.command;

import com.jabberpoint.patterns.composite.PresentationInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class NextSlideCommandTest {

    @Mock
    private PresentationInterface presentation;

    private NextSlideCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new NextSlideCommand(presentation);
    }

    @Test
    void execute_ShouldCallNextSlideOnPresentation() {
        command.execute();

        verify(presentation).nextSlide();
    }
} 