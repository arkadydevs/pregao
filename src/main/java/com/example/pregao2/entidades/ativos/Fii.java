package com.example.pregao2.entidades.ativos;

import java.io.*;

public class Fii extends Ativo {

    public Fii(){

    }

    public Fii(String empresa, String codNegociacao, double cotacao, int lote) {
        super(empresa, codNegociacao, cotacao, lote);
    }

    @Override
    public void setCodNegociacao(String codNegociacao) {
        if(codNegociacao.length() != 6 || !Character.isLetter(codNegociacao.charAt(0)) || !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
                !Character.isLetter(codNegociacao.charAt(3)) || codNegociacao.charAt(4) !='1' ||
                codNegociacao.charAt(5) !='1' || codNegociacao.charAt(6) !='B'){
            this.codNegociacao = codNegociacao;
        } else{
            throw new IllegalArgumentException("Código de negociação inválido");
        }
    }

    public void insert(Fii fii) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/fii.txt";


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(fii.getEmpresa() + " " +fii.getCodNegociacao() + " " + fii.getCotacao() + " " + fii.getLote());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

    public String buscarEmpresaPeloTicket(String ticket) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/fii.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 2) {
                    String codNegociacao = partes[1];
                    String empresa = partes[0];
                    if (ticket.equals(codNegociacao)) {
                        return empresa;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
        return null;
    }

}
