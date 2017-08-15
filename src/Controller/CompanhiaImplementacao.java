package Controller;

import Model.Trecho;
import Model.Trechos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class CompanhiaImplementacao extends UnicastRemoteObject implements Companhia {

    private int id;
    private List lista;
    private List listaTrechos;
    private Companhia com;
    private Companhia companhia;

    public CompanhiaImplementacao(int id) throws RemoteException {
        super();
        this.id = id;
        listaTrechos = Trechos.retornarListaTrechos();
        lista = new ArrayList<>();
        inicializarTrechos();
    }

    public void inicializarTrechos() {
        lista = new ArrayList<>();
        try {
            Iterator it = listaTrechos.iterator();
            while (it.hasNext()) {
                Trecho trecho = (Trecho) it.next();
                if (trecho.getCompanhia() == id) {
                    lista.add(trecho);
                }
            }
            listaTrechos = lista;
        } catch (Exception ex) {
            System.out.println("ERRO!");
        }
        //listaTrechos = Lista de trechos de um único servidor
    }

    @Override
    public List trechosDoServidor() {
        return listaTrechos;
    }

    @Override
    public List trechos() {

        System.out.println("xxxxxx");
        List trechosServidores = new ArrayList<>();;
        int i = 0;
        try {
            for (i = 1; i <= 3; i++) {
                System.out.println("000000000000000");
                if (i == id) {
                    Iterator it = listaTrechos.iterator(); //cal
                    System.out.println("aaaaa");
                    while (it.hasNext()) {
                        //dlm.addElement("AAA");
                        //Trecho trecho = (Trecho) it.next();
                        trechosServidores.add(it.next());
                    }

                } else if (i != id) {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();

                    Iterator it = trechos.iterator();
                    while (it.hasNext()) {
                        //dlm.addElement("BBB");
                        //Trecho trecho = (Trecho) it.next();
                        trechosServidores.add(it.next());
                        System.out.println("bbbb");
                    }
                }
                System.out.println("cccccccc");
            }
        } catch (RemoteException | MalformedURLException ex) {
        } catch (NotBoundException ex) {
            System.err.println("Servidor: | " + i + " | não iniciado! Execute o servidor...");
        }
        return trechosServidores;
    }

    @Override
    public void comprar(String trecho) throws RemoteException {
        Iterator it = listaTrechos.iterator();
        String[] trechoSplit = trecho.split("-");

        //trechoSplit[2] = companhia do trecho
        if (Integer.valueOf(trechoSplit[2]) == id) {
            while (it.hasNext()) {
                Trecho trecho2 = (Trecho) it.next();
                System.out.println("ID:" + trechoSplit[0] + " | ID:" + trecho2.getId());
                if (trecho2.getId() == Integer.valueOf(trechoSplit[0])) {
                    System.out.println("Comprou esse trecho!");
                    trecho2.setQuantAssentos(trecho2.getQuantAssentos() - 1);
                    if (trecho2.getQuantAssentos() == 0) {
                        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                        listaTrechos.remove(trecho2);
                    }
                    break;
                }
            }
        } else {
            try {
                companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + Integer.valueOf(trechoSplit[2]));
                companhia.removerTrecho(Integer.valueOf(trechoSplit[0]));

            } catch (NotBoundException ex) {
                Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        it = listaTrechos.iterator();
        while (it.hasNext()) {
            Trecho trecho2 = (Trecho) it.next();
            System.out.println(trecho2.getId() + " | " + trecho2.getOrigem() + "  |  " + trecho2.getDestino());
        }
        System.out.println("SAIU!");
        //listaTrechos.remove(10);
        //lista.removeAll(lista);
    }

    @Override
    public void removerTrecho(int idTrecho) throws RemoteException {
        Iterator it = listaTrechos.iterator();
        System.out.println("111111111111111111111");
        while (it.hasNext()) {
            Trecho trecho2 = (Trecho) it.next();
            if (trecho2.getId() == idTrecho) {
                System.out.println("2222222222222222222");
                trecho2.setQuantAssentos(trecho2.getQuantAssentos() - 1);
                if (trecho2.getQuantAssentos() == 0) {
                    System.out.println("333333333333333333");
                    listaTrechos.remove(trecho2);
                    break;
                }
            }
        }
    }
}
