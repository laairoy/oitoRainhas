/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author laairoy
 */
public class Selecao {

    private final ArrayList<Integer> fitness;
    private final ArrayList<Cromossomo> cromossomos;
    private final Map<Cromossomo, Integer> cromo;
    private int tamPopulacao;

    private int somaFitness;

    public Selecao() {
        this.fitness = new ArrayList<>();
        this.cromossomos = new ArrayList<>();
        this.somaFitness = 0;
        this.cromo = new HashMap<>();
        this.tamPopulacao = 10;
    }

    public int setFitness(Cromossomo cromo) {

        int colisao = getColisao(cromo);

        cromossomos.add(cromo);
        fitness.add(colisao);
        somaFitness += colisao;
        this.cromo.put(cromo, 56 - colisao);

        //System.out.println(cromo.toString() + " " + (49 - colisao));
        if((56- colisao) == 56) {
            System.out.println("-> "+cromo.toString());
        }
        return 56 - colisao;

    }

    private int getColisao(Cromossomo cromo) {
        int contar = 0;
        for (int a = 0; a < cromo.tamanho(); a++) {
            for (int i = 0; i < cromo.tamanho(); i++) {
                int x = Math.abs(a - i);
                int y = Math.abs(cromo.getAlelo(a) - cromo.getAlelo(i));

                //verifica diagonais e laterais
                if (a != i) {
                    if (x == y || Objects.equals(cromo.getAlelo(a), cromo.getAlelo(i))) {
                        contar++;
                        //break;

                    }

                }
            }
        }

        return contar;
    }

    public Populacao selecionaRoleta() {
        Map<Integer, Integer> hmap = new HashMap<>();
        ArrayList<Cromossomo> parentes = new ArrayList<>();

        for (int i = 0; i < fitness.size(); i++) {
            hmap.put(i, fitness.get(i));
        }

        Comparar comp = new Comparar(hmap);
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>(comp);

        map.putAll(hmap);
        //System.out.println(map);

        //int index =0;
        for (int i = 0; i < fitness.size(); i++) {
            Double randValor = Math.random() * 360;
            int keyselected = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                Double valor;
                valor = 360 * ((double) (entry.getValue()) / 62);
                //System.out.println(randValor + "-> " + (valor));
                if (randValor > valor) {
                    keyselected = entry.getKey();
                    break;
                }
            }
            //System.out.println(keyselected);
            Cromossomo novoCromo;
            try {
                novoCromo = new Cromossomo(this.cromossomos.get(keyselected).getCromossomo());
                parentes.add(novoCromo);
            } catch (Exception ex) {
                Logger.getLogger(Selecao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return cruzamentoUniforme(parentes);
    }

    private Populacao cruzamentoUniforme(ArrayList<Cromossomo> cromo) {
        Populacao pop = new Populacao(tamPopulacao);

        for (int x = 1; x < cromo.size(); x = x + 2) {
            Cromossomo filho1 = new Cromossomo();
            Cromossomo filho2 = new Cromossomo();
            Cromossomo pai = cromo.get(x - 1);
            Cromossomo mae = cromo.get(x);

            for (int i = 0; i < 8; i++) {
                int rand = (int) (Math.random() * 1);
                if (rand == 1) {
                    filho1.setGene(i, pai.getAlelo(i));
                    filho2.setGene(i, mae.getAlelo(i));
                } else {
                    filho1.setGene(i, mae.getAlelo(i));
                    filho2.setGene(i, pai.getAlelo(i));
                }

            }
            pop.addIndividuo(mutacao(0.05, filho1));
            pop.addIndividuo(mutacao(0.05, filho2));
        }

        return pop;

    }

    private Cromossomo mutacao(double taxa, Cromossomo cromo) {

        for (int x = 0; x < cromo.tamanho(); x++) {
            double muta = Math.random() * 1;

            if (muta < taxa) {
                cromo.setGene(x, (int) (Math.random() * 8 + 1));
            }
        }

        return cromo;
    }

}
