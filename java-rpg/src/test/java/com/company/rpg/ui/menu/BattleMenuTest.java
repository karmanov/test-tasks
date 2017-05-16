package com.company.rpg.ui.menu;

import org.junit.Test;

import static org.junit.Assert.*;


public class BattleMenuTest {

    @Test
    public void testShowMenu() throws Exception {
        Menu menu = new BattleMenu();
        menu.showMenu();
        assertEquals(2, menu.getCommands().size());
    }
}