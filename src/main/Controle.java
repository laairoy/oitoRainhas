/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laairoy
 */
public class Controle {

    private final int tamPopulacao;
    private final int taxaMuta;
    private final int critPara;
    private final int metSelecao;
    private final int metCruzamento;
    private final int tamCromossomo;
    private Populacao populacao;

    public Controle(int tamPopulacao, int taxaMuta, int critPara, int metSelecao, int metCruzamento) {
        this.tamPopulacao = tamPopulacao;
        this.taxaMuta = taxaMuta;
        this.critPara = critPara;
        this.metSelecao = metSelecao;
        this.tamCromossomo = 8;
        this.metCruzamento = metCruzamento;
        populacao = new Populacao(tamPopulacao);

        initPopulacao();
    }

    public void iniciar() {
        if (critPara == 0) {
            selecaoEncontrar();
        } else {
            selecaoGeracao();
        }
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

    private void initPopulacao() {
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

    private boolean selecao(int geracao) {
        Selecao selecao = new Selecao(populacao.tamanho(), taxaMuta, geracao, metCruzamento);
        boolean resultado = selecao.setFitness(populacao);
        selecao.printPopulacao();
        //System.out.println();

        try {
            ArquivoSaida arq = ArquivoSaida.init();
            arq.println("");

            if (resultado == true) {
                arq.close();
                return resultado;
            }
        } catch (IOException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (metSelecao == 0) {
            populacao = selecao.selecionaRoleta();
        } else {
            populacao = selecao.selecionaTorneio();
        }
        //System.out.println();
        return resultado;
    }

    private void selecaoEncontrar() {
        boolean achou = false;
        int i = 0;
        while (achou == false) {
            achou = selecao(i);
            i++;
        }
        System.out.println("fim!");
    }

    private void selecaoGeracao() {
        for (int i = 0; i < critPara; i++) {
            if (selecao(i)) {
                System.out.println("fim!");
                return;
            }
        }
        System.out.println("fim!");
    }

}
