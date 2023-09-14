package entidades;

import entidades.ativos.Ativo;
import estruturas_de_dados.lista.ListaEncadeada;

public class Carteira {
    private ListaEncadeada<Ativo> carteiras;

    public Carteira(ListaEncadeada<Ativo> carteiras){
        this.setCardeiras(carteiras);
    }

    public ListaEncadeada<Ativo> getCarteiras() {
        return carteiras;
    }
    public void setCardeiras(ListaEncadeada<Ativo> carteiras) {
        this.carteiras = carteiras;
    }
}
