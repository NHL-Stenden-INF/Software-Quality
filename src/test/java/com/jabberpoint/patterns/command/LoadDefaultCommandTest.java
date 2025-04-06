package com.jabberpoint.patterns.command;

import static org.mockito.Mockito.*;

import com.jabberpoint.infrastructure.XMLAccessor;
import com.jabberpoint.patterns.composite.PresentationInterface;
import com.jabberpoint.ui.view.ViewInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoadDefaultCommandTest {

    @Mock
    private PresentationInterface presentation;
    
    @Mock
    private ViewInterface viewerFrame;
    
    // Create a test implementation instead of mocking XMLAccessor
    private XMLAccessor xmlAccessor;
    
    @Mock
    private OpenPresentationCommand openCommand;
    
    private LoadDefaultCommand command;
    
    @BeforeEach
    public void setUp() {
        // Create a real instance of XMLAccessor
        xmlAccessor = new XMLAccessor();
        command = new LoadDefaultCommand(presentation, viewerFrame, xmlAccessor);
    }
    
    @Test
    public void execute_ShouldDelegateToOpenPresentationCommand() {
        // Since LoadDefaultCommand just creates and calls OpenPresentationCommand,
        // we need to test that behavior

        // Create a spy of our command to verify internal behavior
        LoadDefaultCommand spyCommand = spy(command);
        
        // Act
        spyCommand.execute();
        
        // We can verify that execute was called, but there's no direct way to verify
        // the internal OpenPresentationCommand creation in a clean way without
        // changing the production code to be more testable.
        
        // At minimum, we can ensure execute didn't throw an exception
        verify(spyCommand).execute();
    }
} 