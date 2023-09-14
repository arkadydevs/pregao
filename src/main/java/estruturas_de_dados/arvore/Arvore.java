package estruturas_de_dados.arvore;

public class Arvore<T extends Comparable<T>> {
    private ElementosArvore<T> raiz;

    public Arvore(){
        this.raiz = null;
    }

    public ElementosArvore<T> getRaiz() {
        return raiz;
    }
    public void setRaiz(ElementosArvore<T> raiz) {
        this.raiz = raiz;
    }
    
    public void addElemento(T valor){
        ElementosArvore<T> elemento = new ElementosArvore<T>(valor);
        if(raiz == null){
            this.raiz = elemento;
        } else {
            ElementosArvore<T> j = this.raiz;
            while(true){
                if(elemento.getValor().compareTo(j.getValor()) == -1) { 
                    if(j.getEsquerda() != null){
                        j = j.getEsquerda();
                    }
                    else {
                        j.setEsquerda(elemento);
                        break;
                    }
                } else {
                    if(j.getDireita() != null){
                        j = j.getDireita();
                    }
                    else {
                        j.setDireita(elemento);
                        break;
                    }
                }
            }
        }
    }
    
       public void ordemCrescente(ElementosArvore<T> j){
        if( j != null){
            ordemCrescente(j.getEsquerda());
            System.out.println(j.getValor());
            ordemCrescente(j.getDireita());
        }
    }

    public void ordemDecrescente(ElementosArvore<T> j){
        if( j != null){
            ordemDecrescente(j.getDireita());
            System.out.println(j.getValor());
            ordemDecrescente(j.getEsquerda());
        }
    }

}
