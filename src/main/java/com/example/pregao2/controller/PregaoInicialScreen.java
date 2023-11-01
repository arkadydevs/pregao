package com.example.pregao2.controller;

import com.example.pregao2.MainApp;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class PregaoInicialScreen {
    @FXML
    private Button logon;
    @FXML
    private Button login;


    private SceneSwitcher sceneSwitcher;

    public PregaoInicialScreen() {
        sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    }

    @FXML
    public void onActionLogon(ActionEvent event) {
        System.out.println("Botão empresaInitialButton clicado");
        sceneSwitcher.switchScene("/logonPregao.fxml");
    }

    @FXML
    public void onActionLogin(ActionEvent event) {
        System.out.println("Botão empresaInitialButton clicado");
        sceneSwitcher.switchScene("/loginPregao.fxml");
    }
}

