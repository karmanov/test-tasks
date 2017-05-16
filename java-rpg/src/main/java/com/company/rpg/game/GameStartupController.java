package com.company.rpg.game;

import com.company.rpg.game.model.GameContext;
import com.company.rpg.repository.GameContextRepository;
import com.company.rpg.ui.CommonCommandsExecutor;
import com.company.rpg.ui.menu.MainMenu;
import com.company.rpg.ui.menu.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameStartupController {

    public void startupGame() {
        printBanner();
        Menu mainMenu = new MainMenu();
        GameContextRepository gameContextRepository = new GameContextRepository();
        GamePlayController gamePlayController = new GamePlayController(gameContextRepository);
        boolean modeSelected = false;
        while (!modeSelected) {
            mainMenu.showMenu();
            int selectionIndex = mainMenu.getSelectionIndex();
            if (1 == selectionIndex) {
                modeSelected = true;
                gamePlayController.startGame();
            } else if (2 == selectionIndex) {
                modeSelected = true;
                GameContext gameContext = CommonCommandsExecutor.load("save");
                if (gameContext != null) {
                    gameContextRepository.setGameContext(gameContext);
                    gamePlayController.continueGame();
                } else {
                    gamePlayController.startGame();
                }
            } else if (3 == selectionIndex) {
                CommonCommandsExecutor.exit();
            } else if (4 == selectionIndex) {
                System.out.println("User's manual will be here");
            }
        }
    }

    /**
     * Just print to the console game ASCII art
     */
    private static void printBanner() {
        try {
            String banner = new String(Files.readAllBytes(Paths.get("assets/banner.txt")));
            System.out.println(banner + "\n");
        } catch (IOException e) {
            //It's not critical, so do nothing
        }
    }
}
