package entidades.ativos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Preferencial extends Ativo {

    public Preferencial(String codNegociacao, double cotacao, int lote) {
        super(codNegociacao, cotacao, lote);

    }

    public void insert(Preferencial preferencial) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\preferencial.txt";


        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(preferencial.getCodNegociacao() + " " + preferencial.getCotacao() + " " + preferencial.getLote());

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }


    @Override
    public void setCodNegociacao(String codNegociacao) {
        if(codNegociacao.length() != 4 || !Character.isLetter(codNegociacao.charAt(0)) ||
                !Character.isLetter(codNegociacao.charAt(1)) || !Character.isLetter(codNegociacao.charAt(2)) ||
                !Character.isLetter(codNegociacao.charAt(3)) || codNegociacao.charAt(4) !='4'){
            this.codNegociacao = codNegociacao;
        } else{
            throw new IllegalArgumentException("Código de negociação inválido");
        }
    }


}
