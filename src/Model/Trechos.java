package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public enum Trechos {

    trecho1(1, "Salvador", "Rio de Janeiro", 1, 2),
    trecho2(2, "Salvador", "São Paulo", 1, 2),
    trecho3(3, "Natal", "Salvador", 1, 2),
    trecho4(4, "Cuiabá", "Vitória", 1, 2),
    trecho5(5, "Brasília", "Vitória", 1, 2),
    trecho6(6, "Manaus", "Vitória", 1, 2),
    trecho7(7, "Curitiba", "Aracaju", 1, 2),
    trecho8(8, "Recife", "Aracaju", 1, 2),
    trecho9(9, "Recife", "Manaus", 1, 2),
    trecho10(10, "Salvador", "São Paulo", 1, 2),
    trecho11(11, "São Paulo", "Recife", 1, 2),
    trecho12(12, "Aracaju", "Rio de Janeiro", 2, 2),
    trecho13(13, "Rio de Janeiro", "Natal", 2, 2),
    trecho14(14, "Rio de Janeiro", "Salvador", 2, 2),
    trecho15(15, "Salvador", "Brasília", 2, 2),
    trecho16(16, "Rio de Janeiro", "São Paulo", 2, 2),
    trecho17(17, "Brasília", "Fortaleza", 2, 2),
    trecho18(18, "Salvador", "Aracaju", 2, 2),
    trecho19(19, "Fortaleza", "Brasília", 2, 2),
    trecho20(20, "Recife", "São Paulo", 2, 2),
    trecho21(21, "Manaus", "Fortaleza", 2, 2),
    trecho22(22, "Natal", "Vitória", 3, 2),
    trecho23(23, "Recife", "São Paulo", 3, 2),
    trecho24(24, "São Paulo", "Salvador", 3, 2),
    trecho25(25, "Manaus", "São Paulo", 3, 2),
    trecho26(26, "Fortaleza", "Natal", 3, 2),
    trecho27(27, "Natal", "Brasília", 3, 2),
    trecho28(28, "Salvador", "Vitória", 3, 2),
    trecho29(29, "Salvador", "Manaus", 3, 2),
    trecho30(30, "Natal", "Recife", 3, 2);

    private final int id;
    private final String origem;
    private final String destino;
    private final int companhia;
    private final int quantAssentos;
    

    Trechos(int id, String origem, String destino, int companhia, int quantAssentos) {
        this.id = id;
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
            trecho = new Trecho(trechos.getId(), trechos.getOrigem(), trechos.getDestino(), trechos.getCompanhia(), trechos.getQuantAssentos());
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
    
}
