package com.jabberpoint.patterns.command;

import javafx.stage.Stage;

public class ExitCommand implements Command {
    private final Stage stage;

    public ExitCommand(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    @Override
    public void execute() {
        stage.close();
    }
} 