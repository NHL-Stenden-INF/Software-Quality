module com.jabberpoint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.graphics;
    requires java.logging;
    requires java.xml;
    
    exports com.jabberpoint;
    exports com.jabberpoint.ui.view;
    exports com.jabberpoint.ui.controller;
    exports com.jabberpoint.patterns.command;
    exports com.jabberpoint.patterns.composite;
    exports com.jabberpoint.patterns.observer;
    exports com.jabberpoint.patterns.factory;
    exports com.jabberpoint.infrastructure;
    exports com.jabberpoint.style;
    
    // Open packages for both JavaFX and reflection/testing
    opens com.jabberpoint;
    opens com.jabberpoint.ui.view;
    opens com.jabberpoint.ui.controller;
    opens com.jabberpoint.patterns.command;
    opens com.jabberpoint.patterns.composite;
    opens com.jabberpoint.patterns.observer;
    opens com.jabberpoint.patterns.factory;
    opens com.jabberpoint.infrastructure;
    opens com.jabberpoint.style;
}
