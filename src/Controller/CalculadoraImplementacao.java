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
        Iterator it = listaTrechos.iterator();
        while (it.hasNext()) {
            Trecho trecho = (Trecho) it.next();
            if (trecho.getCompanhia() == id) {
                lista.add(trecho);
            }
        }
        return lista;
    }

    @Override
    public long somar(long a, long b) throws RemoteException {
        return a + b;
    }

}
