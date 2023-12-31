package com.example.pregao2.estruturas_de_dados.lista;

public class Elemento<T> {
    private T valor;
    private Elemento<T> proximoElemento;

    public Elemento(T novoValor){
        this.valor = novoValor;
    }

    public Elemento<T> getProximoElemento() {
        return proximoElemento;
    }

    public void setProximoElemento(Elemento<T> proximoElemento) {
        this.proximoElemento = proximoElemento;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
