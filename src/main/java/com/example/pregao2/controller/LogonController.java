package com.example.pregao2.controller;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

import com.example.pregao2.MainApp;
import entidades.investidores.InvestidorFisico;
import entidades.investidores.InvestidorJuridico;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.Objects;

public class LogonController {

    @FXML
    private CheckBox checkBoxFisico;
    @FXML
    private CheckBox checkBoxJuridico;
    @FXML
    private Button logonButton;
    @FXML
    private Label errorTextLabel;
    @FXML
    private TextField cnpjTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField senhaTextField;
    @FXML
    private TextField saldoTextField;

    @FXML
    public void initialize(){
        nomeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                nomeTextField.setText(oldValue);
            }
        });
        senhaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                senhaTextField.setText(oldValue);
            }
        });
        saldoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                saldoTextField.setText(oldValue);
            }
        });
    }

    @FXML
    public void checkBoxFisicoTrue(ActionEvent event) {
        if (checkBoxFisico.isSelected()) {
            checkBoxJuridico.setSelected(false);
            cnpjTextField.setPromptText("CPF");
        }
    }

    @FXML
    public void checkBoxJuridicoTrue(ActionEvent event) {
        if (checkBoxJuridico.isSelected()) {
            checkBoxFisico.setSelected(false);
            cnpjTextField.setPromptText("CNPJ");

        }
    }

    @FXML
    public void onActionLogonButton(ActionEvent event) {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);

        if (checkBoxJuridico.isSelected()) {
            InvestidorJuridico investidor = new InvestidorJuridico();
            if ((Objects.equals(cnpjTextField.getText(), "")) || (Objects.equals(senhaTextField.getText(), "")) || (Objects.equals(saldoTextField.getText(), "")) || (Objects.equals(nomeTextField.getText(), ""))) {
                errorTextLabel.setText("PREENCHER TODOS OS CAMPOS");
            } else {
                int teste = 0;

                CNPJValidator validator = new CNPJValidator();
                investidor.setNome(nomeTextField.getText());

                try {
                    errorTextLabel.setText("");
                    validator.assertValid(cnpjTextField.getText());
                    investidor.setCnpj(cnpjTextField.getText());
                    teste++;
                } catch (InvalidStateException e) {
                    errorTextLabel.setText("CNPJ INVÁLIDO");
                }

                try {
                    investidor.setSenha(senhaTextField.getText());
                    teste++;

                } catch (RuntimeException e) {
                    errorTextLabel.setText("SENHA PRECISA TER NO MÍNIMO 6 CARACTERES");
                }

                try {
                    investidor.setSaldo(Double.parseDouble(saldoTextField.getText()));
                    teste++;

                } catch (IllegalArgumentException e) {
                    errorTextLabel.setText("SALDO NÃO PODE SER MENOR QUE ZERO");
                }

                if (teste == 3) {
                    investidor.insert(investidor);
                    System.out.println("Botão InitialButton clicado");
                    sceneSwitcher.switchScene("/menuPrincipal.fxml");
                    sceneSwitcher.switchScene("/pregaoInicialScreen.fxml");


                }
            }
        } else {
            if (checkBoxFisico.isSelected()) {
                InvestidorFisico investidor = new InvestidorFisico();
                if ((Objects.equals(cnpjTextField.getText(), "")) || (Objects.equals(senhaTextField.getText(), "")) || (Objects.equals(saldoTextField.getText(), "")) || (Objects.equals(nomeTextField.getText(), ""))) {
                    errorTextLabel.setText("PREENCHER TODOS OS CAMPOS");
                } else {
                    int teste = 0;

                    CPFValidator validator = new CPFValidator();
                    investidor.setNome(nomeTextField.getText());

                    try {
                        errorTextLabel.setText("");
                        validator.assertValid(cnpjTextField.getText());
                        investidor.setCpf(cnpjTextField.getText());
                        teste++;
                    } catch (InvalidStateException e) {
                        errorTextLabel.setText("CPF INVÁLIDO");
                    }

                    try {
                        investidor.setSenha(senhaTextField.getText());
                        teste++;

                    } catch (RuntimeException e) {
                        errorTextLabel.setText("SENHA PRECISA TER NO MÍNIMO 6 CARACTERES");
                    }

                    try {
                        investidor.setSaldo(Double.parseDouble(saldoTextField.getText()));
                        teste++;

                    } catch (IllegalArgumentException e) {
                        errorTextLabel.setText("SALDO NÃO PODE SER MENOR QUE ZERO");
                    }

                    if (teste == 3) {
                        investidor.insert(investidor);
                        System.out.println("Botão InitialButton clicado");
                        sceneSwitcher.switchScene("/pregaoInicialScreen.fxml");
                    }
                }
            }
        }
    }
}
