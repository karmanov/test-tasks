package com.company.rpg.map.locations;

import com.company.rpg.map.MapMarker;
import com.company.rpg.ui.menu.AbstractMenu;
import com.company.rpg.ui.menu.EmptyLocationMenu;

import java.io.Serializable;

/**
 * Empty location representation
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class EmptyLocation extends Location implements Serializable {

    private EmptyLocationMenu locationMenu;

    private static final String DESCRIPTION = "Nothing interesting here";

    public EmptyLocation(String name, int x, int y) {
        super(name, DESCRIPTION, null, LocationType.EMPTY, x, y, MapMarker.CLOSED);
        this.locationMenu = new EmptyLocationMenu();
    }

    /**
     * Print location information to the console
     */
    @Override
    public void printLocationInfo() {
        System.out.println("You are at " + getName());
        System.out.println(DESCRIPTION);
        System.out.println();
    }


    /**
     * Returns this location menu
     *
     * @return this location menu
     */
    @Override
    public AbstractMenu getLocationMenu() {
        return locationMenu;
    }

    /**
     * Print location menu to the console
     */
    @Override
    public void printLocationMenu() {
        locationMenu.showMenu();
    }
}
