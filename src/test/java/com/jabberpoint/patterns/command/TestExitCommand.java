package com.jabberpoint.patterns.command;

/**
 * Test-specific version of ExitCommand that doesn't actually call System.exit().
 * This allows us to test the command without terminating the JVM.
 */
public class TestExitCommand implements Command {
    private static boolean exitCalled = false;

    @Override
    public void execute() {
        exitCalled = true;
    }

    public static boolean wasExitCalled() {
        return exitCalled;
    }

    public static void reset() {
        exitCalled = false;
    }
} 