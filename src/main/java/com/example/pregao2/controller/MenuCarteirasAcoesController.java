package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Historico;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Random;

public class MenuCarteirasAcoesController {
    @FXML
    private Button altaBotao;

    @FXML
    private Button botaoConfirmarVenda;

    @FXML
    private Button botaoVoltarCarteira;

    @FXML
    private Button carteirasMenuBotao;

    @FXML
    private AnchorPane conteudoScrollPane;

    @FXML
    private Label errorLabel;

    @FXML
    private Button historicoMenuBotao;

    @FXML
    private Label labelCarteiraNome;

    @FXML
    private Button negociarMenuBotao;

    @FXML
    private Label nomeUserLabel;

    @FXML
    private Label precoUnidade;

    @FXML
    private Label quantidadePrecoLabel;

    @FXML
    private Spinner quantidadeSpinner;

    @FXML
    private Label saldoUserLabel;

    @FXML
    private Label nomeAcao;
    @FXML
    private ScrollPane scrollPane;
    private String nome;
    private double saldo;
    private String id;
    private String carteiraNome;
    private double precoUnidadeValor;
    private ListaEncadeada<Double> listaPreco;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    ObjectSaveManager obj = new ObjectSaveManager();
    ObservableList<String[]> listaAcoes = FXCollections.observableArrayList();
    private String nomeAcaoValor;
    private double precoTotal;
    private String nomeEmpresa;
    private String tipoObj;
    private String tipoCaminhoValor;

    @FXML
    public void initialize(){
        userInfo();
        labelCarteiraNome.setText(carteiraNome);
        setAcoes();
        setAcoesLabel();
    }
    @FXML
    public void OnActionBotaoConfirmarCompra(){
        LocalDateTime horaDaVenda = LocalDateTime.now();
        int quantidadeValor = (Integer) quantidadeSpinner.getValue();
        Historico historico = new Historico(0, carteiraNome, nome, nomeEmpresa , nomeAcaoValor,precoUnidadeValor, precoTotal, quantidadeValor,horaDaVenda);
        System.out.println(historico.toString());
        atualizarSaldo(precoTotal);

        atualizarQuantidade((Integer) quantidadeSpinner.getValue(), nomeAcaoValor);
        historico.insertVenda(historico);
        atualizarQuantidadeGlobal(quantidadeValor,nomeAcaoValor, tipoCaminhoValor);
        setAcoesLabel();
        obj.updateObject("SALDO", String.valueOf(saldo + precoTotal));
        sceneSwitcher.switchScene("/fxml/menuCarteirasAcoes.fxml");

    }

    public void setAcoesLabel(){
        for (int i = 0; i < listaAcoes.size(); i++) {
            Label label = new Label(Arrays.toString(listaAcoes.get(i)));
            Button button = new Button("Botão " + i);

            button.getProperties().put("labelValue", label.getText());

            HBox hbox = new HBox(label, button);
            hbox.setStyle("-fx-background-color: #e3e1e1");
            hbox.setPadding(new Insets(0, 10, 0, 0));
            hbox.setAlignment(Pos.CENTER);
            hbox.setLayoutY(i * 50);
            conteudoScrollPane.getChildren().add(hbox);

            int finalI = i;
            button.setOnAction(event -> {
                String listaAcao = Arrays.toString(listaAcoes.get(finalI));
                String[] arrayAcao =listaAcao.split(" ");
                int  quantidadeSpinnerValor = Integer.parseInt(arrayAcao[arrayAcao.length - 1].replaceAll("[^0-9]", "")) ;
                nomeAcaoValor = arrayAcao[3].replaceAll("[,.]", "");
                tipoCaminhoValor =  arrayAcao[2].replaceAll("[,.]", "");
                precoUnidadeValor = setPrecoAcaoUnidade(nomeAcaoValor, tipoCaminhoValor);

                checarUltimaAtualizacao(nomeAcaoValor);
                setQuantidadeSpinner(quantidadeSpinnerValor);
                nomeAcao.setText(nomeAcaoValor);
                precoUnidade.setText(java.lang.String.valueOf(precoUnidadeValor));

            });
        }
    }



