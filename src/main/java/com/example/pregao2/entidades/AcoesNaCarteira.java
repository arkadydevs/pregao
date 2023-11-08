package com.example.pregao2.entidades;

import com.example.pregao2.entidades.investidores.InvestidorFisico;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;

import java.io.*;
import java.util.List;

public class AcoesNaCarteira {

    private String idCarteira;
    private String idInvestidor;
    private String ticker;
    private double precoPago;
    private int quantidade;

    public AcoesNaCarteira(){}

    public AcoesNaCarteira(String idCarteira, String idInvestidor, String ticker, double precoPago, int quantidade) {
        this.idCarteira = idCarteira;
        this.idInvestidor = idInvestidor;
        this.ticker = ticker;
        this.precoPago = precoPago;
        this.quantidade = quantidade;
    }

    public String getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(String idCarteira) {
        this.idCarteira = idCarteira;
    }

    public String getIdInvestidor() {
        return idInvestidor;
    }

    public void setIdInvestidor(String idInvestidor) {
        this.idInvestidor = idInvestidor;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrecoPago() {
        return precoPago;
    }

    public void setPrecoPago(double precoPago) {
        this.precoPago = precoPago;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void insert(AcoesNaCarteira acoesNaCarteira) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/acoesnacarteira.txt";
        boolean tickerJaExiste = false;
        ListaEncadeada<String> linhas = new ListaEncadeada<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4 && partes[1].equals(acoesNaCarteira.getIdCarteira()) && partes[2].equals(acoesNaCarteira.getTicker())) {
                    int quantidadeAtual = Integer.parseInt(partes[4]);
                    double precoAtual = Double.parseDouble(partes[3]);
                    quantidadeAtual += acoesNaCarteira.getQuantidade();
                    precoAtual += acoesNaCarteira.getPrecoPago();
                    partes[3] = String.valueOf(precoAtual);
                    partes[4] = String.valueOf(quantidadeAtual);
                    linha = String.join(" ", partes);
                    tickerJaExiste = true;
                }
                linhas.addElemento(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        if (!tickerJaExiste) {
            try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
                 PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.println(acoesNaCarteira.getIdInvestidor() + " " + acoesNaCarteira.getIdCarteira() + " " + acoesNaCarteira.getTicker() + " " + acoesNaCarteira.getPrecoPago() + " " + acoesNaCarteira.getQuantidade());
            } catch (IOException e) {
                System.err.println("Erro ao adicionar registro: " + e.getMessage());
            }
        }

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (int i = 0; i < linhas.getTamanho(); i++) {
                printWriter.println(linhas.get(i));
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }




}
