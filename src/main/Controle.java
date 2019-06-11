/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author laairoy
 */
public class Controle {

    private final int tamPopulacao;
    private final double taxaMuta;
    private final int critPara;
    private final int tamCromossomo;
    private Populacao populacao;

    public Controle(int tamPopulacao, double taxaMuta, int critPara) {
        this.tamPopulacao = tamPopulacao;
        this.taxaMuta = tamPopulacao;
        this.critPara = critPara;
        this.tamCromossomo = 8;
        populacao = new Populacao(tamPopulacao);
    }

    public int getTamPopulacao() {
        return tamPopulacao;
    }

    public double getTaxaMutacao() {
        return taxaMuta;
    }

    public int getCriterioParada() {
        return critPara;
    }

    public void initPopulacao() {
        for (int x = 0; x < tamPopulacao; x++) {
            Cromossomo cromo = new Cromossomo();
            for (int y = 0; y < tamCromossomo; y++) {
                int randNumber = (int) (Math.random() * 8 + 1);
                cromo.setGene(y, randNumber);
            }
            populacao.addIndividuo(cromo);
        }
    }

    public void printPopulacao() {
        for (int x = 0; x < populacao.tamanho(); x++) {
            System.out.println(populacao.getIndividuo(x).toString());
        }
    }

    public void selecao() {

        for (int i = 0; i < 4000; i++) {
            Selecao selecao = new Selecao();
            for (int x = 0; x < populacao.tamanho(); x++) {
                int valor = selecao.setFitness(populacao.getIndividuo(x));
                if (valor == 56) {
                    System.out.println("achou");
                    i = 100;
                    return;
                }
            }
            populacao = selecao.selecionaRoleta();
        }
    }
}
