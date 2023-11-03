package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ObjectSaveManager;

public class MenuHistoricoController {

    @FXML
    private Button carteirasMenuBotao;

    @FXML
    private Button historicoMenuBotao;

    @FXML
    private Button negociarMenuBotao;
    @FXML
    private Button altaBotao;

    @FXML
    private Label nomeUserLabel;

    @FXML
    private Label saldoUserLabel;
    private String nome;
    private double saldo;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);

    @FXML
    public void initialize() {
        userInfo();
    }

    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/menuPrincipal.fxml");}

    public void userInfo(){
        ObjectSaveManager obj = new ObjectSaveManager();
        nome = (String) obj.getObject("NOME");
        String saldoStr = (String) obj.getObject("SALDO");
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            saldo = 0.0;
        }

        nomeUserLabel.setText(nome);
        saldoUserLabel.setText(String.valueOf(saldo));
    }
}