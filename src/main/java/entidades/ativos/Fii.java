package entidades.ativos;

import java.time.LocalDate;

public class Fii extends Ativo {

    public Fii(String codNegociacao, double cotacao, String liquidacao, LocalDate dataLiquidacao, int lote) {
        super(codNegociacao, cotacao, liquidacao, dataLiquidacao, lote);
    
    }

    
}
