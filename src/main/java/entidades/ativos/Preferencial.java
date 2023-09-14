package entidades.ativos;

import java.time.LocalDate;

import javax.management.RuntimeErrorException;

public class Preferencial extends Ativo {

    public Preferencial(String codNegociacao, double cotacao, String liquidacao, LocalDate dataLiquidacao, int lote) {
        super(codNegociacao, cotacao, liquidacao, dataLiquidacao, lote);
    
    }
    @Override
    public void setCodNegociacao(String codNegociacao) {
        char n = codNegociacao.charAt(4);
        if(n != 4){
            throw new RuntimeErrorException(null, "Código da negociação não é preferencial.");
        }
        this.codNegociacao = codNegociacao;
    }
    
}
