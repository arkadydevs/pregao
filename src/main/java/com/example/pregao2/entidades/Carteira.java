package com.example.pregao2.entidades;

import com.example.pregao2.entidades.ativos.Ativo;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;

public class Carteira {
    private ListaEncadeada<Ativo> carteiras;
    private Corretora corretora;

    public Carteira(ListaEncadeada<Ativo> carteiras, Corretora corretora){
        this.setCarteiras(carteiras);
        this.setCorretora(corretora);
    }

    public ListaEncadeada<Ativo> getCarteiras() {
        return carteiras;
    }
    public void setCarteiras(ListaEncadeada<Ativo> carteiras) {
        this.carteiras = carteiras;
    }

    public void addCarteira (Ativo ativo){
        if (ativo == null){
            throw new IllegalArgumentException ("O código de negociação não pode ser nulo.");
        }

        if (!carteiras.contem(ativo)) {
            carteiras.addElemento(ativo);
        } else {
            System.out.println("A carteira já existe.");
        }
    }

    public Corretora getCorretora() {
        return corretora;
    }

    public void setCorretora(Corretora corretora) {
        if(corretora == null){
            throw new IllegalArgumentException("É preciso ter uma corretora para criar uma carteira.");
        }
        this.corretora = corretora;
    }
}
