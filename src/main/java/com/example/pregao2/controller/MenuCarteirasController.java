package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.Carteira;
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
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    private ComboBox comboBoxCarteiras;
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
    }

    @FXML
    public void OnActionAddCarteira(ActionEvent event){
        Carteira carteira = new Carteira();
        carteira.setIdInvestidor(id);
        carteira.setNomeCarteira(addCarteiraTextField.getText());
        carteira.insert(carteira);
        setComboxCarteiras(id);
        setCarteiras();
    }

    public void setCarteiras(){

        clearCarteiras();
        for (int i = 0; i < listaCarteiras.size(); i++) {
            Label label = new Label(listaCarteiras.get(i));
            Button button = new Button("Botão " + i);
            label.setFont(new Font(20));


            button.getProperties().put("labelValue", label.getText());

            HBox hbox = new HBox(label, button);
            hbox.setStyle("-fx-background-color: #e3e1e1");
            hbox.setPadding(new Insets(0, 10, 0, 0));
            hbox.setAlignment(Pos.CENTER);
            hbox.setLayoutY(i * 50);
            conteudoScrollPane.getChildren().add(hbox);

            button.setOnAction(event -> {
                String labelValue = (String) button.getProperties().get("labelValue");
                ObjectSaveManager obj = new ObjectSaveManager();
                obj.saveObject("CARTEIRAACESSADA", labelValue);
                sceneSwitcher.switchScene("/fxml/menuCarteirasAcoes.fxml");
            });
        }
    }



    public void clearCarteiras() {
        conteudoScrollPane.getChildren().clear();
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
