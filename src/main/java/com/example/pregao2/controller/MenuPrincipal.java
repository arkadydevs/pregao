package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import entidades.investidores.InvestidorJuridico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ObjectSaveManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Objects;

public class MenuPrincipal{
    @FXML
    private Label nomeUserLabel;
    @FXML
    private Label saldoUserLabel;
    @FXML
    private Label precoAtualLabel;
    @FXML
    private Spinner quantidadeSpinner;
    @FXML
    private ComboBox comboBoxAcoes;
    @FXML
    private ComboBox comboBoxAcoesTipo;
    @FXML
    private Label quantidadePrecoLabel;
    @FXML
    private Button tipoAcaoBotao;
    @FXML
    private Button negociarMenuBotao;
    @FXML
    private Button historicoMenuBotao;
    @FXML
    private Button carteirasMenuBotao;
    @FXML
    private Button emAltaMenuBotao;
    @FXML
    private Button altaBotao;
    private String nome;
    private double saldo;
    String acaoPreco = "";
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);


    @FXML
    public void initialize() {
        ObservableList<String> tipos = FXCollections.observableArrayList("fii", "preferencial", "ordinaria");
        comboBoxAcoesTipo.setItems(tipos);
        userInfo();
        spinnerQuantidade();
    }

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

    public void spinnerQuantidade() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 20, 0);
        quantidadeSpinner.setValueFactory(valueFactory);

        double acaoPrecoDouble = Double.parseDouble(acaoPreco);
        int quantidade = (int) quantidadeSpinner.getValue();

        double precoTotal = quantidade * acaoPrecoDouble;
        quantidadePrecoLabel.setText(String.valueOf(precoTotal));
    }


    @FXML
    public void OnActiontTipoAcaoBotao(ActionEvent event) {
        String tipoCaminho = (String) comboBoxAcoesTipo.getValue();
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\"+ tipoCaminho + ".txt";
        ObservableList<String> listaTicket = FXCollections.observableArrayList();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 1) {
                    String ticket = partes[0];
                    if (ticket.matches("[A-Z]+\\d+[A-Z]*")) {
                        System.out.println(ticket);
                        listaTicket.add(ticket);
                    }
                }
            }
            comboBoxAcoes.setItems(listaTicket);
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    @FXML
    public void OnActionProcurarAcaoBotao(ActionEvent event){
        String acaoEscolhida = (String) comboBoxAcoes.getValue();
        String tipoCaminho = (String) comboBoxAcoesTipo.getValue();
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\"+ tipoCaminho + ".txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (Objects.equals(partes[0], acaoEscolhida)) {
                    acaoPreco = partes[1];


                }
            }
            precoAtualLabel.setText(acaoPreco);
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/menuPrincipal.fxml");}
}



