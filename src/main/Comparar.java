/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author laairoy
 */
public class Comparar implements Comparator<Object> {

    Map<?, Integer> map;

    public Comparar(Map<Integer, Integer> map) {
        this.map = map;
    }

    public int compare(Object o1, Object o2) {

        if (map.get(o2) == map.get(o1)) {
            return 1;
        } else {
            return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));
        }

    }
}
