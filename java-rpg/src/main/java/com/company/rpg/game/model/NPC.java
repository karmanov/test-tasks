package com.company.rpg.game.model;

import com.company.rpg.map.locations.LocationItem;

/**
 * This class deals with Non Player Character (NPC) and all of their properties.
 * Any method that changes a NPC or a player  interacts with it should
 * be placed within this class. If a method deals with entities in general or
 * with variables not unique to the NPC, place it in the entity class.
 */
public class NPC extends Player implements LocationItem {

    private final int expCost;

    public NPC(String name, int health, int experience, int damage, int defence, int expCost) {
        super(name, health, experience, damage, defence);
        this.expCost = expCost;
    }

    public int getExpCost() {
        return expCost;
    }
}
