package entidades.ativos;

import entidades.investidores.InvestidorFisico;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;


public abstract class Ativo{

    protected String codNegociacao; //XXXXY X são letras Y é um número
    private double cotacao;
    private String liquidacao;
    private LocalDate dataLiquidacao;
    private int lote;

    public Ativo(String codNegociacao, double cotacao, String liquidacao, LocalDate dataLiquidacao, int lote) {
        this.setCodNegociacao(codNegociacao);
        this.setCotacao(cotacao);
        this.setLiquidacao(liquidacao);
        this.setDataLiquidacao(dataLiquidacao);
        this.setLote(lote);
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

    public String getLiquidacao() {
        return liquidacao;
    }

    public void setLiquidacao(String liquidacao) {
        this.liquidacao = liquidacao;
    }

    public LocalDate getDataLiquidacao() {
        return dataLiquidacao;
    }

    public void setDataLiquidacao(LocalDate dataLiquidacao) {
        this.dataLiquidacao = dataLiquidacao;
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

}