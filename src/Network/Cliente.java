package Network;

import Controller.Calculadora;
import java.rmi.Naming;
import java.util.Scanner;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class Cliente {

    public static void main(String[] args) {
        Scanner leitura = new Scanner(System.in);

        try {
            System.out.println("Digite o id da Companhia Aérea:");
            int id = leitura.nextInt();

            System.out.println("Cliente");
            Calculadora calculadora = (Calculadora) Naming.lookup("127.0.0.1/Calculadora" + id);
            //long resultado = calculadora.somar(10, 50);

            //System.out.println("A SOMA FOI: " + resultado);
        } catch (Exception ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
    }
}
