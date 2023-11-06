package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Historico;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;


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
    private String nome;
    private String id;
    private double saldo;
    private int quantidade;
    private String acaoPreco = "";
    private String ticketNome = "";
    private String tipoObj = "";
    private String idCarteiraGlobal;
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
                    if (partes.length >= 2) {
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

    }
    @FXML
    public void OnActionBotaoConfirmarCompra(ActionEvent event){
        errorLabel.setText("");
        erroLabelComboBox.setText("");

        String quantidadePreco = quantidadePrecoLabel.getText();
        String[] precoFinalArray = quantidadePreco.split(": ");
        double precoFinal = Double.parseDouble(precoFinalArray[1]);
        if(precoFinal > saldo){
            errorLabel.setText("SALDO INSUFICIENTE");
        }else{

            saldo = saldo - precoFinal;
            atualizarSaldo(saldo);
            Historico compra = new Historico();
            String empresaTicket = buscarEmpresaPeloTicket(ticketNome, (String) comboBoxAcoesTipo.getValue());

            compra.setIdCarteira(idCarteiraGlobal);
            compra.setComprador(nome);
            compra.setEmpresa(empresaTicket);
            compra.setTicket(ticketNome);
            compra.setPrecoUnitario( Double.parseDouble(acaoPreco));
            compra.setPrecoTotal(precoFinal);
            compra.setUnidadesCompradas(quantidade);
            compra.setData(tempo);
            compra.insert(compra);
            System.out.println(compra.toString());
            errorLabel.setText("AÇÕES COMPRADAS! SALDO ATUAL: " + saldo);
            errorLabel.setStyle("-fx-text-fill: green;");
            obj.updateObject("SALDO", String.valueOf(saldo));
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
                if (partes.length >= 2) {
                    String idInvestidor = partes[1];
                    if (id.equals(idInvestidor)) {
                        idCarteiraGlobal = partes[0];
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


    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");}
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");}
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");}
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");}
}



