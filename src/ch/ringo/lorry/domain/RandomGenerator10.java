/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.ringo.lorry.domain;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author achermann
 */
class RandomGenerator10 {

    private static final RandomGenerator10 generator = new RandomGenerator10();
    private final Random random;

    private RandomGenerator10() {
        this.random = new Random(new Date().getTime());
    }

    public static RandomGenerator10 getInstance() {
        return generator;
    }

    public int next() {
        return random.nextInt(10) + 1;
    }
}
