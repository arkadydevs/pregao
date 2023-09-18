package arkadydevs;

import bancos_de_dados.Registro;
import entidades.Carteira;
import entidades.investidores.InvestidorFisico;
import estruturas_de_dados.lista.ListaEncadeada;



public class App 
{
    public static void main( String[] args )
    {  
        InvestidorFisico iv = new InvestidorFisico(1, null, 100000, null, null);
        iv.comprarAcao("AMER3", 110);
    }
}
