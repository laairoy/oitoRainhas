/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author laairoy
 */
public class Selecao {

    private final ArrayList<Integer> fitness;
    private final ArrayList<Cromossomo> cromossomos;

    public Selecao() {
        this.fitness = new ArrayList<>();
        this.cromossomos = new ArrayList<>();
    }

    public void setFitness(Cromossomo cromo) {
        int colisao = 0;
        for (int x = 0; x < cromo.tamanho(); x++) {
            colisao += getColisao(x, cromo);
        }

        cromossomos.add(cromo);
        fitness.add(colisao);
        System.out.println(cromo.toString() + " " + (28 - colisao));
    }

    private int getColisao(int index, Cromossomo cromo) {
        int contar = 0;

        for (int i = 0; i < cromo.tamanho(); i++) {
            int x = Math.abs(index - i);
            int y = Math.abs(cromo.getAlelo(index) - cromo.getAlelo(i));

            //verifica diagonais e laterais
            if (index != i) {
                if (x == y || Objects.equals(cromo.getAlelo(index), cromo.getAlelo(i))) {
                    contar++;

                }

            }
        }

        return contar;
    }
}
