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

    /**
     * @return the lista
     */
    public List getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List lista) {
        this.lista = lista;
    }

    /**
     * @return the listaTrechos
     */
    public List getListaTrechos() {
        return listaTrechos;
    }

    /**
     * @param listaTrechos the listaTrechos to set
     */
    public void setListaTrechos(List listaTrechos) {
        this.listaTrechos = listaTrechos;
    }

    /**
     * @param querRegCrit the querRegCrit to set
     */
    public void setQuerRegCrit(int[] querRegCrit) {
        this.querRegCrit = querRegCrit;
    }

    /**
     * @param temRegCrit the temRegCrit to set
     */
    public void setTemRegCrit(boolean[] temRegCrit) {
        this.temRegCrit = temRegCrit;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    private List lista;
    private List listaTrechos;
    private List pedidos;
    private int[] querRegCrit; // 1:está usando, 2:quer usar, 0:não quer
    private boolean[] temRegCrit; //true: está usando. false: não está
    private int logiClock;
    private int serverId;
    private final int id;
    private boolean[] listaDeEspera;
    private Companhia companhia;

    public CompanhiaImplementacao(int id) throws RemoteException {
        super();
        this.id = id;
        this.serverId = id;
        listaTrechos = Trechos.retornarListaTrechos();
        lista = new ArrayList<>();
        pedidos = new ArrayList<>();
        logiClock = 0;
        querRegCrit = new int[31];
        temRegCrit = new boolean[31];
        listaDeEspera = new boolean[31];
        inicializarTrechos();
    }

    public void inicializarTrechos() {

        setLista(new ArrayList<>());
        try {
            Iterator it = getListaTrechos().iterator();
            while (it.hasNext()) {
                Trecho trecho = (Trecho) it.next();
                if (trecho.getCompanhia() == getId()) {
                    getLista().add(trecho);
                }
            }
            setListaTrechos(getLista());
        } catch (Exception ex) {
            System.out.println("ERRO!");
        }
        //listaTrechos = Lista de trechos de um único servidor
    }

    @Override
    public List trechosDoServidor() {
        return getListaTrechos();
    }

    @Override
    public List trechos() {

        //System.out.println("xxxxxx");
        List trechosServidores = new ArrayList<>();;
        int i = 0;
        try {
            for (i = 1; i <= 3; i++) {
                //System.out.println("000000000000000");
                if (i == getId()) {
                    Iterator it = getListaTrechos().iterator(); //cal
                    while (it.hasNext()) {
                        //dlm.addElement("AAA");
                        //Trecho trecho = (Trecho) it.next();
                        trechosServidores.add(it.next());
                        System.out.println("aaaaa");
                    }

                } else if (i != getId()) {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();

                    Iterator it = trechos.iterator();
                    while (it.hasNext()) {
                        //dlm.addElement("BBB");
                        //Trecho trecho = (Trecho) it.next();
                        trechosServidores.add(it.next());
                        //System.out.println("bbbb");
                    }
                }
                //System.out.println("cccccccc");
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
        Iterator it = getListaTrechos().iterator();
        String[] trechoSplit = trecho.split("-");

        //trechoSplit[2] = companhia do trecho
        if (Integer.valueOf(trechoSplit[2]) == getId()) {
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
                        getListaTrechos().remove(trecho2);
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
        Iterator it = getListaTrechos().iterator();

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
                    getListaTrechos().remove(trecho2);
                }
            }
            return true;
        }
    }

    @Override
    public synchronized boolean pedirAcesso(int[] ids, int myLogiClock, int myServerId) throws RemoteException {
        for (int i = 0; i < ids.length; i++) {
            if (autorizarAcesso(ids[i], myLogiClock, myServerId) == true) {
                getTemRegCrit()[ids[i]] = true;
                logiClock++;
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
    public synchronized boolean pedirAcessoInter(int ids[]) {
        int autorizar = ids.length;
        System.out.println(""+ids.length);
        while (autorizar != 0) {
            for (int i = 0; i < ids.length; i++) {
                if (getTemRegCrit()[ids[i]] == false && getQuerRegCrit()[ids[i]] == 0) {
                    getQuerRegCrit()[ids[i]] = 2;
                    autorizar--;
                    System.out.println("Autorizado trecho " + ids[i]);
                }
            }
        }
        System.out.println("Verificado acesso interno");
        return true;
    }

    @Override
    public synchronized boolean autorizarTotals(int[] ids) {
        boolean regCritInterna = false;
        boolean regCritRemota = false;
            try {
                regCritInterna = pedirAcessoInter(ids);

                regCritRemota = pedirAcesso(ids, logiClock, serverId);
                System.out.println("aaaaa");
            } catch (RemoteException ex) {
                Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(regCritInterna && regCritRemota){
                System.out.println("Tudo ok");
            }
        System.out.println("bbbbbbbbbbbbb");
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
    public synchronized boolean autorizarAcesso(int id, int myLogiClock, int myServerId) {
        Companhia comp;
        for (int i = 1; i <= 3; i++) {
            System.out.println("AQUI!");
            if (i != serverId) {
                try {
                    comp = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    if (comp.getTemRegCrit()[id] == true) {
                        autorizarAcesso(id, myLogiClock, myServerId);
                    } else if (comp.getQuerRegCrit()[id] == 1 && (getLogiClock() == myLogiClock) && (getServerId() > myServerId)) {
                        autorizarAcesso(id, myLogiClock, myServerId);
                    } else if ((comp.getQuerRegCrit()[id] == 1) && (getLogiClock() > myLogiClock)) {
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

        System.out.println("Verificado acesso remoto");
        return true;
    }

    /**
     * Sai da região crítica
     *
     * @param ids
     * @throws RemoteException
     */
    @Override
    public synchronized void liberarAcesso(int[] ids) throws RemoteException {
        for (int i = 0; i < ids.length; i++) {
            getQuerRegCrit()[ids[i]] = 0;
            getTemRegCrit()[ids[i]] = false;
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
    @Override
    public int[] getQuerRegCrit() {
        return querRegCrit;
    }

    /**
     * @return the temRegCrit
     */
    @Override
    public boolean[] getTemRegCrit() {
        return temRegCrit;
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

    @Override
    public int checarDisp(int[] ids) throws RemoteException {
        int dispo = 0;
        int reservados = 0;    
        
        for(int i = 1; i<=3; i++){
            System.out.println("11111111111111111");
            if(i != serverId){
                System.out.println("222222222222222");
                try {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();
                    Iterator it = trechos.iterator();
                    for( int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                if(trecho.getStatus() == 2){
                                    return 2;
                                }
                                else if (trecho.getStatus() == 1){
                                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                                    reservados++;
                                    dispo = 1;
                                    if(reservados > 1 || listaDeEspera[ids[s]]){
                                        return 2;
                                    }
                                }
                            }
                            
                        }
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                System.out.println("333333333333333333333");
                Iterator it = this.listaTrechos.iterator();
                    for( int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                if(trecho.getStatus() == 2){
                                    return 2;
                                }
                                else if (trecho.getStatus() == 1){
                                    reservados++;
                                    dispo = 1;
                                    if(reservados > 1 || listaDeEspera[ids[s]]){
                                        return 2;
                                    }
                                }
                            }
                        }
                    }
            }
        }
        return dispo;
    }
    
    @Override
    public void trocarStatus(Trecho trecho, int status){
        
    Iterator it = this.listaTrechos.iterator();
                        while(it.hasNext()){
                            System.out.println("0000000000000000000000");
                            Trecho trecho2 = (Trecho) it.next();
                            if(trecho.getId() == trecho2.getId()){
                                System.out.println("88888888888888888888888888");
                                trecho2.setStatus(status);
                            }
                        }
                        
                        System.out.println("__________________________________________________");
                        it = this.listaTrechos.iterator();
                        while(it.hasNext()){
                            Trecho trecho2 = (Trecho) it.next();
                            System.out.println(trecho2.getId()+"------"+trecho2.getStatus());
                        }
                        
                        
                    }
    
    
    
    /**
     * Reserva todos os trechos
     * @param ids
     * @return 
     */
    @Override
    public boolean reservando(int ids[]){
        
        for(int i = 1; i<=3; i++){
            
            if(i != serverId){
                try {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    System.out.println("ENTROUUUUUUUUUUUU!" + i);
                    List trechos = companhia.trechosDoServidor();
                    Iterator it = trechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        System.out.println("ENTROUUUUUUUUUUUU2!" + s);
                        while(it.hasNext()){
                            System.out.println("ENTROUUUUUUUUUUUU3!" + s);
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                System.out.println("AQUIIIIIIIII!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                companhia.trocarStatus(trecho, 1);
                                //trecho.setStatus(1);
                            }
                        }
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                    Iterator it = this.listaTrechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                trecho.setStatus(1);
                            }
                        }
                    }
            }
        }
        return true;
    }
    
    /**
     * Compra todos os trechos
     * @param ids
     * @return 
     */
    @Override
    public boolean comprar(int ids[]){
        for(int i = 1; i<=3; i++){
            if(i != serverId){
                try {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();
                    Iterator it = trechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                companhia.trocarStatus(trecho, 2);
                                trecho.setStatus(2);
                            }
                        }
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                    Iterator it = this.listaTrechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                trecho.setStatus(2);
                            }
                        }
                    }
            }
        }
        return true;
    }
    /**
     * Reserva todos os trechos disponíveis e fica na lista de espera pelo que está reservado
     * @param ids
     * @return 
     */
    @Override
    public boolean listaDeEspera1(int ids[]){
        for(int i = 1; i<=3; i++){
            if(i != serverId){
                try {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();
                    Iterator it = trechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            ////////////////////////////////////////////////////////
                            if(trecho.getId() == ids[s]){
                                if(trecho.getStatus()== 0){
                                    companhia.trocarStatus(trecho, 1);
                                    trecho.setStatus(1);
                                }
                                else{
                                    listaDeEspera[ids[s]] = true;
                                }
                            }
                        }
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                    Iterator it = this.listaTrechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                if(trecho.getStatus()== 0){
                                    trecho.setStatus(1);
                                } else{
                                    listaDeEspera[ids[s]] = true;
                                }
                            }
                            
                        }
                    }
            }
        }
        return true;
    }
    /**
     * Torna disponíveis de novo os trechos quando o trecho na lista de espera é comprado por outra pessoa
     * @param ids
     * @return 
     */
    @Override
    public boolean listaDeEspera2(int ids[]){
        
        for(int i = 1; i<=3; i++){
            if(i != serverId){
                try {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();
                    Iterator it = trechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                companhia.trocarStatus(trecho, 0);
                                trecho.setStatus(0);
                                if(listaDeEspera[ids[s]]){
                                    listaDeEspera[ids[s]] = false;
                                }
                            }
                        }
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                    Iterator it = this.listaTrechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                trecho.setStatus(0);
                                if(listaDeEspera[ids[s]]){
                                    listaDeEspera[ids[s]] = false;
                                }
                            }
                        }
                    }
            }
        }
        return true;
    }
    
    
    
    @Override
    public boolean desistir(int ids[]){

        for(int i = 1; i<=3; i++){
            if(i != serverId){
                try {
                    companhia = (Companhia) Naming.lookup("127.0.0.1/PassagensAreas" + i);
                    List trechos = companhia.trechosDoServidor();
                    Iterator it = trechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                companhia.trocarStatus(trecho, 0);
                                trecho.setStatus(0);
                            }
                        }
                    }
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    Logger.getLogger(CompanhiaImplementacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                    Iterator it = this.listaTrechos.iterator();
                    for(int s = 0; s < ids.length; s++){
                        while(it.hasNext()){
                            Trecho trecho = (Trecho) it.next();
                            if(trecho.getId() == ids[s]){
                                trecho.setStatus(0);
                            }
                        }
                    }
            }
        }
        return true;
    }
}
