module com.example.light {
    requires javafx.controls;
    requires javafx.fxml;

    //requires org.controlsfx.controls;

    opens com.example.light to javafx.fxml;
    exports com.example.light;
}