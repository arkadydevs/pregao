package com.example.pregao2.entidades;

import com.example.pregao2.entidades.ativos.Ativo;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;

import java.io.*;

public class Carteira {

    private int id;
    private String nomeCarteira;
    private String idInvestidor;

    public Carteira(){

    }

    public Carteira(int id, String nomeCarteira, String idInvestidor) {
        this.id = id;
        this.nomeCarteira = nomeCarteira;
        this.idInvestidor = idInvestidor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCarteira() {
        return nomeCarteira;
    }

    public void setNomeCarteira(String nomeCarteira) {
        this.nomeCarteira = nomeCarteira;
    }

    public String getIdInvestidor() {
        return idInvestidor;
    }

    public void setIdInvestidor(String idInvestidor) {
        this.idInvestidor = idInvestidor;
    }

    public void insert(Carteira carteira){
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/carteira.txt";
        int novoId = obterProximoId(caminhoArquivo);


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(novoId + " " + carteira.getIdInvestidor() + " " + carteira.getNomeCarteira());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

    public static int obterProximoId(String caminhoArquivo) {
        int novoId = 1;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 1 && !partes[0].isEmpty()) {
                    int idLido = Integer.parseInt(partes[0]);
                    if (idLido >= novoId) {
                        novoId = idLido + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        return novoId;
    }
}
