package entidades.ativos;

import java.time.LocalDate;

public class Ordinaria extends Ativo {

    public Ordinaria(String codNegociacao, double cotacao, String liquidacao, LocalDate dataLiquidacao, int lote) {
        super(codNegociacao, cotacao, liquidacao, dataLiquidacao, lote);

    }

    @Override
    public void setCodNegociacao(String codNegociacao) {
        if(codNegociacao.length() != 4 || !Character.isLetter(codNegociacao.charAt(0)) ||
                !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
                !Character.isLetter(codNegociacao.charAt(3)) || codNegociacao.charAt(4) !='3'){
            this.codNegociacao = codNegociacao;
        } else{
            throw new IllegalArgumentException("Código de negociação inválido");
        }
    }
}