package entidades.ativos;

import java.time.LocalDate;

import javax.management.RuntimeErrorException;

public abstract class Ativo{


    //git bababababababaab

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
        if(validarCodNegociacao() == true){
        this.codNegociacao = codNegociacao;
        } else{
            throw new RuntimeErrorException(null, "Código de negociação inválido");
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