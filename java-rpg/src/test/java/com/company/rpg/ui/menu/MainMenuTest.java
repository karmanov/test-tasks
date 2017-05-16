package com.company.rpg.ui.menu;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MainMenuTest {

    @Test
    public void testShotMenu() throws Exception {
        Menu menu = new MainMenu();
        menu.showMenu();
        assertEquals(4, menu.getCommands().size());
    }

}