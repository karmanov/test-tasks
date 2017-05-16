package com.company.rpg.ui.menu;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AbstractMenuTest {

    private AbstractMenu menu;

    @Before
    public void setUp() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        menu = new BattleMenu();
        menu.showMenu();

    }

    @Test
    public void testGetSelectionIndex() throws Exception {
        int selectionIndex = menu.getSelectionIndex();
        assertEquals(1, selectionIndex);
    }

    @Test
    public void testGetCommandByIndex() throws Exception {
        int selectionIndex = menu.getSelectionIndex();
        String commandByIndex = menu.getCommandByIndex(selectionIndex);
        assertEquals("Attack", commandByIndex);

    }

    @Test
    public void testGetCommands() throws Exception {
        List<String> commands = menu.getCommands();
        assertEquals(2, commands.size());
        assertTrue(commands.contains("Attack"));
    }


}