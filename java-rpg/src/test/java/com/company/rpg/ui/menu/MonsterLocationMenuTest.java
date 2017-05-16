package com.company.rpg.ui.menu;

import org.junit.Test;

import static org.junit.Assert.*;

public class MonsterLocationMenuTest {

    @Test
    public void testShowMenu() throws Exception {
        Menu menu = new MonsterLocationMenu();
        menu.showMenu();
        assertEquals(1, menu.getCommands().size());
    }
}