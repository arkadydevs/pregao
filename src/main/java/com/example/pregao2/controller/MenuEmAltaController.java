package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.example.pregao2.model.ObjectSaveManager;
import com.example.pregao2.model.ShellSort;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MenuEmAltaController {

    @FXML
    private Button altaBotao;

    @FXML
    private Button botaoProcurar;

    @FXML
    private Button carteirasMenuBotao;

    @FXML
    private ComboBox comboBoxAcoesTipo;

    @FXML
    private ComboBox comboBoxOrdernar;

    @FXML
    private Button historicoMenuBotao;

    @FXML
    private Button negociarMenuBotao;

    @FXML
    private Label nomeUserLabel;

    @FXML
    private Label saldoUserLabel;
    @FXML
    private VBox vboxPesquisa;
    private String nome;
    private double saldo;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    private ShellSort shellSort = new ShellSort();
    Map<Double, String> tickerEPrecos = new HashMap<>();


    @FXML
    public void initialize() {
        userInfo();
        setComboBox();
    }

    @FXML
    public void OnActionProcurar(ActionEvent event){
        setComboBox();
        pegarDadosOrdenados();
    }

    public void pegarDadosOrdenados() {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/" + comboBoxAcoesTipo.getValue() + ".txt";
        String[] arrayTickerOrdenado = ordenarEmAlta();

        ListaEncadeada<String> linhasFormatadas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            List<String> todasAsLinhas = new ArrayList<>();
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                todasAsLinhas.add(linha);
            }

            for (int i = 0; i < arrayTickerOrdenado.length; i++) {
                for (String linhaArquivo : todasAsLinhas) {
                    String[] partes = linhaArquivo.split(" ");
                    if (partes.length >= 4 && arrayTickerOrdenado[i].equals(partes[1])) {
                        String detalhesFormatados = String.format("%-10s | %-10s | %-10s | %-10s", partes[0], partes[1], partes[2], partes[3]);
                        System.out.println(detalhesFormatados);
                        linhasFormatadas.addElemento(detalhesFormatados);
                    }
                }
            }

            vboxPesquisa.getChildren().clear();
            for (int i = 0; i < linhasFormatadas.getTamanho(); i++) {
                Label label = new Label(linhasFormatadas.get(i).getValor());
                label.setFont(new Font(20));

                HBox hbox = new HBox(label);
                hbox.setStyle("-fx-background-color: #e3e1e1");
                hbox.setPadding(new Insets(0, 10, 0, 0));
                hbox.setAlignment(Pos.CENTER);
                vboxPesquisa.getChildren().add(hbox);
            }

        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }




    public String[] ordenarEmAlta() {
        double[] arrayDadosOrdenados;
        double[] arrayDadosTxt = arrayDadosTxt();
        System.out.println("Antes da ordenação: " + Arrays.toString(arrayDadosTxt));

        String tipoDado = (String) comboBoxOrdernar.getValue();
        System.out.println("Tipo de Dado: " + tipoDado);

        if (tipoDado.equals("MAIOR QUANTIDADE") || tipoDado.equals("PREÇO MAIS ALTO")) {
            arrayDadosOrdenados = ShellSort.shellSortMaiorMenor(arrayDadosTxt);

        } else if (tipoDado.equals("MENOR QUANTIDADE") || tipoDado.equals("PREÇO MAIS BAIXO")) {
            arrayDadosOrdenados = ShellSort.shellSort(arrayDadosTxt);

        } else {

            System.out.println("Tipo de Dado não reconhecido: " + tipoDado);
            return new String[0];
        }

        System.out.println("Depois da ordenação: " + Arrays.toString(arrayDadosOrdenados));

        ListaEncadeada<String> listaDados = new ListaEncadeada<>();
        for (double arrayDadosOrdenado : arrayDadosOrdenados) {
            listaDados.addElemento(tickerEPrecos.get(arrayDadosOrdenado));
            System.out.println(arrayDadosOrdenado);
            System.out.println(tickerEPrecos.get(arrayDadosOrdenado));
        }

        String[] tickerNomes = new String[listaDados.getTamanho()];
        for (int i = 0; i < listaDados.getTamanho(); i++) {
            tickerNomes[i] = listaDados.get(i).getValor();
        }

        return tickerNomes;
    }



    public double[] arrayDadosTxt(){
        tickerEPrecos.clear();
        String tipoDado = (String) comboBoxOrdernar.getValue();

        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ comboBoxAcoesTipo.getValue() +".txt";
        double[] arrayDados = {};
        ListaEncadeada<Double> listaDados = new ListaEncadeada<>();


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 2) {
                    if(tipoDado.equals("MAIOR QUANTIDADE") || tipoDado.equals("MENOR QUANTIDADE")){
                        tickerEPrecos.put(Double.parseDouble(partes[3]), partes[1]);
                        listaDados.addElemento(Double.parseDouble(partes[3]));
                    }
                    else  if(tipoDado.equals("PREÇO MAIS BAIXO") || tipoDado.equals("PREÇO MAIS ALTO")){
                        tickerEPrecos.put(Double.parseDouble(partes[2]), partes[1]);
                        listaDados.addElemento(Double.parseDouble(partes[2]));
                    }
                }
            }
            arrayDados = new double[listaDados.getTamanho()];
            for (int i = 0; i < listaDados.getTamanho(); i++) {
                arrayDados[i] = listaDados.get(i).getValor();
            }


        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
        return arrayDados;
    }


    public void setComboBox(){
        ObservableList<String> spinner = FXCollections.observableArrayList("fii", "preferencial", "ordinaria");
        comboBoxAcoesTipo.setItems(spinner);
        spinner = FXCollections.observableArrayList("PREÇO MAIS ALTO", "PREÇO MAIS BAIXO", "MAIOR QUANTIDADE", "MENOR QUANTIDADE");
        comboBoxOrdernar.setItems(spinner);
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
        String saldoStr = (String) obj.getObject("SALDO");
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            saldo = 0.0;
        }

        nomeUserLabel.setText(nome);
        saldoUserLabel.setText(String.valueOf(saldo));
    }
}
