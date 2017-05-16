package com.company.rpg.ui.menu;

/**
 * Main game menu. Give access to main games function such as
 * 1. Start new game;
 * 2. Load previously saved game
 * 3. Show user's manual
 * 4. Exit the game
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class MainMenu extends AbstractMenu {

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        System.out.println("Main Menu:");
        addCommandIfNotExist("Start new game");
        addCommandIfNotExist("Load game");
        addCommandIfNotExist("Exit");
        addCommandIfNotExist("Help");
        printCommands();
        System.out.print("> ");
    }

    private void addCommandIfNotExist(String command) {
        if (!getCommands().contains(command)) {
            getCommands().add(command);
        }
    }
}
