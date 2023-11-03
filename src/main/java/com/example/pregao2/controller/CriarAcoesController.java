package com.example.pregao2.controller;

import entidades.ativos.Fii;
import entidades.ativos.Ordinaria;
import entidades.ativos.Preferencial;
import estruturas_de_dados.lista.ListaEncadeada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ObjectSaveManager;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

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
    private Spinner<Integer> spinnerLote;

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
                Fii ativo = new Fii(ticket, numeroAleatorio, loteQuantidade);
                System.out.println(ativo.getCodNegociacao());
                System.out.println(ativo.getCotacao());
                ativo.insert(ativo);

            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
        else if (Objects.equals(tipoAcao, "ORDINÁRIA")) {
            try {
                Ordinaria ativo = new Ordinaria(ticket, numeroAleatorio , loteQuantidade);
                System.out.println(ativo);
                System.out.println(ativo.getCodNegociacao());
                ativo.insert(ativo);


            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
        else if (Objects.equals(tipoAcao, "PREFERENCIAL")) {
            try {
                Preferencial ativo = new Preferencial(ticket, numeroAleatorio, loteQuantidade);
                System.out.println(ativo);
                System.out.println(ativo.getCodNegociacao());
                ativo.insert(ativo);


            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
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
        String nome = (String) obj.getObject("NOME");
        nomeUserLabel.setText("Empresa: " + nome);
    }
    public void setSpinnerLote(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 100, 0);
        spinnerLote.setValueFactory(valueFactory);
    }

}