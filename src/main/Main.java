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
public class Main {

    public static void main(String[] args) {
        int tamPopulacao = 100;
        //int tamCromo = 8;
        
        Controle control = new Controle(tamPopulacao, 0, 0);
        
        control.initPopulacao();
        
        control.printPopulacao();
        
        control.selecao();
        
        //control.printPopulacao();
        
        
    }
}
