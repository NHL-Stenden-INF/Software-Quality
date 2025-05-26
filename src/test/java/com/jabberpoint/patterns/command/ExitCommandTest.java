package com.jabberpoint.patterns.command;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.security.Permission;
import static org.mockito.Mockito.*;
import javafx.stage.Stage;

class ExitCommandTest {
    private static class ExitException extends SecurityException {
        private final int status;
        
        ExitException(int status) {
            super("System.exit(" + status + ") was called");
            this.status = status;
        }
        
        public int getStatus() {
            return status;
        }
    }
    
    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            // Allow all other permissions
        }
        
        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
    
    private SecurityManager originalManager;
    
    @BeforeEach
    void setUp() {
        originalManager = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager());
    }
    
    @AfterEach
    void tearDown() {
        System.setSecurityManager(originalManager);
    }

    @Test
    void execute_ShouldCallStageClose() {
        Stage mockStage = mock(Stage.class);
        ExitCommand command = new ExitCommand(mockStage);
        command.execute();
        verify(mockStage, times(1)).close();
    }
} 