    public void setQuantidadeSpinner(int quantidadeMax){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, quantidadeMax, 0);
        quantidadeSpinner.setValueFactory(valueFactory);

        quantidadeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            int quantidade = (int) newValue;
            precoTotal = quantidade * precoUnidadeValor;
            precoTotal = Math.round(precoTotal * 100.0) / 100.0;
            quantidadePrecoLabel.setText("Preço: " + precoTotal);
        });
    }



    public void setAcoes(){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/acoesnacarteira.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3) {
                    String idInvestidor = partes[0];
                    String nomeCarteira = partes[1];

                    if (id.equals(idInvestidor) && carteiraNome.equals(nomeCarteira)) {

                        listaAcoes.add(partes);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    public Double setPrecoAcaoUnidade(String ticker, String tipoAcao){

            String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoAcao +".txt";
            double valorUnitario = 0;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                while ((linha = bufferedReader.readLine()) != null) {
                    String[] partes = linha.split(" ");
                    if (partes.length >= 3) {
                        String tickerTxt = partes[1];
                        if (ticker.equals(tickerTxt)) {
                            valorUnitario = Double.parseDouble(partes[2]);
                            nomeEmpresa = partes[0];
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro na leitura do arquivo: " + e.getMessage());
            }
        return valorUnitario;
    }

    public void atualizarQuantidade(int quantidadeAtivosVendidos, String ticker) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/acoesnacarteira.txt";
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4 && ticker.equals(partes[3])) {
                    int novaQuantidade = Integer.parseInt(partes[4]) - quantidadeAtivosVendidos;
                    if (novaQuantidade > 0) {
                        partes[4] = String.valueOf(novaQuantidade);
                        linha = String.join(" ", partes);
                    } else {
                        continue;
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
                if (!linha.isEmpty()) {
                    printWriter.println(linha);
                }
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
                if (partes.length >= 4 && partes[0].equals(id)) {
                    String novoSaldoStr = String.valueOf(novoSaldo + Double.parseDouble(partes[2]));
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

    public void atualizarQuantidadeGlobal(int quantidadeAtivosVendidos, String ticker,  String tipoAcao) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/" + tipoAcao + ".txt";
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3 && partes[1].equals(ticker)) {
                    int novaQuantidade = quantidadeAtivosVendidos + Integer.parseInt(partes[3]);
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
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminhoValor+".txt";
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
    public void OnActionBotaoVoltarCarteira(){
        obj.removeObject("CARTEIRAACESSADA");
        sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");
    }

    public void userInfo(){
        nome = (String) obj.getObject("NOME");
        id = (String) obj.getObject("ID");
        tipoObj = (String) obj.getObject("TIPO");

        carteiraNome = (String) obj.getObject("CARTEIRAACESSADA");
        String saldoStr = (String) obj.getObject("SALDO");
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            saldo = 0.0;
        }

        nomeUserLabel.setText(nome);
        saldoUserLabel.setText(String.valueOf(saldo));
    }

    @FXML
    public void OnActionHistoricoMenuBotao(){sceneSwitcher.switchScene("/fxml/menuHistorico.fxml");
        obj.removeObject("CARTEIRAACESSADA");
    }
    @FXML
    public void OnAcitonCarteirasMenuBotao(){sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");
        obj.removeObject("CARTEIRAACESSADA");
    }
    @FXML
    public void OnActionAltaBotao(){sceneSwitcher.switchScene("/fxml/menuEmAlta.fxml");
        obj.removeObject("CARTEIRAACESSADA");
    }
    @FXML
    public void OnActionNegociarMenuBotao(){sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");
        obj.removeObject("CARTEIRAACESSADA");
    }
}
