package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Historico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

public class MenuPrincipal{
    @FXML
    private Label nomeUserLabel;
    @FXML
    private Label saldoUserLabel;
    @FXML
    private Label precoAtualLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label quantidadePrecoLabel;
    @FXML
    private Label nomeTicketLabel;
    @FXML
    private Spinner quantidadeSpinner;
    @FXML
    private ComboBox comboBoxAcoes;
    @FXML
    private ComboBox comboBoxAcoesTipo;
    @FXML
    private Button botaoConfirmarCompra;
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
    private String id;
    private double saldo;
    private int quantidade;
    private String acaoPreco = "";
    private String ticketNome = "";
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    ObjectSaveManager obj = new ObjectSaveManager();
    LocalDateTime tempo = LocalDateTime.now();




    @FXML
    public void initialize() {
        ObservableList<String> tipos = FXCollections.observableArrayList("fii", "preferencial", "ordinaria");
        comboBoxAcoesTipo.setItems(tipos);
        userInfo();
    }

    public void userInfo(){
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

    public void spinnerQuantidade() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 20, 0);
        quantidadeSpinner.setValueFactory(valueFactory);

        quantidadeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            quantidade = (int) newValue;
            double acaoPrecoDouble = Double.parseDouble(acaoPreco);
            double precoTotal = quantidade * acaoPrecoDouble;
            precoTotal = Math.round(precoTotal * 100.0) / 100.0;
            quantidadePrecoLabel.setText("Preço: " + String.valueOf(precoTotal));
        });
    }


    @FXML
    public void OnActiontTipoAcaoBotao(ActionEvent event) {
        String tipoCaminho = (String) comboBoxAcoesTipo.getValue();
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminho+".txt";
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
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminho+".txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (Objects.equals(partes[1], acaoEscolhida)) {
                    acaoPreco = partes[2];
                    spinnerQuantidade();

                }
            }
            ticketNome = (String) comboBoxAcoes.getValue();
            precoAtualLabel.setText(acaoPreco);
            nomeTicketLabel.setText(ticketNome);
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }
    @FXML
    public void OnActionBotaoConfirmarCompra(){
        String quantidadePreco = quantidadePrecoLabel.getText();
        String[] precoFinalArray = quantidadePreco.split(": ");
        double precoFinal = Double.parseDouble(precoFinalArray[1]);
        if(precoFinal > saldo){
            errorLabel.setText("SALDO INSUFICIENTE");
        }else{
            saldo = saldo - precoFinal;
            saldoUserLabel.setText(String.valueOf(saldo));
            int idInt = Integer.parseInt(id);
            Historico compra = new Historico(idInt, nome, null, ticketNome, Double.parseDouble(acaoPreco), precoFinal, quantidade,tempo);
            System.out.println(compra.toString());

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



