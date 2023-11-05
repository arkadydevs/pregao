package com.example.pregao2.entidades.ativos;

import java.io.*;

public class Ordinaria extends Ativo {

    public Ordinaria(){

    }

    public Ordinaria(String empresa, String codNegociacao, double cotacao, int lote) {
        super(empresa, codNegociacao, cotacao, lote);
    }

    public void insert(Ordinaria ordinaria) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/ordinaria.txt";


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(ordinaria.getEmpresa() + " " +ordinaria.getCodNegociacao() + " " + ordinaria.getCotacao() + " " + ordinaria.getLote());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

    @Override
    public void setCodNegociacao(String codNegociacao) {
        if(codNegociacao.length() != 4 || !Character.isLetter(codNegociacao.charAt(0)) ||
                !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
                !Character.isLetter(codNegociacao.charAt(3)) || codNegociacao.charAt(4) !='3'){
            this.codNegociacao = codNegociacao;
        } else{
            throw new IllegalArgumentException("Código de negociação inválido");
        }
    }

    public String buscarEmpresaPeloTicket(String ticket) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/ordinaria.txt";

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