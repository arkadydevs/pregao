package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Historico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;
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
    private TableView tabelaHistoricoCompras;
    @FXML
     private TableColumn ticket;
    @FXML
    private TableColumn empresa;
    @FXML
    private TableColumn quantidade;
    @FXML
    private TableColumn precoTotal;
    @FXML
    private TableColumn precoUnidade;
    @FXML
    private TableColumn data;
    @FXML
    private Label saldoUserLabel;
    private String nome;
    private double saldo;
    private String id;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);


    @FXML
    public void initialize() {
        userInfo();
        colocarColunas();
        preencherTabelaHistoricoCompras();


    }


    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");}

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

    public void colocarColunas(){
        ticket = new TableColumn("TICKET");
        empresa = new TableColumn("EMPRESA");
        quantidade = new TableColumn("QUANTIDADE");
        precoTotal = new TableColumn("PREÇO TOTAL");
        precoUnidade = new TableColumn("PREÇO UNIDADE");
        data = new TableColumn("DATA");

        ticket.setCellValueFactory(new PropertyValueFactory<Historico, String>("ticket"));
        empresa.setCellValueFactory(new PropertyValueFactory<Historico, String>("empresa"));
        quantidade.setCellValueFactory(new PropertyValueFactory<Historico, Integer>("unidadesCompradas"));
        precoTotal.setCellValueFactory(new PropertyValueFactory<Historico, Double>("precoTotal"));
        precoUnidade.setCellValueFactory(new PropertyValueFactory<Historico, Double>("precoUnitario"));
        data.setCellValueFactory(new PropertyValueFactory<Historico, LocalDateTime>("data"));



        tabelaHistoricoCompras.getColumns().addAll(ticket, empresa, quantidade, precoTotal, precoUnidade, data);
    }

    public void preencherTabelaHistoricoCompras() {
        List<Historico> listaDeHistorico = obterHistoricoCompras();
        ObservableList<Historico> observableHistoricoList = FXCollections.observableArrayList(listaDeHistorico);
        tabelaHistoricoCompras.setItems(observableHistoricoList);
    }


    public List<Historico> obterHistoricoCompras(){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/historico.txt";
        List <Historico> historicoListaEncadeada = new ArrayList<>();

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
                    historico.toString();
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
