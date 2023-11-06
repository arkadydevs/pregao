package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Carteira;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.scene.control.TextField;

public class MenuCarteirasController {

    @FXML
    private Button addCarteira;
    @FXML
    private TextField addCarteiraTextField;
    @FXML
    private Button altaBotao;
    @FXML
    private Button carteirasMenuBotao;
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
    private String id;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);


    @FXML
    public void initialize() {
        userInfo();

    }


    @FXML
    public void OnActionAddCarteira(ActionEvent event){
        Carteira carteira = new Carteira();
        carteira.setIdInvestidor(id);
        carteira.setNomeCarteira(addCarteiraTextField.getText());
        carteira.insert(carteira);
    }
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
        id = (String) obj.getObject("ID");
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
