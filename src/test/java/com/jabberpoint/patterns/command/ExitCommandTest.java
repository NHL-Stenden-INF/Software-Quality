package com.jabberpoint.patterns.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExitCommandTest {

    @BeforeEach
    void setUp() {
        TestExitCommand.reset();
    }

    @AfterEach
    void tearDown() {
        TestExitCommand.reset();
    }

    @Test
    void execute_ShouldSetExitCalledFlag() {
        // Arrange
        TestExitCommand command = new TestExitCommand();

        // Act
        command.execute();

        // Assert
        assertTrue(TestExitCommand.wasExitCalled(), "Exit command should have been executed");
    }
} 