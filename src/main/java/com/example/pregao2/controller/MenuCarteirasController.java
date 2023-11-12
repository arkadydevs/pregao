package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Carteira;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.*;
import java.util.Arrays;

public class MenuCarteirasController {

    @FXML
    private AnchorPane conteudoScrollPane;
    @FXML
    private Button addCarteira;
    @FXML
    private TextField addCarteiraTextField;
    @FXML
    private Button altaBotao;
    @FXML
    private Button carteirasMenuBotao;
    @FXML
    private Button historicoMenuBotao;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button negociarMenuBotao;
    @FXML
    private Label nomeUserLabel;
    @FXML
    private Label saldoUserLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private VBox vboxCarteiras;

    private ListaEncadeada<Double> listaPreco = new ListaEncadeada<>();

    @FXML
    private Label totalInvestidoLabel;
    ObservableList<String[]> listaAcoes = FXCollections.observableArrayList();
    private String nome;
    private double saldo;
    private String id;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    ObservableList<String> listaCarteiras = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        userInfo();
        setComboxCarteiras(id);
        setCarteiras();
        setAcoes();
        spinnerTextField();
        double totalInvestido = somarListaPreco(setTotalInvestido());
        System.out.println(setTotalInvestido());
        System.out.println(totalInvestido);
        totalInvestidoLabel.setText(String.valueOf(totalInvestido));
    }

    @FXML
    public void OnActionAddCarteira(ActionEvent event){
        if(addCarteiraTextField.getText().equals("") || addCarteiraTextField.getText() == null){
            errorLabel.setText("É PRECISO INSERIR UM NOME PARA CRIAR UMA NOVA CARTEIRA!");
        }
        else {
            vboxCarteiras.getChildren().clear();

            Carteira carteira = new Carteira();
            carteira.setIdInvestidor(id);
            carteira.setNomeCarteira(addCarteiraTextField.getText());
            carteira.insert(carteira);
            setComboxCarteiras(id);
            setCarteiras();
            sceneSwitcher.switchScene("/fxml/menuCarteiras.fxml");
        }
    }

    public void spinnerTextField() {
        addCarteiraTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                addCarteiraTextField.setText(oldValue);
            }
        });
    }

    public void setCarteiras() {
        VBox contentVBox = new VBox();
        contentVBox.setAlignment(Pos.TOP_CENTER);
        contentVBox.getChildren().clear();

        for (int i = 0; i < listaCarteiras.size(); i++) {
            Label label = new Label(listaCarteiras.get(i));
            Button button = new Button("ACESSAR: " +  label.getText());
            label.setFont(new Font(20));

            button.getProperties().put("labelValue", label.getText());

            HBox hbox = new HBox(label, button);
            hbox.setStyle("-fx-background-color: #e3e1e1");
            hbox.setPadding(new Insets(0, 10, 0, 0));
            hbox.setAlignment(Pos.CENTER);
            hbox.setLayoutY(i * 50);
            contentVBox.getChildren().add(hbox);

            button.setOnAction(event -> {
                String labelValue = (String) button.getProperties().get("labelValue");
                ObjectSaveManager obj = new ObjectSaveManager();
                obj.saveObject("CARTEIRAACESSADA", labelValue);
                sceneSwitcher.switchScene("/fxml/menuCarteirasAcoes.fxml");
            });
        }

        vboxCarteiras.getChildren().add(contentVBox);
    }



    public void setComboxCarteiras(String id){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/carteira.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 2) {
                    String idInvestidor = partes[1];
                    if (id.equals(idInvestidor)) {
                        System.out.println(idInvestidor);
                        listaCarteiras.add(partes[2]);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    public void setAcoes(){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/acoesnacarteira.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 3) {
                    String idInvestidor = partes[0];

                    if (id.equals(idInvestidor)) {

                        listaAcoes.add(partes);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    public ListaEncadeada<Double> setTotalInvestido() {
        for (int i = 0; i < listaAcoes.size(); i++) {
            String[] partes = listaAcoes.get(i);
            if (partes.length >= 5) {
                String tipoAcao = partes[2];
                String ticker = partes[3];
                double quantidade = Double.parseDouble(partes[4]);

                String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/" + tipoAcao + ".txt";

                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
                    String linha;
                    while ((linha = bufferedReader.readLine()) != null) {
                        String[] partesAcao = linha.split(" ");
                        if (partesAcao.length >= 3) {
                            String tickerTxt = partesAcao[1];

                            if (ticker.equals(tickerTxt)) {
                                listaPreco.addElemento(Double.parseDouble(partesAcao[2]) * quantidade);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Erro na leitura do arquivo: " + e.getMessage());
                }
            } else {
                System.err.println("Formato de ação inválido: " + Arrays.toString(partes));
            }
        }

        return listaPreco;
    }


    public double somarListaPreco(ListaEncadeada<Double> listaPreco) {
        double soma = 0;

        for (int i = 0; i < listaPreco.getTamanho(); i++) {
            double precoAtual = listaPreco.get(i).getValor();
            soma = soma +precoAtual;
        }

        return soma;
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
}
