module com.example.lab10client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab10client to javafx.fxml;
    exports com.example.lab10client;
}