package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EscolhaJuridica {

    @FXML
    private Button login;

    @FXML
    private Button logon;

    @FXML
    void onActionCriar(ActionEvent event) {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
        sceneSwitcher.switchScene("/fxml/criarAcoes.fxml");
    }

    @FXML
    void OnActionVender(ActionEvent event) {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
        sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");
    }

}