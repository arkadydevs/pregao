package com.example.pregao2.controller;

import estruturas_de_dados.lista.ListaEncadeada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ObjectSaveManager;

public class CriarAcoesController {

    @FXML
    private Button botaoCriar;
    @FXML
    private Label acoeSaidaExemplo;

    @FXML
    private ComboBox<String> comboBoxTipo;

    @FXML
    private Label nomeUserLabel;

    @FXML
    private TextField textFieldNome1;

    @FXML
    private TextField textFieldNome2;

    @FXML
    private TextField textFieldNome3;

    @FXML
    private TextField textFieldNome4;

    @FXML
    public void initialize() {
        setComboBoxTipo();
        setTextFieldNomes();
        setNome();
    }

    public void setComboBoxTipo() {
        ObservableList<String> observableList = FXCollections.observableArrayList("FII", "PREFERENCIAL", "ORDINÁRIA");
        comboBoxTipo.setItems(observableList);

    }

    public void setTextFieldNomes(){
        TextField[] textFields = {textFieldNome1, textFieldNome2, textFieldNome3, textFieldNome4};
        for (TextField textField : textFields) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 1 || !Character.isLetter(newValue.charAt(0))) {
                    textField.setText(oldValue);
                }
                else{
                    textField.setText(newValue.toUpperCase());
                    updateAcoesSaidaExemploLabel();

                }
            });
        }
    }

    public void updateAcoesSaidaExemploLabel() {
        TextField[] textFields = {textFieldNome1, textFieldNome2, textFieldNome3, textFieldNome4};
        for (TextField textField : textFields){
            if (textField.equals("")){
                textField.setText(" ");
            }
        }
        String tipoText = switch (comboBoxTipo.getValue()) {
            case "FII" -> "11B";
            case "PREFERENCIAL" -> "2";
            case "ORDINÁRIA" -> "3";
            default -> "";
        };
        String textFieldExemplo = textFieldNome1.getText() + textFieldNome2.getText() + textFieldNome3.getText() + textFieldNome4.getText() + tipoText;
        acoeSaidaExemplo.setText(textFieldExemplo);
    }

    public void setNome(){
        ObjectSaveManager obj = new ObjectSaveManager();
        String nome = (String) obj.getObject("NOME");
        nomeUserLabel.setText(nome);
    }
}