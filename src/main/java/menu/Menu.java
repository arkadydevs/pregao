package menu;

import java.util.Scanner;

import br.com.caelum.stella.validation.CNPJValidator;

public class Menu {
    public Menu(){

    }
    
    public void inicio(){
        CNPJValidator validator = new CNPJValidator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Bem vindo ao Pregão 231!\n Quer logar como:\n\n 1-Pessoa jurídica\n 2-Pessoa física\n");
        int primeiro = sc.nextInt();
        if(primeiro == 1){
            
            System.out.println("Digite o CNPJ da sua empresa: ");
            String cnpj = sc.nextLine();
            //ver se cnpj esta no txt
            if(validator.assertValid(cnpj)){
                System.out.println("1- ");
            }
            else{
                System.out.println("CNPJ inválido ou não cadastrado")
            }
        }
        else if(primeiro == 2){
            System.out.println("Digite o seu cpf: ");
            //ver se cpf esta no txt
            



        }
        else{
            System.out.println("");
        }
        sc.close();
    }

    
}
