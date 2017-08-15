/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CompanhiaImplementacao;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import Controller.Companhia;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class ServidorRMI {

    public static void main(String[] args) {
        Companhia companhia;
        int id;
        Scanner leitura = new Scanner(System.in);

        try {
            System.out.println("Digite o id da Companhia Aérea:");
            id = leitura.nextInt();
            System.out.println("Digite a porta do servidor:");
            int porta = leitura.nextInt();

            LocateRegistry.createRegistry(porta);
            companhia = new CompanhiaImplementacao(id);
            Naming.rebind("127.0.0.1/PassagensAreas" + id, (Remote) companhia);

            //leitura.nextLine();
            //leitura.nextLine();
            //fazerConexões();
            System.out.println("Servidor Remoto Iniciado...");
        } catch (RemoteException | MalformedURLException ex) {
            System.err.println("ERRO: " + ex.getMessage());
        }
    }
}
