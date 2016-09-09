/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ringo.lorry.domain;

/**
 *
 * @author achermann
 */
public class ArithmeticProblem {
    private int multiplicator = 0;
    private int multiplicand = 0;
    private int product = 0;

    public ArithmeticProblem(int series) {
        multiplicator = RandomGenerator10.getInstance().next();
        multiplicand = series;
        product = multiplicator * multiplicand;
    }
;

    public String getDescription () {
        return multiplicator + " x " + multiplicand + " = ";
    }
    
    public boolean verify (int proposal) {
        return proposal == product;
    }
}
