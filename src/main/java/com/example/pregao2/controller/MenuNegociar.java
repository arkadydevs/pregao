package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.AcoesNaCarteira;
import com.example.pregao2.entidades.Historico;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;


public class MenuNegociar {
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
    private ComboBox comboBoxCarteiras;
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
    private Label erroLabelComboBox;
    @FXML
    private Button altaBotao;

    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?,?> graficoHistorico;

    private String nome;
    private String id;
    private double saldo;
    private int quantidade;
    private String acaoPreco = "";
    private String ticketNome = "";
    private String tipoObj = "";
    private double precoTotal;

    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    ObjectSaveManager obj = new ObjectSaveManager();
    LocalDateTime tempo = LocalDateTime.now();

    private String saldoStr;



    @FXML
    public void initialize() {
        ObservableList<String> tipos = FXCollections.observableArrayList("fii", "preferencial", "ordinaria");
        comboBoxAcoesTipo.setItems(tipos);
        userInfo();
        setComboxCarteiras(id);
    }

    public void userInfo(){
        nome = (String) obj.getObject("NOME");
        id = (String) obj.getObject("ID");
        tipoObj = (String) obj.getObject("TIPO");


        saldoStr = (String) obj.getObject("SALDO");
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            saldo = 0.0;
        }

        nomeUserLabel.setText(nome);
        saldoUserLabel.setText(String.valueOf(saldo));
    }

    public void spinnerQuantidade(int limite) {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, limite, 0);
        quantidadeSpinner.setValueFactory(valueFactory);

        quantidadeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            quantidade = (int) newValue;
            double acaoPrecoDouble = Double.parseDouble(acaoPreco);
            precoTotal = quantidade * acaoPrecoDouble;
            precoTotal = Math.round(precoTotal * 100.0) / 100.0;
            quantidadePrecoLabel.setText("Preço: " + String.valueOf(precoTotal));
        });
    }



    @FXML
    public void OnActiontTipoAcaoBotao(ActionEvent event) {
        errorLabel.setText("");
        erroLabelComboBox.setText("");
        if (comboBoxAcoesTipo.getValue()==null){
            erroLabelComboBox.setText("É PRECISO ESCOLHER TIPO DE AÇÃO");
        }
        else{
            String tipoCaminho = (String) comboBoxAcoesTipo.getValue();
            String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminho+".txt";
            ObservableList<String> listaTicket = FXCollections.observableArrayList();

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                while ((linha = bufferedReader.readLine()) != null) {
                    String[] partes = linha.split(" ");
                    if (partes.length >= 2 && Integer.parseInt(partes[3]) >0) {
                        String ticket = partes[1];
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

    }


    public void setGraficoHistorico() {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/mudancadepreco.txt";
        XYChart.Series series = new XYChart.Series();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 2 && partes[1].equals(comboBoxAcoes.getValue())) {
                    String[] precosTabela = partes[2].split(",");
                    for (int i = 0; i < precosTabela.length; i++) {
                        String xStr = String.valueOf(i);
                        double yValue = Double.parseDouble(precosTabela[i]);
                        XYChart.Data data = new XYChart.Data(xStr, yValue);
                        series.getData().add(data);

                    }
                }
            }
            series.setName("PREÇO DO ATIVO");
            graficoHistorico.getData().clear();
            graficoHistorico.getData().addAll(series);
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }


    @FXML
    public void OnActionProcurarAcaoBotao(ActionEvent event){
        errorLabel.setText("");
        erroLabelComboBox.setText("");
        if(comboBoxAcoes.getValue() == null){
            erroLabelComboBox.setText("É PRECISO ESCOLHER UMA AÇÃO");
        }
        else {
            String acaoEscolhida = (String) comboBoxAcoes.getValue();
            String tipoCaminho = (String) comboBoxAcoesTipo.getValue();
            String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminho+".txt";
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;

                while ((linha = bufferedReader.readLine()) != null) {
                    String[] partes = linha.split(" ");
                    if (partes.length >= 2 && Objects.equals(partes[1], acaoEscolhida)) {
                        acaoPreco = partes[2];
                        spinnerQuantidade(Integer.parseInt(partes[3]));
                    }
                }
                ticketNome = (String) comboBoxAcoes.getValue();
                checarUltimaAtualizacao(ticketNome);
                precoAtualLabel.setText(acaoPreco);
                nomeTicketLabel.setText(ticketNome);
                graficoHistorico.setTitle(ticketNome);
                setGraficoHistorico();

            } catch (IOException e) {
                System.err.println("Erro na leitura do arquivo: " + e.getMessage());
            }
        }

    }
    @FXML
    public void OnActionBotaoConfirmarCompra(ActionEvent event){
        errorLabel.setText("");
        erroLabelComboBox.setText("");
        if(comboBoxCarteiras.getValue() == (null)){
            errorLabel.setText("É PRECISO ESCOLHER UM CARTEIRA");
        }
        else if(quantidade == 0){
            errorLabel.setText("VOCÊ NÃO PODE COMPRAR 0 ATIVOS");
        }
        else{


            String quantidadePreco = quantidadePrecoLabel.getText();
            String[] precoFinalArray = quantidadePreco.split(": ");
            double precoFinal = Double.parseDouble(precoFinalArray[1]);
            if(precoFinal > saldo){
                errorLabel.setText("SALDO INSUFICIENTE");
            }else{
                String tipoAcao = (String) comboBoxAcoesTipo.getValue();
                saldo = saldo - precoFinal;
                int quantidade = (int) quantidadeSpinner.getValue();
                System.out.println(quantidade);
                atualizarSaldo(saldo);
                Historico compra = new Historico();
                String empresaTicket = buscarEmpresaPeloTicket(ticketNome, tipoAcao);
                String idCarteiraGlobal = (String) comboBoxCarteiras.getValue();

                compra.setIdCarteira(idCarteiraGlobal);
                compra.setComprador(nome);
                compra.setEmpresa(empresaTicket);
                compra.setTicket(ticketNome);
                compra.setPrecoUnitario( Double.parseDouble(acaoPreco));
                compra.setPrecoTotal(precoFinal);
                compra.setUnidadesCompradas(quantidade);
                compra.setData(tempo);
                compra.insert(compra);

                AcoesNaCarteira acoesNaCarteira = new AcoesNaCarteira(idCarteiraGlobal, id, tipoAcao ,ticketNome, quantidade);
                System.out.println(acoesNaCarteira.toString());
                acoesNaCarteira.insert(acoesNaCarteira);
                System.out.println(compra.toString());
                atualizarQuantidadeGlobal(quantidade, ticketNome, tipoAcao);
                errorLabel.setText("AÇÕES COMPRADAS! SALDO ATUAL: " + saldo);
                errorLabel.setStyle("-fx-text-fill: green;");
                obj.updateObject("SALDO", String.valueOf(saldo));
            }
        }
    }


    public String buscarEmpresaPeloTicket(String ticket, String tipoCaminho) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminho+".txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 2) {
                    String codNegociacao = partes[1];
                    String empresa = partes[0];
                    if (ticket.equals(codNegociacao)) {
                        return empresa;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
        return null;
    }
    public void atualizarQuantidadeGlobal(int quantidadeAtivosVendidos, String ticker,  String tipoAcao) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/" + tipoAcao + ".txt";
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3 && partes[1].equals(ticker)) {
                    int novaQuantidade =Integer.parseInt(partes[3])- quantidadeAtivosVendidos;
                    partes[3] = String.valueOf(novaQuantidade);
                    linha = String.join(" ", partes);
                }
                linhas.addElemento(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (int i = 0; i < linhas.getTamanho(); i++) {
                String linha = String.valueOf(linhas.get(i));
                printWriter.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }

    public void atualizarSaldo(double novoSaldo) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/" + tipoObj + ".txt";
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 5 && partes[0].equals(id)) {
                    String novoSaldoStr = String.valueOf(novoSaldo);
                    partes[2] = (novoSaldoStr);
                    linha = String.join(" ", partes);
                    saldoUserLabel.setText(novoSaldoStr);
                }
                linhas.addElemento(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (int i = 0; i < linhas.getTamanho(); i++) {
                String linha = String.valueOf(linhas.get(i));
                printWriter.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }



    public void setComboxCarteiras(String id){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/carteira.txt";
        ObservableList<String> listaCarteiras = FXCollections.observableArrayList();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3) {
                    String idInvestidor = partes[1];
                    if (id.equals(idInvestidor)) {
                        System.out.println(idInvestidor);
                        listaCarteiras.add(partes[2]);
                    }
                }
            }
            comboBoxCarteiras.setItems(listaCarteiras);
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    public void checarUltimaAtualizacao(String ticker) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/mudancadepreco.txt";
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3 && partes[1].equals(ticker)) {
                    LocalDate diaTxt = LocalDate.parse(partes[3]);
                    LocalDate hoje = LocalDate.now();
                    long diferencaEmDias = ChronoUnit.DAYS.between(diaTxt, hoje);

                    if (diferencaEmDias >= 1) {
                        partes[3] = String.valueOf(hoje);
                        Random random = new Random();
                        double numeroAleatorio = random.nextDouble() * 150;
                        numeroAleatorio = Math.round(numeroAleatorio * 100.0) / 100.0;
                        partes[2] = String.valueOf(numeroAleatorio) +","+partes[2];
                        System.out.println(numeroAleatorio);
                        atualizarPrecoAtivo(ticker, String.valueOf(numeroAleatorio));

                        linha = String.join(" ", partes);

                    }
                }
                linhas.addElemento(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (int i = 0; i < linhas.getTamanho(); i++) {
                String linha = String.valueOf(linhas.get(i));
                printWriter.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }

    public void atualizarPrecoAtivo(String ticker, String ultimoPreco) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ comboBoxAcoesTipo.getValue() +".txt";
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3 && partes[1].equals(ticker)) {
                    partes[2] = ultimoPreco;
                    linha = String.join(" ", partes);

                }
                linhas.addElemento(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (int i = 0; i < linhas.getTamanho(); i++) {
                String linha = String.valueOf(linhas.get(i));
                printWriter.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }


    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");}
}



