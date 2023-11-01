package estruturas_de_dados.arvore;

public class ElementosArvore<T>{
    private T valor;
    private ElementosArvore<T> esquerda;
    private ElementosArvore<T> direita;

    public ElementosArvore(T novoValor){
        this.valor = novoValor;
        this.esquerda = null;
        this.direita = null;
    }

    public T getValor() {
        return valor;
    }
    public void setValor(T valor) {
        this.valor = valor;
    }

    public ElementosArvore<T> getEsquerda() {
        return esquerda;
    }
    public void setEsquerda(ElementosArvore<T> esquerda) {
        this.esquerda = esquerda;
    }

    public ElementosArvore<T> getDireita() {
        return direita;
    }
    public void setDireita(ElementosArvore<T> direita) {
        this.direita = direita;
    }
    
}