package entidades.investidores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import entidades.Carteira;
import estruturas_de_dados.fila.Fila;
import estruturas_de_dados.lista.Elemento;
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

    public void comprarAtivos() {
        String caminhoArquivo = null;
        ListaEncadeada<String> filaCodigo = new ListaEncadeada<>();
        ListaEncadeada<Integer> filaQuantidade = new ListaEncadeada<>();
        Scanner sc = new Scanner(System.in);
        String codAtivo = null;
        int quantidade = 0;
        
        try {
            System.out.println("Insira a quantidade de diferentes ativos que deseja comprar:");
            int numCompras = sc.nextInt();
        
            sc.nextLine(); // Consuma a quebra de linha pendente
        
            if (numCompras <= 0) {
                System.err.println("Você deve comprar pelo menos um ativo.");
                return;
            }
        
            for (int i = 0; i < numCompras; i++) {
                System.out.println("Insira o ticket do ativo:");
                codAtivo = sc.nextLine(); // Remova espaços em branco
                
                if (codAtivo.length() == 7) {
                    caminhoArquivo = "src\\main\\java\\bancos_de_dados\\fii.txt"; 
                } else if (codAtivo.length() == 5 && codAtivo.charAt(4) == '4') {
                    caminhoArquivo = "src\\main\\java\\bancos_de_dados\\preferencial.txt"; 
                } else if (codAtivo.length() == 5 && codAtivo.charAt(4) == '3') {
                    caminhoArquivo = "src\\main\\java\\bancos_de_dados\\ordinaria.txt"; 
                } else {
                    System.err.println("Ticket do ativo não é válido");
                    return; 
                }
            
                System.out.println("Insira a quantidade de ações que deseja comprar:");
                quantidade = sc.nextInt();
                
                filaCodigo.addElemento(codAtivo);
                filaQuantidade.addElemento(quantidade);
              
        
            }
    
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                int numeroLinha = 1;
                
        // Dentro do loop para leitura do arquivo
        for (int i = 0; i < filaCodigo.getTamanho(); i++) {
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                String id = partes[0].trim();
                
                if (id.equals(filaCodigo.get(i))) {
                    double cotacao = Double.parseDouble(partes[1].trim());
                    int lote = Integer.parseInt(partes[4].trim());
                    
                   Elemento<Integer> elemento = filaQuantidade.get(i); // Obtenha o elemento da fila
                    quantidade = elemento.getValor(); 
                    if (quantidade < lote) {
                        System.out.println("Você não comprou a quantidade mínima de ações necessárias");
                    } else {
                        double custo = cotacao * quantidade;
                        if (custo <= getSaldo()) {
                            double saldoAtual = getSaldo() - custo;
                            setSaldo(saldoAtual);
                            System.out.println("Saldo atual: " + getSaldo());
                        } else {
                            System.out.println("Saldo insuficiente para comprar " + quantidade + " ações de " + id);
                        }
                    }
                }
            }
}   

            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                e.printStackTrace(); 
            }
        } finally {
            sc.close(); // Feche o Scanner no bloco finally
        }
    }
}
