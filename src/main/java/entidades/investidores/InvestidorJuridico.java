package entidades.investidores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import entidades.Carteira;
import estruturas_de_dados.lista.ListaEncadeada;

public class InvestidorJuridico extends Investidor{

    private String cnpj;

    public InvestidorJuridico(int id, String nome, double saldo, ListaEncadeada<Carteira> carteiras, String cnpj) {
        super(id, nome, saldo, carteiras);
        this.setCnpj(cnpj);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        CNPJValidator validator = new CNPJValidator();
        try {
            validator.assertValid(cnpj);
            this.cnpj = cnpj;
        } catch (InvalidStateException e) {
            System.err.println("CNPJ da empresa inválido: " + e.getInvalidMessages());
        }
    }
    
        public boolean verificarCnpj(String cpf) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\investidorjuridico.txt"; 
    
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
