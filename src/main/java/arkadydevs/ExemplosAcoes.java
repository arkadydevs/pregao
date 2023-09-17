package arkadydevs;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExemplosAcoes {
    public static void main(String[] args) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\fii.txt"; // Substitua pelo caminho do seu arquivo
        int numeroLinhaDesejada = 3; // Substitua pelo número da linha desejada

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int numeroLinha = 1;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String id = partes[0].trim(); // Pega o primeiro valor (id) e remove espaços em branco
                if (id.equals("VINO11B")) {
                    String cotacao = partes[1].trim(); // Pega o valor da segunda coluna (cotacao) e remove espaços em branco
                    String bab = partes[2].trim();
                    System.out.println("ID da Linha " + numeroLinha + ": " + id);
                    System.out.println("Cotação da Linha " + numeroLinha + ": " + cotacao);
                }
                

                numeroLinha++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
