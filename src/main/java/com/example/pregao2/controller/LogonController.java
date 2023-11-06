package com.example.pregao2.controller;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

import com.example.pregao2.MainApp;
import com.example.pregao2.entidades.investidores.InvestidorFisico;
import com.example.pregao2.entidades.investidores.InvestidorJuridico;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class LogonController {

    @FXML
    private CheckBox checkBoxFisico;
    @FXML
    private CheckBox checkBoxJuridico;
    @FXML
    private Button logonButton;

    @FXML
    private Label errorTextLabel;
    @FXML
    private TextField identificadorTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField senhaTextField;
    @FXML
    private TextField saldoTextField;

    @FXML
    public void initialize(){
        nomeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                nomeTextField.setText(oldValue);
            }
        });
        senhaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                senhaTextField.setText(oldValue);
            }
        });
        saldoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                saldoTextField.setText(oldValue);
            }
        });
    }

    @FXML
    public void checkBoxFisicoTrue(ActionEvent event) {
        if (checkBoxFisico.isSelected()) {
            checkBoxJuridico.setSelected(false);
            nomeTextField.setPromptText("Nome de Usuário");
            identificadorTextField.setPromptText("CPF");
        }
    }

    @FXML
    public void checkBoxJuridicoTrue(ActionEvent event) {
        if (checkBoxJuridico.isSelected()) {
            checkBoxFisico.setSelected(false);
            nomeTextField.setPromptText("Nome da Empresa");
            identificadorTextField.setPromptText("CNPJ");

        }
    }

    @FXML
    public void onActionLogonButton(ActionEvent event) {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);

        if (checkBoxJuridico.isSelected()) {
            InvestidorJuridico investidor = new InvestidorJuridico();
            if ((Objects.equals(identificadorTextField.getText(), "")) || (Objects.equals(senhaTextField.getText(), "")) || (Objects.equals(saldoTextField.getText(), "")) || (Objects.equals(nomeTextField.getText(), ""))) {
                errorTextLabel.setText("PREENCHER TODOS OS CAMPOS");
            } else {
                int teste = 0;

                CNPJValidator validator = new CNPJValidator();
                investidor.setNome(nomeTextField.getText());


                try {
                    errorTextLabel.setText("");
                    validator.assertValid(identificadorTextField.getText());
                    investidor.setCnpj(identificadorTextField.getText());
                    teste++;
                } catch (InvalidStateException e) {
                    errorTextLabel.setText("CNPJ INVÁLIDO");
                }
                validarNomeIdentificador(nomeTextField.getText(), identificadorTextField.getText(), "investidorjuridico");
                try {
                    investidor.setSenha(senhaTextField.getText());
                    teste++;

                } catch (RuntimeException e) {
                    errorTextLabel.setText("SENHA PRECISA TER NO MÍNIMO 6 CARACTERES");
                }

                try {
                    investidor.setSaldo(Double.parseDouble(saldoTextField.getText()));
                    teste++;

                } catch (IllegalArgumentException e) {
                    errorTextLabel.setText("SALDO NÃO PODE SER MENOR QUE ZERO");
                }

                if (teste == 3) {
                    investidor.insert(investidor);
                    System.out.println("Botão InitialButton clicado");
                    sceneSwitcher.switchScene("/fxml/menuNegociar.fxml");
                    sceneSwitcher.switchScene("/fxml/pregaoInicialScreen.fxml");


                }
            }
        } else {
            if (checkBoxFisico.isSelected()) {

                InvestidorFisico investidor = new InvestidorFisico();
                if ((Objects.equals(identificadorTextField.getText(), "")) || (Objects.equals(senhaTextField.getText(), "")) || (Objects.equals(saldoTextField.getText(), "")) || (Objects.equals(nomeTextField.getText(), ""))) {
                    errorTextLabel.setText("PREENCHER TODOS OS CAMPOS");
                } else {
                    int teste = 0;

                    CPFValidator validator = new CPFValidator();
                    investidor.setNome(nomeTextField.getText());

                    try {
                        errorTextLabel.setText("");
                        validator.assertValid(identificadorTextField.getText());
                        investidor.setCpf(identificadorTextField.getText());
                        teste++;
                    } catch (InvalidStateException e) {
                        errorTextLabel.setText("CPF INVÁLIDO");
                    }
                    validarNomeIdentificador(nomeTextField.getText(), identificadorTextField.getText(), "investidorfisico");

                    try {
                        investidor.setSenha(senhaTextField.getText());
                        teste++;

                    } catch (RuntimeException e) {
                        errorTextLabel.setText("SENHA PRECISA TER NO MÍNIMO 6 CARACTERES");
                    }

                    try {
                        investidor.setSaldo(Double.parseDouble(saldoTextField.getText()));
                        teste++;

                    } catch (IllegalArgumentException e) {
                        errorTextLabel.setText("SALDO NÃO PODE SER MENOR QUE ZERO");
                    }

                    if (teste == 3) {
                        investidor.insert(investidor);
                        System.out.println("Botão InitialButton clicado");
                        sceneSwitcher.switchScene("/fxml/pregaoInicialScreen.fxml");
                    }
                }
            }
        }
    }

    public void validarNomeIdentificador(String nome, String identificador, String tipoCaminho) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/"+ tipoCaminho+".txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4) {
                    String identificadorLido = partes[3];
                    String nomeLido = partes[1];

                    if (nome.equals(nomeLido)) {
                        errorTextLabel.setText("NOME DE USUÁRIO JÁ CADASTRADO");
                        throw new RuntimeException("NOME DE USUÁRIO JÁ CADASTRADO");
                    }
                    else if(identificador.equals(identificadorLido)){
                        errorTextLabel.setText("CPF OU CNPJ JÁ CADASTRADO");
                        throw new RuntimeException("CPF OU CNPJ JÁ CADASTRADO");

                    }
                }
            }
            System.out.println("Investidor não encontrado ou senha incorreta");
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }
}
