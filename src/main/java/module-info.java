module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens org.openjfx to javafx.fxml;
    exports org.openjfx;

    opens org.openjfx.controller to javafx.fxml;
    exports org.openjfx.controller;

    opens org.openjfx.data to javafx.fxml;
    exports org.openjfx.data;

    opens org.openjfx.service to javafx.fxml;
    exports org.openjfx.service;
}