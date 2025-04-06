package com.jabberpoint.patterns.command;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.ViewInterface;

public class LoadDefaultCommand implements Command {
    private final PresentationInterface presentation;
    private final ViewInterface viewerFrame;
    private final XMLAccessor accessor;
    
    public LoadDefaultCommand(PresentationInterface presentation, ViewInterface viewerFrame, XMLAccessor accessor) {
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
        this.accessor = accessor;
    }
    
    @Override
    public void execute() {
        Command command = new OpenPresentationCommand(presentation, viewerFrame, accessor, "resources/default.xml");
        command.execute();
    }
} 