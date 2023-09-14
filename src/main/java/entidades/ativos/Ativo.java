package entidades.ativos;

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
        this.codNegociacao = codNegociacao;
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
        this.lote = lote;
    }

}