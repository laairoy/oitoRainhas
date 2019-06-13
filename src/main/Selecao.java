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
    private int taxaMutacao;
    int geracao;

    private int somaFitness;

    public Selecao(int tamPopulacao, int taxaMutacao, int geracao) {
        this.fitness = new ArrayList<>();
        this.cromossomos = new ArrayList<>();
        this.somaFitness = 0;
        this.cromo = new HashMap<>();
        this.tamPopulacao = tamPopulacao;
        this.taxaMutacao = taxaMutacao;
        this.geracao = geracao;
    }

    public boolean setFitness(Populacao populacao) {
        boolean bool = false;
        for (int x = 0; x < populacao.tamanho(); x++) {
            //int valor = selecao.setFitness(populacao.getIndividuo(x));
            Cromossomo cromossomo = populacao.getIndividuo(x);

            int colisao = getColisao(cromossomo);

            cromossomos.add(cromossomo);
            fitness.add(56 - colisao);
            somaFitness += colisao;
            this.cromo.put(cromossomo, 56 - colisao);

            //System.out.println(cromo.toString() + " " + (49 - colisao));
            if ((56 - colisao) == 56) {
                System.out.println("-> " + cromossomo.toString());
                bool = true;
            }
            //return 56 - colisao;
        }
        return bool;
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

        System.out.println("Melhor: " + this.cromossomos.get(map.firstKey()).toString() + " " + map.values().iterator().next());
        //System.out.println();

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

        for (int x = 1; x < tamPopulacao; x = x + 2) {
            Cromossomo filho1 = new Cromossomo();
            Cromossomo filho2 = new Cromossomo();
            Cromossomo pai = cromo.get(x - 1);
            Cromossomo mae = cromo.get(x);

            for (int i = 0; i < 8; i++) {
                int rand = (int) (Math.random() * 10);
                if (rand < 5) {
                    filho1.setGene(i, pai.getAlelo(i));
                    filho2.setGene(i, mae.getAlelo(i));
                } else {
                    filho1.setGene(i, mae.getAlelo(i));
                    filho2.setGene(i, pai.getAlelo(i));
                }

            }
            Cromossomo mut1 = mutacao(filho1);
            Cromossomo mut2 = mutacao(filho2);
            
            System.out.println("Cruzamento: " + " Pais " + pai.toString() + "+" + mae.toString()
                    + " => F1: " + filho1.toString() + ", M: " + mut1.toString());
            System.out.println("Cruzamento: " + " Pais " + pai.toString() + "+" + mae.toString()
                    + " => F2: " + filho2.toString() + ", M: " + mut2.toString());
            
            
            pop.addIndividuo(mut1);
            pop.addIndividuo(mut2);
        }

        return pop;

    }

    private Cromossomo mutacao(Cromossomo cromo) {
        Cromossomo novo = new Cromossomo();
        for (int x = 0; x < cromo.tamanho(); x++) {
            int muta = (int) (Math.random() * 100);
            //System.out.println("taxa "+ muta +" >" + taxaMutacao);
            if (muta < taxaMutacao) {
                
                novo.setGene(x, (int) (Math.random() * 8 + 1));
            }
            else {
                novo.setGene(x, cromo.getGene(x));
            }

        }

        return novo;
    }

    public void printPopulacao() {
        int count = 0;
        for (Map.Entry<Cromossomo, Integer> entry : cromo.entrySet()) {
            System.out.println("g" + geracao + " i" + count + " : " + entry.getKey().toString() + " Fitness: " + entry.getValue());
            count++;
        }
    }
}
