package Controller;

import Model.Trecho;
import Model.Trechos;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class CalculadoraImplementacao extends UnicastRemoteObject implements Calculadora {

    private List lista;
    private List listaTrechos;

    public CalculadoraImplementacao() throws RemoteException {
        super();
        listaTrechos = Trechos.retornarListaTrechos();
        lista = new ArrayList<>();
    }

    @Override
    public List trechos(int id) {
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
        return lista;
    }

    @Override
    public void comprar(String trecho) throws RemoteException {
        Iterator it = listaTrechos.iterator();
        String[] id = trecho.split("-");

        while (it.hasNext()) {
            Trecho trecho2 = (Trecho) it.next();
            System.out.println("ID:" + id[0] + " | ID:" + trecho2.getId());
            if (trecho2.getId() == Integer.valueOf(id[0])) {
                System.out.println("Comprou esse trecho!");
                trecho2.setQuantAssentos(trecho2.getQuantAssentos() - 1);
                if (trecho2.getQuantAssentos() == 0) {
                    listaTrechos.remove(trecho2);
                }
                break;
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

    /*
    @Override
    public long somar(long a, long b) throws RemoteException {
        return a + b;
    }
     */
}
