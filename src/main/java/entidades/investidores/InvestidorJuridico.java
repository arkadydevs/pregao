package entidades.investidores;

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
    
}
