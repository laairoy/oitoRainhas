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
    private final int tamPopu;
    private final double taxaMuta;
    private final int critPara;
    
    public Controle(int tamPopu, double taxaMuta, int critPara){
        this.tamPopu = tamPopu;
        this.taxaMuta = tamPopu;
        this.critPara = critPara;
    }

    public int getTamPopu() {
        return tamPopu;
    }

    public double getTaxaMuta() {
        return taxaMuta;
    }

    public int getCritPara() {
        return critPara;
    }

}
