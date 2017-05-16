package com.company.rpg.ui.menu;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyLocationMenuTest {

    @Test
    public void testShowMenu() throws Exception {
        Menu menu = new EmptyLocationMenu();
        menu.showMenu();
        assertEquals(8, menu.getCommands().size());
    }
}