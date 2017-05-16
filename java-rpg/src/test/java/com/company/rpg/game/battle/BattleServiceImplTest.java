package com.company.rpg.game.battle;

import com.company.rpg.game.model.NPC;
import com.company.rpg.game.model.Player;
import com.company.rpg.map.locations.Location;
import com.company.rpg.map.locations.LocationType;
import com.company.rpg.map.locations.MonsterLocation;
import com.company.rpg.utils.RandomUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomUtil.class)
public class BattleServiceImplTest {


    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void testBattleWin() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        PowerMockito.mockStatic(RandomUtil.class);
        Mockito.when(RandomUtil.nextInt(10)).thenReturn(1);
        NPC npc = new NPC("NPC", 1, 1, 10, 30, 500);
        Player player = new Player("Player", 100, 100, 100, 30);
        Location location = new MonsterLocation("Monster location", 0, 1, npc);
        BattleService battleService = new BattleServiceImpl();
        Location battleLocation = battleService.battle(player, location);
        assertEquals(600, player.getExperience());
        assertNull(battleLocation.getLocationItem());
        assertEquals(LocationType.EMPTY, battleLocation.getLocationType());
    }

    @Test
    public void testBattleLost() throws Exception {
        exit.expectSystemExitWithStatus(0);
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        PowerMockito.mockStatic(RandomUtil.class);
        Mockito.when(RandomUtil.nextInt(10)).thenReturn(10);
        Mockito.when(RandomUtil.nextInt(100)).thenReturn(20);
        NPC npc = new NPC("NPC", 1000, 1, 100, 300, 500);
        Player player = new Player("Player", 1, 100, 1, 1);
        Location location = new MonsterLocation("Monster location", 0, 1, npc);
        BattleService battleService = new BattleServiceImpl();
        battleService.battle(player, location);
    }
}