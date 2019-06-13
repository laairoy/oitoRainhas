/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author laairoy
 */
public class ArquivoSaida {
    private static ArquivoSaida arquivo;
    private File file;
    private BufferedWriter buff;
    
    private ArquivoSaida(String caminho) throws IOException{
        file = new File(caminho);
        buff = new BufferedWriter(new FileWriter(file));
    }
    
    public static ArquivoSaida init() throws IOException{
        if(arquivo == null){
            arquivo = new ArquivoSaida("resultado.txt");
        }
        return arquivo;
    }
    
    public void println(String str) throws IOException{
        buff.write(str + "\n");
    }
    
    public void close() throws IOException{
        buff.close();
        arquivo = null;
        
    }
    
    
}
