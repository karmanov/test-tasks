package com.company.rpg.game.battle;

import com.company.rpg.map.locations.Location;
import com.company.rpg.game.model.Player;

/**
 * Basic interface for handling battles between player and different NPC
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public interface BattleService {

    /**
     * Executes battle logic
     *
     * @param hero     - player
     * @param location - NPC's location
     * @return updated {@link Location}
     */
    Location battle(Player hero, Location location);
}
