package com.company.rpg.map.locations;

import com.company.rpg.map.MapMarker;
import com.company.rpg.ui.menu.AbstractMenu;
import com.company.rpg.ui.menu.EmptyLocationMenu;
import com.company.rpg.ui.menu.Menu;
import com.company.rpg.ui.menu.MonsterLocationMenu;

import java.io.Serializable;

/**
 * Monster location class represents map
 * location with monster that should be defeated
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class MonsterLocation extends Location implements Serializable {

    private static final String DESCRIPTION = "to Monster's location";

    private MonsterLocationMenu locationMenu;

    private EmptyLocationMenu emptyLocationMenu;

    private Menu currentMenu;

    public MonsterLocation(String name,
                           int x, int y, LocationItem monster) {
        super(name, DESCRIPTION, monster, LocationType.MONSTER, x, y, MapMarker.CLOSED);
        this.locationMenu = new MonsterLocationMenu();
        this.emptyLocationMenu = new EmptyLocationMenu();
        this.currentMenu = locationMenu;

    }

    /**
     * Print location information to the console
     */
    @Override
    public void printLocationInfo() {
        System.out.println("You get into monster's cave" + getName());
        if (getLocationItem() == null && LocationType.EMPTY.equals(getLocationType())) {
            System.out.println("But it's empty.");
            this.currentMenu = emptyLocationMenu;
        } else {
            System.out.println("Old and evil monster leave here. \n He saw you and begins to attack");
        }
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
     * Print location info to the console
     */
    @Override
    public void printLocationMenu() {
        this.currentMenu.showMenu();
    }
}
