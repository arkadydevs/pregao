package entidades.ativos;

import java.time.LocalDate;

import javax.management.RuntimeErrorException;

public class Ordinaria extends Ativo {

    public Ordinaria(String codNegociacao, double cotacao, String liquidacao, LocalDate dataLiquidacao, int lote) {
        super(codNegociacao, cotacao, liquidacao, dataLiquidacao, lote);
    
    }
    @Override
    public void setCodNegociacao(String codNegociacao) {
        char n = codNegociacao.charAt(4);
        if(n != 3){
            throw new RuntimeErrorException(null, "Código da negociação não é ordinário.");
        }
        this.codNegociacao = codNegociacao;
    }
    
    
}