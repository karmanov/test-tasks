package com.company.rpg.ui;

import com.company.rpg.game.model.GameContext;
import com.company.rpg.ui.menu.GameLoadMenu;
import com.company.rpg.ui.menu.Menu;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Utility class contains common game methods
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class CommonCommandsExecutor {

    /**
     * Iterate over "save" directory and output to the user's console
     * list of all saved games in menu view (numbered list, with ability to select)
     * Deserialize selected file to {@link GameContext} class instance and return it.
     *
     * @param saveDirectory directory contains save game files
     * @return deserialized file to {@link GameContext} class instance
     */
    public static GameContext load(String saveDirectory) {
        Menu gameLoadMenu = new GameLoadMenu(saveDirectory);
        gameLoadMenu.showMenu();
        if (gameLoadMenu.getCommands().isEmpty()) {
            return null;
        }
        int selectionIndex = gameLoadMenu.getSelectionIndex();
        String selectedFile = gameLoadMenu.getCommandByIndex(selectionIndex);
        System.out.println("Loading game....");
        try {
            ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(selectedFile)));
            return (GameContext) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error occurred during loading the game. Reason: " + e.getMessage());
            return null;
        }
    }

    /**
     * Serialize given {@link GameContext} instance to the file under "save" directory
     * with current timestamp as file name
     *
     * @param gameContext to serialize
     */

    public static void save(GameContext gameContext, String name, String saveDirectory) {
        System.out.println("Saving current game....");
        try {
            File saveDir = new File(saveDirectory);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(saveDirectory + "/" + name + ".ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameContext);
            System.out.println("Saved game to the file: " + name + ".ser \n");
        } catch (IOException e) {
            System.err.println("Could not save game. Reason: " + e.getMessage());
        }
    }

    /**
     * Exit from the game
     */
    public static void exit() {
        System.out.println("Thanks you for playing Java RPG.");
        System.exit(0);
    }
}
