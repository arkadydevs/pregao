package entidades.investidores;

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


}
