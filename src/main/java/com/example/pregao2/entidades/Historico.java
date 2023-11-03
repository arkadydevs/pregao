package com.example.pregao2.entidades;

import java.time.LocalDateTime;

public class Historico {

    private int id;
    private String comprador;
    private String empresa;
    private String ticket;
    private double precoUnitario;
    private double precoTotal;
    private int unidadesCompradas;
    private LocalDateTime data;

    public Historico() {
    }

    public Historico(int id,String comprador, String empresa, String ticket, double precoUnitario, double precoTotal, int unidadesCompradas, LocalDateTime data) {
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

    public void insert(Historico historico){

    }

    @Override
    public String toString() {
        return "Historico{" +
                "id='" + id+ '\''+
                ",comprador='" + comprador + '\'' +
                ", empresa='" + empresa + '\'' +
                ", ticket='" + ticket + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", precoTotal=" + precoTotal +
                ", unidadesCompradas=" + unidadesCompradas +
                ", data=" + data +
                '}';
    }

}
