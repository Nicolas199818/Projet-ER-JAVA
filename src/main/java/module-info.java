module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}