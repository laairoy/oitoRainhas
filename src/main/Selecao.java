/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
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
    private final int tamPopulacao;
    private final int taxaMutacao;
    private final int metCruzamento;
    int geracao;

    private int somaFitness;

    public Selecao(int tamPopulacao, int taxaMutacao, int geracao, int metCruzamento) {
        this.fitness = new ArrayList<>();
        this.cromossomos = new ArrayList<>();
        this.somaFitness = 0;
        this.cromo = new HashMap<>();
        this.tamPopulacao = tamPopulacao;
        this.taxaMutacao = taxaMutacao;
        this.geracao = geracao;
        this.metCruzamento = metCruzamento;
    }

    public boolean setFitness(Populacao populacao) {
        boolean bool = false;
        String melhor = null;
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
                //melhor = "Melhor: " + cromossomo.toString() + " Fitness: " + 56;
                //System.out.println(melhor);
                bool = true;
            }
            //return 56 - colisao;
        }
        if (melhor != null) {
            ArquivoSaida arq;
            try {
                arq = ArquivoSaida.init();
                arq.println(melhor);
            } catch (IOException ex) {
                Logger.getLogger(Selecao.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        ArrayList<Cromossomo> parentes = new ArrayList<>();
        SortedMap<Integer, Integer> map = organizarPopulacao();

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

        return cruzamento(parentes);
    }

    private SortedMap<Integer, Integer> organizarPopulacao() {
        Map<Integer, Integer> hmap = new HashMap<>();

        for (int i = 0; i < fitness.size(); i++) {
            hmap.put(i, fitness.get(i));
        }

        Comparar comp = new Comparar(hmap);
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>(comp);

        map.putAll(hmap);
        try {
            ArquivoSaida arq = ArquivoSaida.init();
            arq.println("Melhor: " + this.cromossomos.get(map.firstKey()).toString() + " " + map.values().iterator().next());
        } catch (IOException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    private Cromossomo mutacao(Cromossomo cromo) {
        Cromossomo novo = new Cromossomo();
        for (int x = 0; x < cromo.tamanho(); x++) {
            int muta = (int) (Math.random() * 100);
            //System.out.println("taxa "+ muta +" >" + taxaMutacao);
            if (muta < taxaMutacao) {

                novo.setGene(x, (int) (Math.random() * 8 + 1));
            } else {
                novo.setGene(x, cromo.getGene(x));
            }

        }

        return novo;
    }

    public void printPopulacao() {
        int count = 0;
        for (Map.Entry<Cromossomo, Integer> entry : cromo.entrySet()) {
            ArquivoSaida arq;
            try {
                arq = ArquivoSaida.init();
                arq.println("g" + geracao + " i" + count + " : " + entry.getKey().toString() + " Fitness: " + entry.getValue());
            } catch (IOException ex) {
                Logger.getLogger(Selecao.class.getName()).log(Level.SEVERE, null, ex);
            }

            // System.out.println("g" + geracao + " i" + count + " : " + entry.getKey().toString() + " Fitness: " + entry.getValue());
            count++;
        }
    }

    public Populacao selecionaTorneio() {
        ArrayList<Cromossomo> parentes = new ArrayList<>();
        SortedMap<Integer, Integer> map = organizarPopulacao();
        int tamTorneio = fitness.size() / 10;

        if (tamTorneio < 2) {
            tamTorneio = 2;
        }

        for (int i = 0; i < fitness.size(); i++) {
            Integer keyselected = (int) (Math.random() * fitness.size());

            for (int x = 1; x < tamTorneio; x++) {
                Integer sorteio = (int) (Math.random() * fitness.size());

                if (fitness.get(sorteio) > fitness.get(keyselected)) {
                    keyselected = sorteio;
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

        return cruzamento(parentes);
    }

    private Populacao cruzamento(ArrayList<Cromossomo> cromo) {
        Populacao pop = new Populacao(tamPopulacao);

        for (int x = 1; x < tamPopulacao; x = x + 2) {

            Cromossomo pai = cromo.get(x - 1);
            Cromossomo mae = cromo.get(x);
            ArrayList<Cromossomo> temp;

            if (metCruzamento == 0) {
                //System.out.println("Cruzamento Uniforme");
                temp = cruzamentoUniforme(pai, mae);
            } else {
                //System.out.println("Cruzamento de um ponto");
                temp = cruzamentoUmPonto(pai, mae);
            }

            Cromossomo mut1 = mutacao(temp.get(0));
            Cromossomo mut2 = mutacao(temp.get(1));

            try {
                ArquivoSaida arq = ArquivoSaida.init();
                arq.println("Cruzamento: " + " Pais " + pai.toString() + "+" + mae.toString()
                        + " => F1: " + temp.get(0).toString() + ", M: " + mut1.toString());;
                arq.println("Cruzamento: " + " Pais " + pai.toString() + "+" + mae.toString()
                        + " => F2: " + temp.get(1).toString() + ", M: " + mut2.toString());
            } catch (IOException ex) {
                Logger.getLogger(Selecao.class.getName()).log(Level.SEVERE, null, ex);
            }

            pop.addIndividuo(mut1);
            pop.addIndividuo(mut2);
        }

        return pop;

    }

    private ArrayList<Cromossomo> cruzamentoUmPonto(Cromossomo pai, Cromossomo mae) {
        ArrayList<Cromossomo> list = new ArrayList<>();
        Cromossomo filho1 = new Cromossomo();
        Cromossomo filho2 = new Cromossomo();

        for (int i = 0; i < 8; i++) {
            //int rand = (int) (Math.random() * 10);
            if (i < 4) {
                filho1.setGene(i, pai.getAlelo(i));
                filho2.setGene(i, mae.getAlelo(i));
            } else {
                filho1.setGene(i, mae.getAlelo(i));
                filho2.setGene(i, pai.getAlelo(i));
            }

        }
        list.add(filho1);
        list.add(filho2);
        return list;

    }

    private ArrayList<Cromossomo> cruzamentoUniforme(Cromossomo pai, Cromossomo mae) {
        ArrayList<Cromossomo> list = new ArrayList<>();
        Cromossomo filho1 = new Cromossomo();
        Cromossomo filho2 = new Cromossomo();

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
        list.add(filho1);
        list.add(filho2);
        return list;

    }
}
