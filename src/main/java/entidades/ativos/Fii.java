package entidades.ativos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Fii extends Ativo {


    public Fii(String codNegociacao, double cotacao, int lote) {
        super(codNegociacao, cotacao, lote);

    }

    @Override
    public void setCodNegociacao(String codNegociacao) {
        if(codNegociacao.length() != 6 || !Character.isLetter(codNegociacao.charAt(0)) || !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
                !Character.isLetter(codNegociacao.charAt(3)) || codNegociacao.charAt(4) !='1' ||
                codNegociacao.charAt(5) !='1' || codNegociacao.charAt(6) !='B'){
            this.codNegociacao = codNegociacao;
        } else{
            throw new IllegalArgumentException("Código de negociação inválido");
        }
    }

    public void insert(Fii fii) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\fii.txt";


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(fii.getCodNegociacao() + " " + fii.getCotacao() + " " + fii.getLote());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }


}
