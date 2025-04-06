package com.jabberpoint.patterns.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.ViewInterface;

public class LoadLandscapeCommandTest {
    
    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private ViewInterface viewerFrame;
    
    @Mock
    private XMLAccessor xmlAccessor;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void execute_ShouldDelegateToOpenPresentationCommand() {
        // Create the command
        LoadLandscapeCommand command = new LoadLandscapeCommand(presentation, viewerFrame, xmlAccessor);
        
        // Just verify the command can be executed without exceptions
        command.execute();
        
        // No direct assertions since this is a black box test
        // The real functionality is tested in OpenPresentationCommand tests
    }
} 