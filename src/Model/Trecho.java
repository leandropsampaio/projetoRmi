package Model;

import java.io.Serializable;

/**
 * Esta classe implementa as cartas de compra, contendo os atributos de uma
 * carta.
 *
 * @author Leandro Pereira Sampaio e brnrdrosa.
 */
public class Trecho implements Serializable {

    private int id;
    private String origem;
    private String destino;
    private int companhia;
    private int quantAssentos;

    public Trecho(int id, String origem, String destino, int companhia, int quantAssentos) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.companhia = companhia;
        this.quantAssentos = quantAssentos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the origem
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the companhia
     */
    public int getCompanhia() {
        return companhia;
    }

    /**
     * @param companhia the companhia to set
     */
    public void setCompanhia(int companhia) {
        this.companhia = companhia;
    }

    public int getQuantAssentos() {
        return quantAssentos;
    }

    public void setQuantAssentos(int quantAssentos) {
        this.quantAssentos = quantAssentos;
    }
}
