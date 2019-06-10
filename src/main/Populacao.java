/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author laairoy
 */
public class Populacao {

    private final ArrayList<Cromossomo> populacao;
    private final int tamMax;

    public Populacao(int tamanho) {
        this.populacao = new ArrayList<>();
        this.tamMax = tamanho;
    }

    public void addIndividuo(Cromossomo cromo) {
        populacao.add(cromo);
    }

    public Cromossomo getIndividuo(int index) {
        return populacao.get(index);
    }

    public int tamanho() {
        return populacao.size();
    }

    public int tamanhoMax() {
        return tamMax;
    }
}
