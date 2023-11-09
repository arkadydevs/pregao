package com.example.pregao2.entidades;

import com.example.pregao2.entidades.ativos.Fii;

import java.io.*;
import java.time.LocalDateTime;

public class Historico {

    private int id;
    private String idCarteira;
    private String comprador;
    private String empresa;
    private String ticket;
    private double precoUnitario;
    private double precoTotal;
    private int unidadesCompradas;
    private LocalDateTime data;

    public Historico() {
    }

    public Historico(int id, String idCarteira,String comprador, String empresa, String ticket, double precoUnitario, double precoTotal, int unidadesCompradas, LocalDateTime data) {
        this.id = id;
        this.idCarteira = idCarteira;
        this.comprador = comprador;
        this.empresa = empresa;
        this.ticket = ticket;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
        this.unidadesCompradas = unidadesCompradas;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(String idCarteira) {
        this.idCarteira = idCarteira;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getUnidadesCompradas() {
        return unidadesCompradas;
    }

    public void setUnidadesCompradas(int unidadesCompradas) {
        this.unidadesCompradas = unidadesCompradas;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }



    public void insert(Historico historico) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/historico.txt";
        int novoId = obterProximoId(caminhoArquivo);


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(novoId+ " "+ historico.getIdCarteira() +" " + historico.getComprador() + " " + historico.getEmpresa() + " " + historico.getTicket() + " " + historico.getPrecoUnitario() + " " + historico.getPrecoTotal() + " "  + historico.getUnidadesCompradas() + " " + getData());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }

    public void insertVenda(Historico historico) {
        String caminhoArquivo = "src/main/java/com/example/pregao2/bancos_de_dados/historicovendas.txt";
        int novoId = obterProximoId(caminhoArquivo);


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(novoId+ " "+ historico.getIdCarteira() +" " + historico.getComprador() + " " + historico.getEmpresa() + " " + historico.getTicket() + " " + historico.getPrecoUnitario() + " " + historico.getPrecoTotal() + " "  + historico.getUnidadesCompradas() + " " + getData());

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


    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", idCarteira='" + idCarteira + '\'' +
                ", comprador='" + comprador + '\'' +
                ", empresa='" + empresa + '\'' +
                ", ticket='" + ticket + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", precoTotal=" + precoTotal +
                ", unidadesCompradas=" + unidadesCompradas +
                ", data=" + data +
                '}';
    }

}
