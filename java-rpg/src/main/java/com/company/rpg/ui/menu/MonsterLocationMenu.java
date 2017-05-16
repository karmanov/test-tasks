package com.company.rpg.ui.menu;

import java.io.Serializable;

/**
 * Monster location menu
 * Contains menu for game location that contains monsters
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class MonsterLocationMenu extends AbstractMenu implements Serializable {

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        getCommands().clear();
        getCommands().add("Fight");
        System.out.println("Please select an actions: ");
        printCommands();
        System.out.print("> ");
    }
}
