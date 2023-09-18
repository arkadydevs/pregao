package bancos_de_dados;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class Registro {
    private String bancoDeDados;
    private String dados;

    public Registro(String bancoDeDados, String dados) {
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
