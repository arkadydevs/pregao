package com.example.pregao2.controller;


import com.example.pregao2.MainApp;
import entidades.investidores.InvestidorFisico;
import entidades.investidores.InvestidorJuridico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.ObjectSaveManager;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userTextField;
    @FXML
    private CheckBox checkBoxFisico;
    @FXML
    private CheckBox checkBoxJuridico;
    @FXML
    private TextField senhaTextField;
    @FXML
    private Button botaoVoltar;

    @FXML
    public void initialize(){
        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                userTextField.setText(oldValue);
            }
        });
        senhaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                senhaTextField.setText(oldValue);
            }
        });
    }

    @FXML
    public void checkBoxFisicoTrue(ActionEvent event) {
        if (checkBoxFisico.isSelected()) {
            checkBoxJuridico.setSelected(false);
            userTextField.setPromptText("CPF");
        }
    }

    @FXML
    public void checkBoxJuridcoTrue(ActionEvent event) {
        if (checkBoxJuridico.isSelected()) {
            checkBoxFisico.setSelected(false);
            userTextField.setPromptText("CNPJ");

        }
    }

    @FXML
    public void OnActionVoltar(ActionEvent event){
        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
        sceneSwitcher.switchScene("/pregaoInicialScreen.fxml");

    }
    @FXML
    public void OnActionLogin(ActionEvent event)throws IOException {
        if (checkBoxJuridico.isSelected()) {
            try {
                InvestidorJuridico investidorJuridico = new InvestidorJuridico();
                investidorJuridico.setCnpj(userTextField.getText());
                investidorJuridico.setSenha(senhaTextField.getText());
                investidorJuridico.buscarSaldo(userTextField.getText());
                investidorJuridico.loginJuridico(userTextField.getText(), senhaTextField.getText());


            } catch (RuntimeException e) {
                System.err.println("Erro: " + e);
            }
        }
        else if(checkBoxFisico.isSelected()){
            try {
                InvestidorFisico investidorFisico = new InvestidorFisico();
                investidorFisico.setCpf(userTextField.getText());
                investidorFisico.setSenha(senhaTextField.getText());
                investidorFisico.buscarSaldo(userTextField.getText());
                investidorFisico.loginJuridico(userTextField.getText(), senhaTextField.getText());
            }

            catch (RuntimeException e) {
                System.err.println("Erro: " + e);
            }
        }
    }
}

