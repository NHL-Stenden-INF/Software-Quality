module jabberpoint {
    requires javafx.fxml;
    requires java.xml;
    requires java.logging;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    
    exports com.jabberpoint;
    exports com.jabberpoint.ui.view;
    exports com.jabberpoint.ui.controller;
    exports com.jabberpoint.patterns.factory;
    exports com.jabberpoint.infrastructure;
    exports com.jabberpoint.patterns.composite;
    exports com.jabberpoint.style;
    exports com.jabberpoint.patterns.observer;

    opens com.jabberpoint to javafx.fxml;
}
