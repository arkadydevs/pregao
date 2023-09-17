package entidades.investidores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import entidades.Carteira;
import estruturas_de_dados.lista.ListaEncadeada;

public class InvestidorFisico extends Investidor{
    private String cpf;

    public InvestidorFisico(int id, String nome, double saldo, ListaEncadeada<Carteira> carteiras, String cpf) {
        super(id, nome, saldo, carteiras);
        this.setCpf(cpf);
    }
    
    public String getCpf() {

        return cpf;
    }
    public void setCpf(String cpf) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
            this.cpf = cpf;
        } catch (InvalidStateException e) {
            System.err.println("CPF inválido: " + e.getInvalidMessages());
        }
    }

    public boolean verificarCPF(String cpf) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\investidorfisico.txt"; 
    
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int numeroLinha = 1;
    
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 4) { 
                    String cpfBanco = partes[3].trim(); 
                    if (cpfBanco.equals(cpf)) {
                        return true;
                    }
                } else {
                    System.err.println("Linha incompleta na linha " + numeroLinha);
                }
    
                numeroLinha++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    
        return false;
    }

}
