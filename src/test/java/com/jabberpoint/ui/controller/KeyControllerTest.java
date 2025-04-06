package com.jabberpoint.ui.controller;

import com.jabberpoint.patterns.composite.PresentationInterface;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class KeyControllerTest {

    @Mock
    private PresentationInterface presentation;

    private TestKeyController keyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        keyController = new TestKeyController(presentation);
    }

    @Test
    void handleKeyPressed_WithNextSlideKeys_ShouldExecuteNextSlideCommand() {
        KeyEvent[] nextSlideKeys = {
            createKeyEvent(KeyCode.PAGE_DOWN),
            createKeyEvent(KeyCode.RIGHT),
            createKeyEvent(KeyCode.DOWN),
            createKeyEvent(KeyCode.SPACE),
            createKeyEvent(KeyCode.N)
        };

        for (KeyEvent keyEvent : nextSlideKeys) {
            keyController.handleKeyPressed(keyEvent);
            verify(presentation, times(1)).nextSlide();
            reset(presentation);
        }
    }

    @Test
    void handleKeyPressed_WithPreviousSlideKeys_ShouldExecutePreviousSlideCommand() {
        KeyEvent[] previousSlideKeys = {
            createKeyEvent(KeyCode.PAGE_UP),
            createKeyEvent(KeyCode.LEFT),
            createKeyEvent(KeyCode.UP),
            createKeyEvent(KeyCode.P)
        };

        for (KeyEvent keyEvent : previousSlideKeys) {
            keyController.handleKeyPressed(keyEvent);
            verify(presentation, times(1)).previousSlide();
            reset(presentation);
        }
    }

    @Test
    void handleKeyPressed_WithDigitKeys_ShouldExecuteGoToSlideCommand() {
        for (int i = 0; i <= 9; i++) {
            KeyEvent keyEvent = createKeyEvent(KeyCode.valueOf("DIGIT" + i));

            keyController.handleKeyPressed(keyEvent);

            verify(presentation, times(1)).setCurrentSlideIndex(i);
            reset(presentation);
        }
    }

    private KeyEvent createKeyEvent(KeyCode keyCode) {
        return new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            keyCode,
            false,
            false,
            false,
            false
        );
    }
}
