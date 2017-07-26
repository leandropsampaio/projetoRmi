package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public enum Trechos {

    trecho1("Salvador", "Rio de Janeiro", 1, 2),
    trecho2("Salvador", "São Paulo", 1, 2),
    trecho3("Natal", "Salvador", 1, 2),
    trecho4("Cuiabá", "Vitória", 1, 2),
    trecho5("Brasília", "Vitória", 1, 2),
    trecho6("Manaus", "Vitória", 1, 2),
    trecho7("Curitiba", "Aracaju", 1, 2),
    trecho8("Recife", "Aracaju", 1, 2),
    trecho9("Recife", "Manaus", 1, 2),
    trecho10("Salvador", "São Paulo", 1, 2),
    trecho11("São Paulo", "Recife", 1, 2),
    trecho12("Aracaju", "Rio de Janeiro", 2, 2),
    trecho13("Rio de Janeiro", "Natal", 2, 2),
    trecho14("Rio de Janeiro", "Natal", 2, 2),
    trecho15("Salvador", "Brasília", 2, 2),
    trecho16("Rio de Janeiro", "São Paulo", 2, 2),
    trecho17("Brasília", "Fortaleza", 2, 2),
    trecho18("Salvador", "Aracaju", 2, 2),
    trecho19("Fortaleza", "Brasília", 2, 2),
    trecho20("Recife", "São Paulo", 2, 2),
    trecho21("Manaus", "Fortaleza", 2, 2),
    trecho22("Natal", "Vitória", 3, 2),
    trecho23("Recife", "São Paulo", 3, 2),
    trecho24("São Paulo", "Salvador", 3, 2),
    trecho25("Manaus", "São Paulo", 3, 2),
    trecho26("Fortaleza", "Natal", 3, 2),
    trecho27("Natal", "Brasília", 3, 2),
    trecho28("Salvador", "Vitória", 3, 2),
    trecho29("Salvador", "Manaus", 3, 2),
    trecho30("Natal", "Recife", 3, 2);

    private final String origem;
    private final String destino;
    private final int companhia;
    private final int quantAssentos;

    Trechos(String origem, String destino, int companhia, int quantAssentos) {
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
                trecho = new Trecho(trechos.getOrigem(), trechos.getDestino(), trechos.getCompanhia(), trechos.getQuantAssentos());
                lista.add(trecho);
        }
        //Collections.shuffle(lista);
        return lista;
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
