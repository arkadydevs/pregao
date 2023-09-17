package entidades.investidores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entidades.Carteira;
import estruturas_de_dados.lista.ListaEncadeada;

public abstract class Investidor{
    private int id;
    private String nome;
    private double saldo;
    private ListaEncadeada<Carteira> carteiras;

    public Investidor(int id, String nome, double saldo, ListaEncadeada<Carteira> carteiras){
        this.setId(id);
        this.setNome(nome);
        this.setSaldo(saldo);
        this.setCarteiras(carteiras);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        if(saldo < 0.0){
            throw new IllegalArgumentException("Não é possível ter uma excessão negativa");
        }
        this.saldo = saldo;
    }

    public ListaEncadeada<Carteira> getCarteiras() {
        return carteiras;
    }
    public void setCarteiras(ListaEncadeada<Carteira> carteiras) {
        this.carteiras = carteiras;
    }


    public void comprarAcao(String codAtivo, int quantidade) {
        String caminhoArquivo = "src\\main\\java\\bancos_de_dados\\fii.txt"; 
    
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int numeroLinha = 1;
    
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String id = partes[0].trim(); 
                if (id.equals(codAtivo)) {
                    double cotacao = Double.parseDouble(partes[1].trim());
                    int lote = Integer.parseInt(partes[4].trim()); 
                        if (quantidade < lote) {
                            System.out.println("Você não comprou a quantidade mínima de ações necessárias");
                        }
                        else{
                            double saldo = getSaldo() - (cotacao * quantidade);
                            setSaldo(saldo);
                            System.out.println(getSaldo());
                        }
                }
                numeroLinha++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    

    }
}