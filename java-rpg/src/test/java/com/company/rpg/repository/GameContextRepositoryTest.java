package com.company.rpg.repository;

import com.company.rpg.game.model.GameContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameContextRepositoryTest {

    @Test
    public void testGetGameContext() throws Exception {
        GameContextRepository gameContextRepository = new GameContextRepository();
        GameContext gameContext = gameContextRepository.getGameContext();
        assertNotNull(gameContext);
    }

    @Test
    public void testSetGameContext() throws Exception {
        GameContextRepository gameContextRepository = new GameContextRepository();
        GameContext gameContext = gameContextRepository.getGameContext();
        GameContext newGameContext = new GameContext();
        newGameContext.setTopic("test");
        gameContextRepository.setGameContext(newGameContext);
        assertNotEquals(gameContext, gameContextRepository.getGameContext());
    }
}