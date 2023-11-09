package com.example.pregao2.entidades;

import com.example.pregao2.estruturas_de_dados.pilha.Pilha;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MudancaDePreco {
    private String tipoAtivo;
    private String ticker;
    private Pilha<Double> pilhaPrecos;

    public MudancaDePreco(String tipoAtivo, String ticker, Pilha<Double> pilhaPrecos) {
        this.tipoAtivo = tipoAtivo;
        this.ticker = ticker;
        this.pilhaPrecos = pilhaPrecos;
    }

    public String getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(String tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Pilha<Double> getPilhaPrecos() {
        return pilhaPrecos;
    }

    public void setPilhaPrecos(Pilha<Double> pilhaPrecos) {
        this.pilhaPrecos = pilhaPrecos;
    }

    public void insert(MudancaDePreco mudancaDePreco){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/mudancadepreco.txt";
        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println( mudancaDePreco.getTipoAtivo() +" " + mudancaDePreco.getTicker() + " " + mudancaDePreco.getPilhaPrecos());
        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

    public void inserirNovoPreco(String ticker){

    }
}
