package com.example.pregao2.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class SceneSwitcher {
    private Stage stage;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
    }

    public void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.resizableProperty().setValue(false);

            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
