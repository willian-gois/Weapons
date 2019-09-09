package br.com.finalelite.weapons.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Chance<V> {

    private final Random random;

    public Chance() {
        this.random = new Random();
    }

    public V getWinner(HashMap<V, NumberChance> values) {
        int valueWinner = random.nextInt(100);
        for (Entry<V, NumberChance> value : values.entrySet()) {
            if (valueWinner >= value.getValue().getNumberMin() && valueWinner <= value.getValue().getNumberMax()) {
                return value.getKey();
            }
        }
        return null;
    }

    public static boolean getPercent(int percent) {
        return new Random().nextInt(99) <= percent;
    }

    public static class NumberChance {

        double numberMin;
        double numberMax;

        public NumberChance(double numberMin, double numberMax) {
            this.numberMin = numberMin;
            this.numberMax = numberMax;
        }

        public double getNumberMin() {
            return numberMin;
        }

        public double getNumberMax() {
            return numberMax;
        }
    }
}
