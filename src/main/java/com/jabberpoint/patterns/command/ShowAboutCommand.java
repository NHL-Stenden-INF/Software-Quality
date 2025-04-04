package com.jabberpoint.patterns.command;

import com.jabberpoint.ui.view.AboutBox;

public class ShowAboutCommand implements Command {
    @Override
    public void execute() {
        AboutBox.show();
    }
} 