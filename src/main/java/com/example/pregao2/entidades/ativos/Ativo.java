package com.example.pregao2.entidades.ativos;


public abstract class Ativo{

    protected String codNegociacao; //XXXXY X são letras Y é um número
    protected double cotacao;
    protected String empresa;
    protected int lote;

    public Ativo(){

    }

    public Ativo(String empresa, String codNegociacao, double cotacao, int lote) {
        this.setEmpresa(empresa);
        this.setCodNegociacao(codNegociacao);
        this.setCotacao(cotacao);
        this.setLote(lote);
    }


    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCodNegociacao() {
        return codNegociacao;

    }

    public void setCodNegociacao(String codNegociacao) {
        if(validarCodNegociacao()){
            this.codNegociacao = codNegociacao;
        } else{
            throw new IllegalArgumentException("Código de negociação inválido");
        }
    }

    public double getCotacao() {
        return cotacao;
    }

    public void setCotacao(double cotacao) {
        this.cotacao = cotacao;
    }


    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        if(lote < 1){
            throw new IllegalArgumentException("Não se pode criar uma quantidade de ações negativas");
        }
        this.lote = lote;
    }

    public boolean validarCodNegociacao(){
        if (codNegociacao.length() != 5 || !Character.isLetter(codNegociacao.charAt(0)) ||
                !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
                !Character.isLetter(codNegociacao.charAt(3)) || !Character.isDigit(codNegociacao.charAt(4))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fii{" +
                "empresa='" + empresa + '\'' +
                ", codNegociacao='" + codNegociacao + '\'' +
                ", cotacao=" + cotacao +
                ", lote=" + lote +
                '}';
    }

}