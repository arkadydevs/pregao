package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.ativos.Fii;
import com.example.pregao2.entidades.ativos.Ordinaria;
import com.example.pregao2.entidades.ativos.Preferencial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

public class CriarAcoesController {

    @FXML
    private Button botaoCriar;
    @FXML
    private Button voltarMenuEscolha;
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
    String nome;
    @FXML
    private Spinner<Integer> spinnerLote;
    ObjectSaveManager obj = new ObjectSaveManager();


    @FXML
    public void initialize() {
        updateAcoesSaidaExemploLabel();
        setComboBoxTipo();
        setSpinnerLote();
        setTextFieldNomes();
        setNome();

        comboBoxTipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateAcoesSaidaExemploLabel();
        });
    }

    public void setComboBoxTipo() {
        comboBoxTipo.getItems().addAll("FII", "PREFERENCIAL", "ORDINÁRIA");
    }


    @FXML
    public void onActionBotaoCriar(ActionEvent event){
        String tipoAcao = comboBoxTipo.getValue();
        String ticket = acoeSaidaExemplo.getText();
        int loteQuantidade = spinnerLote.getValue();
        Random random = new Random();
        double numeroAleatorio = random.nextDouble() * 150;
        numeroAleatorio = Math.round(numeroAleatorio * 100.0) / 100.0;
        LocalDate dataDeHoje = LocalDate.now();

        if (Objects.equals(tipoAcao, "FII")) {
            try {

                Fii ativo = new Fii(nome, ticket, numeroAleatorio, loteQuantidade);
                System.out.println(ativo.toString());

                ativo.insert(ativo);

            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
        else if (Objects.equals(tipoAcao, "ORDINÁRIA")) {
            try {

                Ordinaria ativo = new Ordinaria(nome, ticket, numeroAleatorio , loteQuantidade);
                System.out.println(ativo.toString());

                ativo.insert(ativo);


            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
        else if (Objects.equals(tipoAcao, "PREFERENCIAL")) {
            try {

                Preferencial ativo = new Preferencial(nome, ticket, numeroAleatorio, loteQuantidade);
                System.out.println(ativo.toString());

                ativo.insert(ativo);


            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
    }

    @FXML
    public void OnActionVoltarMenuEscolha(){
        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
        sceneSwitcher.switchScene("/fxml/escolhaJuridico.fxml");
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

        if(comboBoxTipo.getValue() == null){
            String textFieldExemplo = textFieldNome1.getText() + textFieldNome2.getText() + textFieldNome3.getText() + textFieldNome4.getText() + "";

        }
        else {
            String tipoText = switch (comboBoxTipo.getValue()) {
                case "FII" -> "11B";
                case "PREFERENCIAL" -> "2";
                case "ORDINÁRIA" -> "3";
                default -> "";
            };
            String textFieldExemplo = textFieldNome1.getText() + textFieldNome2.getText() + textFieldNome3.getText() + textFieldNome4.getText() + tipoText;
            acoeSaidaExemplo.setText(textFieldExemplo);
        }

    }
    public void setNome(){
        ObjectSaveManager obj = new ObjectSaveManager();
        nome = (String) obj.getObject("NOME");
        nomeUserLabel.setText("Empresa: " + nome);
    }
    public void setSpinnerLote(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 100, 0);
        spinnerLote.setValueFactory(valueFactory);
    }

}