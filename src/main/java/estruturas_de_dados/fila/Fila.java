package estruturas_de_dados.fila;


import estruturas_de_dados.lista.Elemento;
import estruturas_de_dados.lista.ListaEncadeada;

public class Fila<T> {
    private ListaEncadeada<T> lista;

    public Fila() {
        this.lista = new ListaEncadeada<>();
    }
    

    public void addElemento(T elemento){
        this.lista.addElemento(elemento);
    }

    public void remover() {
        if(lista.vazia()){
            throw new IllegalStateException("A fila está vazia");
        }
        this.lista.removerElementoValor(lista.getPrimeiro().getValor());
    }
    
    public int tamanho(){
        return lista.getTamanho();
    }
    
    public Elemento<T> get(){
        return this.lista.getPrimeiro();
    }


}