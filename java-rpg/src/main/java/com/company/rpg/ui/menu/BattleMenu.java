package com.company.rpg.ui.menu;

import java.io.Serializable;

/**
 * Battler menu class contains menu commands
 * that player can use during battle
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class BattleMenu extends AbstractMenu implements Serializable {


    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        getCommands().clear();
        getCommands().add("Attack");
        getCommands().add("Charge Attack");
        System.out.println("What would you like to do?");
        System.out.println("");
        printCommands();
        System.out.print("> ");
    }
}
