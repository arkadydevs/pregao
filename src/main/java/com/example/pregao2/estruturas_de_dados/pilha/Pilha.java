package com.example.pregao2.estruturas_de_dados.pilha;


import com.example.pregao2.estruturas_de_dados.lista.Elemento;
import com.example.pregao2.estruturas_de_dados.lista.ListaEncadeada;

public class Pilha<T> {
    private ListaEncadeada<T> lista;

    public Pilha() {
        this.lista = new ListaEncadeada<>();
    }
    

    public void addElemento(T elemento){
        this.lista.addElemento(elemento);
    }

    public void remover() {
        if(lista.vazia()){
            throw new IllegalStateException("A pilha está vazia");
        }
        this.lista.removerElementoValor(lista.getUltimo().getValor());
    }
    
    public int tamanho(){
        return lista.getTamanho();
    }
    
    public Elemento<T> get(){
        return this.lista.getUltimo();
    }

    public ListaEncadeada<T> ultimosCinco() {
        ListaEncadeada<T> ultimosCincoElementos = new ListaEncadeada<>();

        int tamanhoPilha = this.lista.getTamanho();
        int limite = Math.min(tamanhoPilha, 5);

        for (int i = 0; i < limite; i++) {
            Elemento<T> elemento = this.lista.get(tamanhoPilha - 1 - i);
            ultimosCincoElementos.addElemento(elemento.getValor());
        }

        return ultimosCincoElementos;
    }

    public void imprimirUltimosCinco() {
        ListaEncadeada<T> ultimosCincoElementos = ultimosCinco();

        int tamanho = ultimosCincoElementos.getTamanho();
        int inicio = Math.max(0, tamanho - 5);

        for (int i = inicio; i < tamanho; i++) {
            T elemento = ultimosCincoElementos.get(i).getValor();
            System.out.println(elemento);
        }
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        ListaEncadeada<T> ultimosCincoElementos = ultimosCinco();

        int tamanho = ultimosCincoElementos.getTamanho();
        int inicio = Math.max(0, tamanho - 5);

        for (int i = inicio; i < tamanho; i++) {
            T elemento = ultimosCincoElementos.get(i).getValor();
            builder.append(elemento);

            if (i < tamanho - 1) {
                builder.append(",");
            }
        }

        return builder.toString();
    }
}