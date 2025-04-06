module com.jabberpoint {
    requires transitive javafx.fxml;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires java.xml;
    requires java.logging;

    exports com.jabberpoint;
    exports com.jabberpoint.infrastructure;
    exports com.jabberpoint.patterns.composite;
    exports com.jabberpoint.patterns.command;
    exports com.jabberpoint.patterns.factory;
    exports com.jabberpoint.patterns.observer;
    exports com.jabberpoint.style;
    exports com.jabberpoint.ui.controller;
    exports com.jabberpoint.ui.view;

    opens com.jabberpoint to javafx.fxml;
    opens com.jabberpoint.ui.controller to javafx.fxml;
    opens com.jabberpoint.ui.view to javafx.fxml;
}
