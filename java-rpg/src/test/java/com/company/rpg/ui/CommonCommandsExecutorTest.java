package com.company.rpg.ui;

import com.company.rpg.game.model.GameContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.Assert.*;

public class CommonCommandsExecutorTest {

    public static final String TEST_SAVE = "test-save";

    @Test
    public void testLoad() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        GameContext gameContext = new GameContext();
        String name = "testSave";
        CommonCommandsExecutor.save(gameContext, name, TEST_SAVE);
        GameContext loadedGameContext = CommonCommandsExecutor.load(TEST_SAVE);
        assertNotNull(loadedGameContext);
        assertEquals(gameContext, loadedGameContext);
    }

    @Test
    public void testSave() throws Exception {
        GameContext gameContext = new GameContext();
        String name = "testSave";
        CommonCommandsExecutor.save(gameContext, name, TEST_SAVE);
        assertTrue("Save file not found", new File(TEST_SAVE + "/" + name + ".ser").exists());
    }
}