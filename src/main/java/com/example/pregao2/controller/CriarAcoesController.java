package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.MudancaDePreco;
import com.example.pregao2.entidades.ativos.Fii;
import com.example.pregao2.entidades.ativos.Ordinaria;
import com.example.pregao2.entidades.ativos.Preferencial;
import com.example.pregao2.estruturas_de_dados.pilha.Pilha;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.Locale;
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
        comboBoxTipo.getItems().addAll("fii", "preferencial", "ordinaria");
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
        Pilha<Double> pilhaPrecos = new Pilha<>();
        for (int i = 0; i < 10; i++){
            double numeroAleatorioPilha = random.nextDouble() * 150;
            numeroAleatorioPilha = Math.round(numeroAleatorioPilha * 100.0) / 100.0;
            pilhaPrecos.addElemento(numeroAleatorioPilha);
            System.out.println(numeroAleatorioPilha);
        }
        pilhaPrecos.addElemento(numeroAleatorio);
        LocalDate hoje = LocalDate.now();
        MudancaDePreco mudancaDePreco = new MudancaDePreco(tipoAcao, ticket, pilhaPrecos.toString(),hoje);
        mudancaDePreco.insert(mudancaDePreco);

        if (Objects.equals(tipoAcao, "fii")) {
            try {

                Fii ativo = new Fii(nome, ticket, numeroAleatorio, loteQuantidade);
                System.out.println(ativo.toString());

                ativo.insert(ativo);

            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
        else if (Objects.equals(tipoAcao, "ordinaria")) {
            try {

                Ordinaria ativo = new Ordinaria(nome, ticket, numeroAleatorio , loteQuantidade);
                System.out.println(ativo.toString());

                ativo.insert(ativo);


            } catch (RuntimeException e) {
                System.err.println("ERRO " + e);
            }
        }
        else if (Objects.equals(tipoAcao, "preferencial")) {
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
                case "fii" -> "11B";
                case "preferencial" -> "2";
                case "ordinaria" -> "3";
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