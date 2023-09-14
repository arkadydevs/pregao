package entidades.investidores;

import entidades.Carteira;
import estruturas_de_dados.lista.ListaEncadeada;

public abstract class Investidor{
    private int id;
    private String nome;
    private double saldo;
    private ListaEncadeada<Carteira> carteiras;

    public Investidor(int id, String nome, double saldo, ListaEncadeada<Carteira> carteiras){
        this.setId(id);
        this.setNome(nome);
        this.setSaldo(saldo);
        this.setCarteiras(carteiras);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        if(saldo < 0.0){
            throw new IllegalArgumentException("Não é possível ter uma excessão negativa");
        }
        this.saldo = saldo;
    }

    public ListaEncadeada<Carteira> getCarteiras() {
        return carteiras;
    }
    public void setCarteiras(ListaEncadeada<Carteira> carteiras) {
        this.carteiras = carteiras;
    }

}