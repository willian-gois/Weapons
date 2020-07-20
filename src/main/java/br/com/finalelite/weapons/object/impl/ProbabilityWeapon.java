package br.com.finalelite.weapons.object.impl;

import br.com.finalelite.weapons.object.Weapon;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public abstract class ProbabilityWeapon extends Weapon {

    public ProbabilityWeapon(String id) {
        super(id);
    }

    protected boolean chance(double percentage, int level) {
        return ThreadLocalRandom.current().nextInt(10) <= (percentage * level) / 100;
    }
}
