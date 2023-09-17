package arkadydevs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExemplosAcoes {
    public static void main(String[] args) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados"; // Caminho relativo para o arquivo TXT

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Faça algo com cada linha lida do arquivo de ações
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}