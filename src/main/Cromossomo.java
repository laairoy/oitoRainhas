/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Arrays;

/**
 *
 * @author laairoy
 */
public class Cromossomo {

    private final Integer[] cromo;

    public Cromossomo(Integer[] cromo) throws Exception {
        if (cromo.length != 8) {
            
            throw new IllegalArgumentException("Cromossomo com tamanho inválido!");
        }
        this.cromo = cromo;
    }
    
    public Cromossomo(){
        this.cromo = new Integer[8];
    }
    
    public Integer getAlelo(int index){
        return this.cromo[index];
    }
    
    public Integer[] getCromossomo(){
        return this.cromo;
    }
    
    public void setGene(int locus, int alelo){
        cromo[locus] = alelo;
    }
    
    public int getGene(int locus){
        return cromo[locus];
    }
    
    public int tamanho(){
        return cromo.length;
    }
    
    @Override
    public String toString(){
        return Arrays.toString(cromo);
    }
    
}
