package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MenuHistoricoController {

    @FXML
    private Button carteirasMenuBotao;

    @FXML
    private Button historicoMenuBotao;

    @FXML
    private Button negociarMenuBotao;
    @FXML
    private Button altaBotao;
    @FXML
    private TextArea textLabelHIstorico;

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
        procurarHisturico();
    }

    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/fxml/menuPrincipal.fxml");}

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

    private void procurarHisturico() {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/historico.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            StringBuilder historicoText = new StringBuilder();
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 1 && partes[2].equals(nome)) {
                    historicoText.append(linha).append("\n");
                }
            }
            textLabelHIstorico.setText(historicoText.toString());
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }
}
