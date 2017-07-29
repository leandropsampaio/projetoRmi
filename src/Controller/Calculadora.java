package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public interface Calculadora extends Remote {

    //public long somar(long a, long b) throws RemoteException;
    
    public List trechos(int id) throws RemoteException;
    
    public void comprar(String trecho) throws RemoteException;
}
