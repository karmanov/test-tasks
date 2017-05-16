package com.company.rpg.ui.menu;

import com.company.rpg.game.model.GameContext;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class PlayerMenuTest {

    @Before
    public void setUp() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
    }

    @Test
    public void testShowMenu() throws Exception {
        GameContext gameContext = new GameContext();
        gameContext.setTopic("game_of_thrones");
        PlayerMenu playerMenu = new PlayerMenu(gameContext);
        playerMenu.showMenu();
        int selection = playerMenu.getSelectionIndex();

        assertEquals(3, selection);
    }
}