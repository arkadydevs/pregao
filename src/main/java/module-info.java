module com.example.pregao2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.management;
    requires caelum.stella.core;

    opens com.example.pregao2 to javafx.fxml;
    opens com.example.pregao2.controller to javafx.fxml;

    exports com.example.pregao2; // Exporta o pacote raiz para outros módulos, incluindo javafx.graphics
}
