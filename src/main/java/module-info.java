module com.example.pregao2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.management;
    requires caelum.stella.core;

    opens com.example.pregao2 to javafx.fxml;
    opens com.example.pregao2.controller to javafx.fxml;

    opens com.example.pregao2.entidades;
    exports com.example.pregao2;
}
