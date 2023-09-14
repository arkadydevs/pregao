package estruturas_de_dados.pilha;


import estruturas_de_dados.lista.Elemento;
import estruturas_de_dados.lista.ListaEncadeada;

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


}