package entities.ativos;

import java.time.LocalDate;

import javax.management.RuntimeErrorException;

public class Fii extends Ativo {

    public Fii(String codNegociacao, double cotacao, String liquidacao, LocalDate dataLiquidacao, int lote) {
        super(codNegociacao, cotacao, liquidacao, dataLiquidacao, lote);
    
    }

    @Override
    public void setCodNegociacao(String codNegociacao) {
        if (codNegociacao.length() != 7 || !Character.isLetter(codNegociacao.charAt(0)) ||
        !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
        !Character.isLetter(codNegociacao.charAt(3)) || codNegociacao.charAt(4) !=1 || 
        codNegociacao.charAt(5) !=1 || codNegociacao.charAt(6) !='B') {
        throw new RuntimeErrorException(null, "Código da negociação inválido.");
    }
        this.codNegociacao = codNegociacao;
    }

    
}
