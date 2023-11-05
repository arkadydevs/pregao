package com.example.pregao2.entidades.investidores;

import com.example.pregao2.MainApp;
import com.example.pregao2.controller.SceneSwitcher;
import com.example.pregao2.entidades.Carteira;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import com.example.pregao2.model.ObjectSaveManager;


import java.io.*;

public class InvestidorJuridico extends Investidor implements Serializable{
    private String cnpj;

    public InvestidorJuridico() {
    }

    public InvestidorJuridico(int id, String nome, double saldo, String senha, ListaEncadeada<Carteira> carteiras, String cnpj) {
        super(id, nome, saldo, senha, carteiras);
        this.setCnpj(cnpj);
    }
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void insert(InvestidorJuridico investidor) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorjuridico.txt";

        int novoId = obterProximoId(caminhoArquivo);

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(novoId+"J" + " " + investidor.getNome() + " " + investidor.getSaldo() + " " + investidor.getCnpj() + " " + investidor.getSenha());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

    private int obterProximoId(String caminhoArquivo) {
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


    public void loginJuridico(String cnpj, String senha) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorjuridico.txt";
        InvestidorJuridico investidorEncontrado = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4) {
                    String cnpjLido = partes[3];
                    String senhaLida = partes[4];

                    if (cnpj.equals(cnpjLido) && senha.equals(senhaLida)) {
                        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
                        System.out.println("Botão InitialButton clicado");
                        sceneSwitcher.switchScene("/fxml/escolhaJuridico.fxml");
                        return;
                    }
                }
            }
            System.out.println("Investidor não encontrado ou senha incorreta");
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo loginJuridico: " + e.getMessage());
        }
    }

    public String buscarSaldo(String cnpj) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorjuridico.txt";
        ObjectSaveManager obj = new ObjectSaveManager();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4) {
                    String id = partes[0];
                    String nome = partes[1];
                    String saldoStr = partes[2];
                    String cnpjLido = partes[3];

                    if (cnpj.equals(cnpjLido)) {
                        String tipoObj = "investidorjuridico";
                        obj.cleanObjects();
                        obj.saveObject("ID", id);
                        obj.saveObject("TIPO", tipoObj);
                        obj.saveObject("CNPJ", cnpjLido);
                        obj.saveObject("NOME", nome);
                        obj.saveObject("SALDO", saldoStr);
                        obj.printMap();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        return null;
    }


}
