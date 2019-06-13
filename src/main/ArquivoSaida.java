/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author laairoy
 */
public class ArquivoSaida {

    private static ArquivoSaida arquivo;
    private String path;
    private List<String> list;

    private ArquivoSaida(String caminho) throws IOException {
        list = new LinkedList<>();
        path = caminho;

    }

    public static ArquivoSaida init() throws IOException {
        if (arquivo == null) {
            arquivo = new ArquivoSaida("resultado.txt");
        }
        return arquivo;
    }

    public void println(String str) throws IOException {
        list.add(str);
    }

    public void close() throws IOException {
        Charset utf8 = StandardCharsets.UTF_8;
        try {
            Files.write(Paths.get(path), list, utf8);
        } catch (IOException e) {
        }

    }

}
