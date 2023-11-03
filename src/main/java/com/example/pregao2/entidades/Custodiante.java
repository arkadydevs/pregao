package com.example.pregao2.entidades;

import com.example.pregao2.entidades.investidores.Investidor;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;

import java.util.Map;

public class Custodiante {
                
    private Map<Investidor, ListaEncadeada<Carteira>> custodia;

    public Custodiante(Map<Investidor, ListaEncadeada<Carteira>> custodia) {
         this.setCustodia(custodia);
    }

    public ListaEncadeada<Carteira> obterCarteiras(Investidor investidor) {
        if (custodia.containsValue(investidor)) {
            return custodia.get(investidor);
        }
        return new ListaEncadeada<>(); 
    }

    public Map<Investidor, ListaEncadeada<Carteira>> getCustodia() {
        return custodia;
    }
    public void setCustodia(Map<Investidor, ListaEncadeada<Carteira>> custodia) {
        this.custodia = custodia;
    }

}
