package com.example.pregao2.controller;

import com.example.pregao2.MainApp;
import com.example.pregao2.model.ObjectSaveManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MenuCarteirasAcoesController {

    @FXML
    public Label labelCarteiraNome;
    @FXML
    private Button addCarteira;

    @FXML
    private TextField addCarteiraTextField;

    @FXML
    private Button altaBotao;

    @FXML
    private Button botaoVoltarCarteira;

    @FXML
    private Button carteirasMenuBotao;

    @FXML
    private AnchorPane conteudoScrollPane;
    @FXML
    private Button historicoMenuBotao;

    @FXML
    private Button negociarMenuBotao;

    @FXML
    private Label nomeUserLabel;

    @FXML
    private Label saldoUserLabel;

    @FXML
    private ScrollPane scrollPane;
    private String nome;
    private double saldo;
    private String id;
    private String carteiraNome;
    SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
    ObjectSaveManager obj = new ObjectSaveManager();
    ObservableList<String[]> listaAcoes = FXCollections.observableArrayList();


    @FXML
    public void initialize(){
        userInfo();
        labelCarteiraNome.setText(carteiraNome);
        setAcoes();
        setAcoesLabel();
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

            button.setOnAction(event -> {
                String labelValue = (String) button.getProperties().get("labelValue");
                ObjectSaveManager obj = new ObjectSaveManager();
                obj.saveObject("CARTEIRAACESSADA", labelValue);
                sceneSwitcher.switchScene("/fxml/menuCarteirasAcoes.fxml");
            });
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
                    String nomeCarteira = partes[1];

                    if (id.equals(idInvestidor) && carteiraNome.equals(nomeCarteira)) {
                        System.out.println(idInvestidor);
                        System.out.println(partes[0]);
                        System.out.println(partes[2]);

                        listaAcoes.add(partes);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
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
