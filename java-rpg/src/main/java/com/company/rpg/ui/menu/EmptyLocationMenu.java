package com.company.rpg.ui.menu;

import java.io.Serializable;

/**
 * Provide a list of actions available in the {@link com.company.rpg.map.locations.EmptyLocation}
 * to the user
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class EmptyLocationMenu extends AbstractMenu implements Serializable {

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        getCommands().clear();
        getCommands().add("North");
        getCommands().add("South");
        getCommands().add("West");
        getCommands().add("East");
        getCommands().add("Map");
        getCommands().add("Statistics");
        getCommands().add("Save");
        getCommands().add("Exit");
        System.out.println("Please select an action: ");
        printCommands();
        System.out.print("> ");
    }
}
