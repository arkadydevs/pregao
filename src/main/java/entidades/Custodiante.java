package entidades;
import java.util.Map;
import entidades.investidores.Investidor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Custodiante {
    private Map<Investidor, List<Carteira>> custodia;

    public Custodiante() {
        custodia = new HashMap<>();
    }

    public List<Carteira> obterCarteiras(Investidor investidor) {
        if (custodia.containsKey(investidor)) {
            return custodia.get(investidor);
        }
        return new ArrayList<>(); 
    }

}
