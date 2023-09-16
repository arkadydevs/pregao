package entidades;

import entidades.ativos.Ativo;
import estruturas_de_dados.lista.ListaEncadeada;

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

    public Corretora getCorretora() {
        return corretora;
    }

    public void setCorretora(Corretora corretora) {
        this.corretora = corretora;
    }
}
