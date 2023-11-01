package bancos_de_dados;

import entidades.investidores.InvestidorJuridico;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Registro {
    private String bancoDeDados;
    private String dados;

    public Registro(String bancoDeDados, InvestidorJuridico in) {
        this.setBancoDeDados(bancoDeDados);
        this.setDados(dados);
    }

    public String getBancoDeDados() {
        return bancoDeDados;
    }

    public void setBancoDeDados(String bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }

    public void insert(Registro registro) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\" + registro.getBancoDeDados() + ".txt";

        try (FileWriter fileWriter = new FileWriter(caminhoArquivo, true);
            PrintWriter printWriter = new PrintWriter(fileWriter)) {
            
            printWriter.println();
            printWriter.println(registro.getDados());
            

        } catch (IOException e) {
            System.err.println("Erro ao adicionar registro: " + e.getMessage());
        }
    }
}
