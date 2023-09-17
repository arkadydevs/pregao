package menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import br.com.caelum.stella.validation.CNPJValidator;
import entidades.investidores.Investidor;
import entidades.investidores.InvestidorJuridico;

public class Menu {
    public Menu(){

    }
    
    public void inicio() {
        CNPJValidator validator = new CNPJValidator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Bem vindo ao Pregão 231!\n Quer logar como:\n\n 1-Pessoa jurídica\n 2-Pessoa física\n");
        int primeiro = sc.nextInt();
        sc.nextLine(); // Consumir a quebra de linha pendente
    
        if (primeiro == 1) {
            System.out.println("Digite o CNPJ da sua empresa: ");
            String cnpj = sc.nextLine();
            // Implemente a verificação do CNPJ aqui
        } else if (primeiro == 2) {
            System.out.println("Digite o seu CPF: ");
            // Verifique se o CPF está no formato ###.###.###-##
            String cpfDigitado = sc.nextLine(); // Remove caracteres não numéricos
    
            String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\investidorfisico.txt";
    
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                int numeroLinha = 1;
    
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(",");
                    if (partes.length >= 4) {
                        // Remova os caracteres não numéricos do CPF lido do arquivo
                        String cpfBanco = partes[3].trim();
                        if (cpfDigitado.equals(cpfBanco)) { // Correção aqui
                            System.out.println("Yes");
                            return; // CPF encontrado, encerrar a função
                        }
                    }
    
                    numeroLinha++;
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }
    
            System.out.println("CPF inválido ou não cadastrado");
        } else {
            System.out.println("Opção inválida");
        }
        sc.close();
    }
    
    
}
