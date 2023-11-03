package com.example.pregao2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    public static Stage primaryStage; // Define the primaryStage as a static variable

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Set the primaryStage
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/pregaoInicialScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Pregão ");
        stage.sizeToScene();
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }
}