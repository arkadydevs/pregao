package com.example.pregao2.entidades;

import com.example.pregao2.entidades.investidores.InvestidorFisico;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;

import java.io.*;
import java.util.List;

public class AcoesNaCarteira {

    private String idCarteira;
    private String idInvestidor;
    private String tipoTicker;
    private String ticker;
    private int quantidade;

    public AcoesNaCarteira(){}

    public AcoesNaCarteira(String idCarteira, String idInvestidor, String tipoTicker, String ticker, int quantidade) {
        this.idCarteira = idCarteira;
        this.idInvestidor = idInvestidor;
        this.tipoTicker = tipoTicker;
        this.ticker = ticker;
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


    public String getTipoTicker() {
        return tipoTicker;
    }

    public void setTipoTicker(String tipoTicker) {
        this.tipoTicker = tipoTicker;
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
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linha.split(" ");
                if (partes.length >= 4 && partes[1].equals(acoesNaCarteira.getIdCarteira()) && partes[3].equals(acoesNaCarteira.getTicker())) {
                    int quantidadeAtual = Integer.parseInt(partes[4]);
                    quantidadeAtual += acoesNaCarteira.getQuantidade();
                    partes[4] = String.valueOf(quantidadeAtual);
                    linha = String.join(" ", partes);
                    tickerJaExiste = true;
                }
                linhas.addElemento(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
            return;
        }

        if (!tickerJaExiste) {
            String novaLinha = acoesNaCarteira.getIdInvestidor() + " " + acoesNaCarteira.getIdCarteira() + " "+ acoesNaCarteira.getTipoTicker() + " "+ acoesNaCarteira.getTicker() + " " + acoesNaCarteira.getQuantidade();
            linhas.addElemento(novaLinha);
        }

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (int i = 0; i < linhas.getTamanho(); i++) {
                printWriter.println(linhas.get(i));
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "AcoesNaCarteira{" +
                "idCarteira='" + idCarteira + '\'' +
                ", idInvestidor='" + idInvestidor + '\'' +
                ", ticker='" + ticker + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
