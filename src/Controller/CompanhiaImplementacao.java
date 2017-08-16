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

    private List lista;
    private List listaTrechos;
    private List pedidos;
    private int[] querRegCrit; // 1:está usando, 2:quer usar, 0:não quer
    private boolean[] temRegCrit; //true: está usando. false: não está
    private int logiClock;
    private int serverId;
    private final int id;
    private Companhia companhia;

    public CompanhiaImplementacao(int id) throws RemoteException {
        super();
        this.id = id;
        listaTrechos = Trechos.retornarListaTrechos();
        lista = new ArrayList<>();
        pedidos = new ArrayList<>();
        logiClock = 0;
        querRegCrit = new int[30];
        temRegCrit = new boolean[30];
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
                    while (it.hasNext()) {
                        //dlm.addElement("AAA");
                        //Trecho trecho = (Trecho) it.next();
                        trechosServidores.add(it.next());
                        System.out.println("aaaaa");
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
    public boolean comprar(String trecho) throws RemoteException {

        List listaAux = new ArrayList<>();
        boolean entrou = false;
        Iterator it = listaTrechos.iterator();
        String[] trechoSplit = trecho.split("-");

        //trechoSplit[2] = companhia do trecho
        if (Integer.valueOf(trechoSplit[2]) == id) {
            while (it.hasNext()) {
                Trecho trecho2 = (Trecho) it.next();
                System.out.println("ID:" + trechoSplit[0] + " | ID:" + trecho2.getId());
                if (trecho2.getId() == Integer.valueOf(trechoSplit[0])) {
                    entrou = true;
                    listaAux.add(trecho2);
                }
            }
            if (entrou == false) {
                System.out.println("ERROOOO ACABOU AS VAGAS !!!!!!!!!!!!");
                return false;
            } else {
                it = listaAux.iterator();
                while (it.hasNext()) {
                    Trecho trecho2 = (Trecho) it.next();
                    System.out.println("Comprou esse trecho!");
                    trecho2.setQuantAssentos(trecho2.getQuantAssentos() - 1);
                    if (trecho2.getQuantAssentos() == 0) {
                        listaTrechos.remove(trecho2);
                    }
                }
                return true;
            }
        } else {
            try {
                companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + Integer.valueOf(trechoSplit[2]));
                return companhia.removerTrecho(Integer.valueOf(trechoSplit[0]));

            } catch (NotBoundException ex) {
                Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("SAIU!!!!!!!!!!!!!!!!!!!!!");
        /*
        it = listaTrechos.iterator();
        while (it.hasNext()) {
            Trecho trecho2 = (Trecho) it.next();
            System.out.println(trecho2.getId() + " | " + trecho2.getOrigem() + "  |  " + trecho2.getDestino());
        }*/
        //listaTrechos.remove(10);
        //lista.removeAll(lista);
        return false;
    }

    @Override
    public boolean removerTrecho(int idTrecho) throws RemoteException {
        List listaAux = new ArrayList<>();
        boolean entrou = false;
        Iterator it = listaTrechos.iterator();

        while (it.hasNext()) {
            Trecho trecho2 = (Trecho) it.next();
            if (trecho2.getId() == idTrecho) {
                entrou = true;
                listaAux.add(trecho2);
            }
        }
        if (entrou == false) {
            System.out.println("ERROOOO ACABOU AS VAGAS !!!!!!!!!!!!");
            return false;
        } else {
            it = listaAux.iterator();
            while (it.hasNext()) {
                Trecho trecho2 = (Trecho) it.next();
                System.out.println("Comprou esse trecho!");
                trecho2.setQuantAssentos(trecho2.getQuantAssentos() - 1);
                if (trecho2.getQuantAssentos() == 0) {
                    listaTrechos.remove(trecho2);
                }
            }
            return true;
        }
    }

    @Override
    public boolean pedirAcesso(int[] ids, int myLogiClock, int myServerId) throws RemoteException {
        for (int i = 0; i < ids.length; i++) {
            if (autorizarAcesso(ids[i], myLogiClock, myServerId) == true) {
                getTemRegCrit()[ids[i]] = true;
            }
        }
        return true;
    }

    /**
     * Pede para acessar uma região crítica dentro do mesmo servidor
     *
     * @param ids
     * @return
     */
    @Override
    public boolean pedirAcessoInter(int ids[]) {
        int autorizar = ids.length;
        while (autorizar != 0) {
            for (int i = 0; i < ids.length; i++) {
                if (getTemRegCrit()[ids[i]] == false && ids[i] != 0 && getQuerRegCrit()[ids[i]] == 0) {
                    getQuerRegCrit()[ids[i]] = 2;
                    ids[i] = 0;
                }
            }
        }
        return true;
    }

    /**
     * Verifica em outros servidores
     *
     * @param id
     * @param myLogiClock
     * @param myServerId
     * @return
     */
    @Override
    public boolean autorizarAcesso(int id, int myLogiClock, int myServerId) {
        CompanhiaImplementacao companhia = null;
        for (int i = 1; i <= 3; i++) {
            if (i != getServerId()) {
                try {
                    companhia = (CompanhiaImplementacao) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    if (companhia.getTemRegCrit()[id] == true) {
                        autorizarAcesso(id, myLogiClock, myServerId);
                    } else if (companhia.getQuerRegCrit()[id] == 1 && (getLogiClock() == myLogiClock) && (getServerId() > myServerId)) {
                        autorizarAcesso(id, myLogiClock, myServerId);
                    } else if ((companhia.getQuerRegCrit()[id] == 1) && (getLogiClock() > myLogiClock)) {
                        autorizarAcesso(id, myLogiClock, myServerId);
                    }
                } catch (NotBoundException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }
    
    

    @Override
    public void liberarAcesso(int[] ids) throws RemoteException {
        for (int i = 0; i < ids.length; i++) {
            querRegCrit[ids[i]] = 0;
            temRegCrit[ids[i]] = false;
        }
    }

    /**
     * @return the serverId
     */
    public int getServerId() {
        return serverId;
    }

    /**
     * @param serverId the serverId to set
     */
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    /**
     * @return the pedidos
     */
    public List getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos the pedidos to set
     */
    public void setPedidos(List pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * @return the querRegCrit
     */
    public int[] getQuerRegCrit() {
        return querRegCrit;
    }

    /**
     * @param querRegCrit the querRegCrit to set
     */
    public void setQuerRegCrit(int[] querRegCrit) {
        this.querRegCrit = querRegCrit;
    }

    /**
     * @return the temRegCrit
     */
    public boolean[] getTemRegCrit() {
        return temRegCrit;
    }

    /**
     * @param temRegCrit the temRegCrit to set
     */
    public void setTemRegCrit(boolean[] temRegCrit) {
        this.temRegCrit = temRegCrit;
    }

    /**
     * @return the logiClock
     */
    public int getLogiClock() {
        return logiClock;
    }

    /**
     * @param logiClock the logiClock to set
     */
    public void setLogiClock(int logiClock) {
        this.logiClock = logiClock;
    }
}
