package estruturas_de_dados.lista;
import java.util.NoSuchElementException;

public class ListaEncadeada<T> {
    private Elemento<T> primeiro;
    private Elemento<T> ultimo;
    private int tamanho;

    public ListaEncadeada(){
        
        this.tamanho = 0;
    }

    public Elemento<T> getPrimeiro() {
        return primeiro;
    }
    public void setPrimeiro(Elemento<T> primeiro) {
        this.primeiro = primeiro;
    }

    public Elemento<T> getUltimo() {
        return ultimo;
    }
    public void setUltimo(Elemento<T> ultimo) {
        this.ultimo = ultimo;
    }

    public int getTamanho() {
        return tamanho;
    }
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }



    public void addElemento(T novoValor){
        Elemento<T> no = new Elemento<T>(novoValor);
        if(primeiro == null && ultimo == null ){
            this.primeiro = no;
            this.ultimo = no;
        } 
        else {
            this.ultimo.setProximoElemento(no);
            this.ultimo = no;   
            
        }
        this.tamanho++;
    }

    public void removerElementoValor(T remover){
        Elemento<T> anterior = null;
        Elemento<T> j = this.primeiro;
        boolean teste = false;

         for (int i = 0; i < this.getTamanho(); i++ ) { 
            if (j.getValor().equals(remover)) {
                if(j == primeiro){
                    this.primeiro = j.getProximoElemento();
                    j.setProximoElemento(null);
                }
                else if(j == ultimo){
                    this.ultimo = anterior;
                    j.setProximoElemento(null);
                }
                else{
                    j.setProximoElemento(j.getProximoElemento());
                    j = null;
                }

                teste = true;
                this.tamanho--;
                break;
            }
            anterior = j;
            j = j.getProximoElemento();
        }
        if(!teste){
            throw new NoSuchElementException("Valor " + remover + " não existe na lista");
        }
    }


    public boolean contem(T existe) {
        Elemento<T> j = this.primeiro;
        while (j != null) {
            if (j.getValor().equals(existe)) {
                return true;
            }
            j = j.getProximoElemento();
        }
        return false;
    }
    

    public Elemento<T> get(int posicao) {
        Elemento<T> j = this.primeiro;
        for (int i = 0; i < posicao; i++) { 
            if (j.getProximoElemento() != null) {
                j = j.getProximoElemento();
            } else {
                
                throw new IndexOutOfBoundsException("Posição fora dos limites da lista");
            }
        }
        return j;
    }

    public boolean vazia(){
        if(primeiro == null && ultimo == null){
            return true;
        }
        return false;
    }
}
