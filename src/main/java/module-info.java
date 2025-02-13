module com.example.arimaa2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.logging;
    exports tests;
    exports com.example.arimaa2;
    requires junit;
    opens com.example.arimaa2 to javafx.fxml;

}