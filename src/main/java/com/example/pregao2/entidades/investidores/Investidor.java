package com.example.pregao2.entidades.investidores;

import com.example.pregao2.entidades.Carteira;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;


public abstract class Investidor{
    private int id;
    private String nome;
    private  String senha;
    private double saldo;
    private ListaEncadeada<Carteira> carteiras;


    public Investidor() {
    }

    public Investidor(int id, String nome, double saldo, String senha, ListaEncadeada<Carteira> carteiras){
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha.length() < 6) {
            throw new RuntimeException("A senha precisa ter pelo menos 6 caracteres");
        }
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        if(saldo < 0.0){
            throw new IllegalArgumentException("Não é possível ter um saldo negativo");
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
