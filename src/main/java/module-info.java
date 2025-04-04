module jabberpoint {
    requires javafx.fxml;
    requires java.xml;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    
    exports com.jabberpoint;
    exports com.jabberpoint.entities;  
    exports com.jabberpoint.model;
    exports com.jabberpoint.view;
    exports com.jabberpoint.controller;
    exports com.jabberpoint.factory;
    exports com.jabberpoint.infrastructure;

    opens com.jabberpoint to javafx.fxml;
}
