package Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public interface Companhia extends Remote {

    public List trechos() throws RemoteException;

    public boolean comprar(String trecho) throws RemoteException;

    public boolean autorizarAcesso(int id, int myLogiClock, int myServerId) throws RemoteException;

    public boolean pedirAcesso(int[] ids, int myLogiClock, int myServerId) throws RemoteException;

    public boolean pedirAcessoInter(int ids[]) throws RemoteException;

    public List trechosDoServidor() throws RemoteException;

    public void liberarAcesso(int ids[]) throws RemoteException;

    public boolean removerTrecho(int trecho) throws RemoteException;
}
