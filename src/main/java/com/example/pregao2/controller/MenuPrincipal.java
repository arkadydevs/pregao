package com.example.pregao2.controller;

import entidades.investidores.InvestidorJuridico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import model.ObjectSaveManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuPrincipal{
    @FXML
    private Label nomeUserLabel;
    @FXML
    private Label saldoUserLabel;
    private String nome;
    private double saldo;

    @FXML
    public void initialize() {
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
