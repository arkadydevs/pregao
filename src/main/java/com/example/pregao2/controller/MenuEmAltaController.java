package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.example.pregao2.model.ObjectSaveManager;

public class MenuEmAltaController {

    @FXML
    private Button altaBotao;

    @FXML
    private Button botaoProcurar;

    @FXML
    private Button carteirasMenuBotao;

    @FXML
    private ComboBox comboBoxAcoesTipo;

    @FXML
    private ComboBox comboBoxOrdernar;

    @FXML
    private Button historicoMenuBotao;

    @FXML
    private Button negociarMenuBotao;

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
        setComboBoxAcoesTipo();
    }

    public void setComboBoxAcoesTipo(){
        ObservableList<String> tipos = FXCollections.observableArrayList("fii", "preferencial", "ordinaria");
        comboBoxAcoesTipo.setItems(tipos);
    }

    public void OnActionProcurar(ActionEvent event){}
    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");}

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
