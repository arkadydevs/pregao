package com.example.pregao2.entidades.investidores;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.example.pregao2.MainApp;
import com.example.pregao2.controller.SceneSwitcher;
import com.example.pregao2.entidades.Carteira;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;
import com.example.pregao2.model.ObjectSaveManager;

import java.io.*;

public class InvestidorFisico extends Investidor{
    private String cpf;

    public InvestidorFisico() {
    }

    public InvestidorFisico(int id, String nome, double saldo, String senha, ListaEncadeada<Carteira> carteiras, String cpf) {
        super(id, nome, saldo, senha, carteiras);
        this.setCpf(cpf);
    }


    public String getCpf() {

        return cpf;
    }
    public void setCpf(String cpf) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
            this.cpf = cpf;
        } catch (InvalidStateException e) {
            System.err.println("CPF inválido: " + e.getInvalidMessages());
        }
    }




    public void insert(InvestidorFisico investidor) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorfisico.txt";

        int novoId = obterProximoId(caminhoArquivo);

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(novoId + " " + investidor.getNome() + " " + investidor.getSaldo() + " " + investidor.getCpf() + " " + investidor.getSenha());

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


    public void loginJuridico(String cpf, String senha) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorfisico.txt";
        InvestidorFisico investidorEncontrado = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4) {
                    String cpfLido = partes[3];
                    String senhaLida = partes[4];

                    if (cpf.equals(cpfLido) && senha.equals(senhaLida)) {
                        SceneSwitcher sceneSwitcher = new SceneSwitcher(MainApp.primaryStage);
                        System.out.println("Botão InitialButton clicado");
                        sceneSwitcher.switchScene("/fxml/menuPrincipal.fxml");
                        return;
                    }
                }
            }
            System.out.println("Investidor não encontrado ou senha incorreta");
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    public void buscarSaldo(String cpf) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorfisico.txt";
        ObjectSaveManager obj = new ObjectSaveManager();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4) {
                    String id = partes[0];
                    String nome = partes[1];
                    String saldoStr = partes[2];
                    String cpfLido = partes[3];

                    if (cpf.equals(cpfLido)) {
                        String tipoObj = "investidorfisico";
                        obj.cleanObjects();
                        obj.saveObject("ID", id);
                        obj.saveObject("TIPO", tipoObj);
                        obj.saveObject("CNPJ", cpfLido);
                        obj.saveObject("NOME", nome);
                        obj.saveObject("SALDO", saldoStr);
                        obj.printMap();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }

    /*public void alterarSaldoNoArquivo(String nome, double novoSaldo) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/investidorfisico.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 4) {
                    String nomeLido = partes[1];
                    if (nome.equals(nomeLido)) {
                        partes[2] = String.valueOf(novoSaldo);
                    }
                }
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }
    }*/


}
