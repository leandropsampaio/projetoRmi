package Network;

import Controller.CompanhiaImplementacao;
import Model.Trecho;
import Model.Trechos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controller.Companhia;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class Servidor {

    private Companhia cal;
    private Companhia calculadora;
    private int id;
    private Scanner leitura = new Scanner(System.in);

    public static void main(String[] args) {
        Servidor servidor = new Servidor();

    }

    public Servidor() {
        try {
            System.out.println("Digite o id da Companhia Aérea:");
            id = leitura.nextInt();
            System.out.println("Digite a porta do servidor:");
            int porta = leitura.nextInt();

            LocateRegistry.createRegistry(porta);
            cal = new CompanhiaImplementacao(id);
            Naming.rebind("127.0.0.1/Calculadora" + id, (Remote) cal);

            leitura.nextLine();
            leitura.nextLine();
            fazerConexões();

            System.out.println("Servidor Remoto Iniciado...");
            //mostrarTrechos();
        } catch (RemoteException | MalformedURLException ex) {
            System.err.println("ERRO: " + ex.getMessage());
        }
    }

    /**
     * Fazer conexões com outras companhias aéreas
     *
     */
    public void fazerConexões() {
        try {
            for (int i = 1; i <= 3; i++) {
                if (i != id) {
                    calculadora = (Companhia) Naming.lookup("127.0.0.1/Calculadora" + i);
                    /*
                    List trechos = calculadora.trechos(i);

                    Iterator it = trechos.iterator();
                    while(it.hasNext()){
                        Trecho trecho = (Trecho) it.next();
                        System.out.println("________________________________________________");
                        System.out.println("Origem: "+trecho.getOrigem());
                        System.out.println("Destino: "+trecho.getDestino());
                        System.out.println("________________________________________________");
                    }
                     */
                }
            }
        } catch (Exception ex) {
            System.err.println("ERRO: " + ex.getMessage());
        }
    }

    /*
    public void mostrarTrechos() {
        List trechos;
        try {
            for (int i = 1; i <= 3; i++) {
                if (i == id) {
                    Iterator it = cal.trechos(id).iterator();
                    
                    while (it.hasNext()) {
                        Trecho trecho = (Trecho) it.next();
                        System.out.println("________________________________________________");
                        System.out.println("Origem: " + trecho.getOrigem());
                        System.out.println("Destino: " + trecho.getDestino());
                        System.out.println("________________________________________________");
                    }
                    
                } else if (i != id) {
                    trechos = calculadora.trechos(i);

                    Iterator it = trechos.iterator();
                    while (it.hasNext()) {
                        Trecho trecho = (Trecho) it.next();
                        System.out.println("________________________________________________");
                        System.out.println("Origem: " + trecho.getOrigem());
                        System.out.println("Destino: " + trecho.getDestino());
                        System.out.println("________________________________________________");
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
