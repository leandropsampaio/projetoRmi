package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public enum Trechos {

    //0-disponivel 1-reservado 2-comprado
    trecho1(1, "Salvador", "Rio de Janeiro", 1, 1, 0),
    trecho2(2, "Salvador", "São Paulo", 1, 1, 0),
    trecho3(3, "Natal", "Salvador", 1, 1, 0),
    trecho4(4, "Cuiabá", "Vitória", 1, 1, 0),
    trecho5(5, "Brasília", "Vitória", 1, 1, 0),
    trecho6(6, "Manaus", "Vitória", 1, 1, 0),
    trecho7(7, "Curitiba", "Aracaju", 1, 1, 0),
    trecho8(8, "Recife", "Aracaju", 1, 1, 0),
    trecho9(9, "Recife", "Manaus", 1, 1, 0),
    trecho10(10, "Salvador", "São Paulo", 1, 1, 0),
    trecho11(11, "São Paulo", "Recife", 1, 1, 0),
    trecho12(12, "Aracaju", "Rio de Janeiro", 2, 1, 0),
    trecho13(13, "Rio de Janeiro", "Natal", 2, 1, 0),
    trecho14(14, "Rio de Janeiro", "Salvador", 2, 1, 0),
    trecho15(15, "Salvador", "Brasília", 2, 1, 0),
    trecho16(16, "Rio de Janeiro", "São Paulo", 2, 1, 0),
    trecho17(17, "Brasília", "Fortaleza", 2, 1, 0),
    trecho18(18, "Salvador", "Aracaju", 2, 1, 0),
    trecho19(19, "Fortaleza", "Brasília", 2, 1, 0),
    trecho20(20, "Recife", "São Paulo", 2, 1, 0),
    trecho21(21, "Manaus", "Fortaleza", 2, 1, 0),
    trecho22(22, "Natal", "Vitória", 3, 1, 0),
    trecho23(23, "Recife", "São Paulo", 3, 1, 0),
    trecho24(24, "São Paulo", "Salvador", 3, 1, 0),
    trecho25(25, "Manaus", "São Paulo", 3, 1, 0),
    trecho26(26, "Fortaleza", "Natal", 3, 1, 0),
    trecho27(27, "Natal", "Brasília", 3, 1, 0),
    trecho28(28, "Salvador", "Vitória", 3, 1, 0),
    trecho29(29, "Salvador", "Manaus", 3, 1, 0),
    trecho30(30, "Natal", "Recife", 3, 1, 0);

    private final int id;
    private final int status;
    private final String origem;
    private final String destino;
    private final int companhia;
    private final int quantAssentos;

    Trechos(int id, String origem, String destino, int companhia, int quantAssentos, int status) {
        this.id = id;
        this.status = status;
        this.origem = origem;
        this.destino = destino;
        this.companhia = companhia;
        this.quantAssentos = quantAssentos;
    }

    private static final Trechos[] trechos = values();

    /**
     * Método responsável por retornar a lista de cartas.
     *
     * return List - Lista de cartas de compras e entreterimento
     */
    public static List retornarListaTrechos() {
        Trecho trecho;
        List<Trecho> lista = new ArrayList<>();

        for (Trechos trechos : trechos) {
            trecho = new Trecho(trechos.getId(), trechos.getOrigem(), trechos.getDestino(), trechos.getCompanhia(), trechos.getQuantAssentos(), trechos.getStatus());
            lista.add(trecho);
        }
        //Collections.shuffle(lista);
        return lista;
    }

    public int getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public int getCompanhia() {
        return companhia;
    }

    public int getQuantAssentos() {
        return quantAssentos;
    }

    public int getStatus() {
        return status;
    }
}