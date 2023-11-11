package com.example.pregao2.entidades;

import com.example.pregao2.estruturas_de_dados.pilha.Pilha;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class MudancaDePreco {
    private String tipoAtivo;
    private String ticker;
    private LocalDate ultimaAtualizacao;
    private String pilhaPrecos;

    public MudancaDePreco(String tipoAtivo, String ticker, String pilhaPrecos, LocalDate ultimaAtualizacao) {
        this.tipoAtivo = tipoAtivo;
        this.ticker = ticker;
        this.pilhaPrecos = pilhaPrecos;
        this.ultimaAtualizacao = ultimaAtualizacao;

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

    public String getPilhaPrecos() {
        return pilhaPrecos;
    }

    public void setPilhaPrecos(String pilhaPrecos) {
        this.pilhaPrecos = pilhaPrecos;
    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public void insert(MudancaDePreco mudancaDePreco){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/mudancadepreco.txt";
        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println( mudancaDePreco.getTipoAtivo() +" " + mudancaDePreco.getTicker() + " " + mudancaDePreco.getPilhaPrecos() + " "+ mudancaDePreco.getUltimaAtualizacao());
        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

}
