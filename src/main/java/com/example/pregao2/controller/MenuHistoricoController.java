package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Historico;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private VBox vboxCompras;
    @FXML
    private Label nomeUserLabel;
    @FXML
    private TableView<Historico> tabelaHistoricoCompras;
    @FXML
    private TableView<Historico> tabelaHistoricoVendas;
    @FXML
    private Label saldoUserLabel;

    private String nome;
    private double saldo;
    private String id;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);

    @FXML
    public void initialize() {
        userInfo();
        configurarTabela(tabelaHistoricoCompras);
        configurarTabela(tabelaHistoricoVendas);
        preencherTabela(tabelaHistoricoCompras, obterHistoricoCompras());
        preencherTabela(tabelaHistoricoVendas, obterHistoricoVendas());
    }

    @FXML
    public void OnActionHistoricoMenuBotao() {
        sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");
    }

    @FXML
    public void OnAcitonCarteirasMenuBotao() {
        sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");
    }

    @FXML
    public void OnActionAltaBotao() {
        sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");
    }

    @FXML
    public void OnActionNegociarMenuBotao() {
        sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");
    }

    public void userInfo() {
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

    private void configurarTabela(TableView<Historico> tabela) {
        TableColumn<Historico, String> ticketCol = new TableColumn<>("TICKET");
        TableColumn<Historico, String> empresaCol = new TableColumn<>("EMPRESA");
        TableColumn<Historico, Integer> quantidadeCol = new TableColumn<>("QUANTIDADE");
        TableColumn<Historico, Double> precoTotalCol = new TableColumn<>("PREÇO TOTAL");
        TableColumn<Historico, Double> precoUnidadeCol = new TableColumn<>("PREÇO UNIDADE");
        TableColumn<Historico, LocalDateTime> dataCol = new TableColumn<>("DATA");

        ticketCol.setCellValueFactory(new PropertyValueFactory<>("ticket"));
        empresaCol.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        quantidadeCol.setCellValueFactory(new PropertyValueFactory<>("unidadesCompradas"));
        precoTotalCol.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        precoUnidadeCol.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela.getColumns().addAll(ticketCol, empresaCol, quantidadeCol, precoTotalCol, precoUnidadeCol, dataCol);
    }

    private void preencherTabela(TableView<Historico> tabela, List<Historico> dados) {
        ObservableList<Historico> observableData = FXCollections.observableArrayList(dados);
        tabela.setItems(observableData);
    }

    public List<Historico> obterHistoricoCompras() {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/historico.txt";
        List<Historico> historicoListaEncadeada = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 1 && partes[2].equals(nome)) {
                    Historico historico = new Historico();
                    historico.setUnidadesCompradas(Integer.parseInt(partes[7]));
                    historico.setPrecoUnitario(Double.parseDouble(partes[5]));
                    historico.setTicket(partes[4]);
                    historico.setEmpresa(partes[3]);
                    historico.setPrecoTotal(Double.parseDouble(partes[6]));
                    historico.setData(LocalDateTime.parse(partes[8]));
                    historicoListaEncadeada.add(historico);
                }
            }
            return historicoListaEncadeada;
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
        return historicoListaEncadeada;
    }

    public List<Historico> obterHistoricoVendas() {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/historicovendas.txt";
        List<Historico> historicoListaEncadeada = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 1 && partes[2].equals(nome)) {
                    Historico historico = new Historico();
                    historico.setUnidadesCompradas(Integer.parseInt(partes[7]));
                    historico.setPrecoUnitario(Double.parseDouble(partes[5]));
                    historico.setTicket(partes[4]);
                    historico.setEmpresa(partes[3]);
                    historico.setPrecoTotal(Double.parseDouble(partes[6]));
                    historico.setData(LocalDateTime.parse(partes[8]));
                    historicoListaEncadeada.add(historico);
                }
            }
            return historicoListaEncadeada;
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
        return historicoListaEncadeada;
    }
}
