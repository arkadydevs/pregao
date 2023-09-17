package arkadydevs;


import entidades.investidores.Investidor;
import entidades.investidores.InvestidorFisico;
import menu.Menu;


public class App 
{
    public static void main( String[] args )
    {
        Investidor vs = new InvestidorFisico(1, "null", 10000000, null, null);
        vs.comprarAcao("MXRF11B", 100);
      
        
    }
}
