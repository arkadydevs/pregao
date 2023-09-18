package arkadydevs;

import bancos_de_dados.Registro;
import entidades.Carteira;
import entidades.investidores.InvestidorFisico;
import estruturas_de_dados.lista.ListaEncadeada;



public class App 
{
    public static void main( String[] args )
    {  
        int id = 7;
        String nome = "Bunda burguer";
        Double saldo = 666.66;
        String cpf = "165.407.950-25";

        String dados = Integer.toString(id) + ", " + nome +", " + Double.toString(saldo) + ", " + "null" + ", "+ cpf;
        
        System.out.println(dados);
        Registro teste = new Registro("investidorfisico", dados);
        teste.insert(teste);
    }
}
