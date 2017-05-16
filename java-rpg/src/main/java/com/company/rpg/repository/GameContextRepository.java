package com.company.rpg.repository;

import com.company.rpg.game.model.GameContext;

/**
 * Holds current {@link GameContext} instance
 * Provides API for getting and setting {@link GameContext}
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class GameContextRepository {

    private GameContext gameContext;

    /**
     * Check if game context exist. Create new if game context is null
     * and return current game context
     *
     * @return current {@link GameContext} instance
     */
    public GameContext getGameContext() {
        if (gameContext == null) {
            gameContext = new GameContext();
        }
        return gameContext;
    }

    /**
     * Set's given {@link GameContext} instance for global availability
     *
     * @param gameContext instance to be set
     */
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }


}
