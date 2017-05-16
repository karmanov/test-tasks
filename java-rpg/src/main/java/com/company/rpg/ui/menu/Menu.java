package com.company.rpg.ui.menu;

import java.util.List;

/**
 * Basic menu interface. Define the required function for each menu in the game
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public interface Menu {

    /**
     * Print to the console numbered menu
     */
    void showMenu();

    /**
     * Get player's entered value to the console
     * and validates it.
     *
     * @return index of player's selected value
     */
    int getSelectionIndex();

    /**
     * Get command from list commands list by given index
     *
     * @param index of the selected command
     * @return string representation of selected command
     */
    String getCommandByIndex(int index);

    /**
     * Return list of available commands commands
     *
     * @return list of available commands commands
     */
    List<String> getCommands();
}
