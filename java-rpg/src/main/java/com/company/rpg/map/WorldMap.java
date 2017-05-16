package com.company.rpg.map;

import com.company.rpg.map.locations.EmptyLocation;
import com.company.rpg.map.locations.Location;
import com.company.rpg.map.locations.MonsterLocation;
import com.company.rpg.game.model.NPC;
import com.company.rpg.utils.RandomUtil;

import java.io.Serializable;

/**
 * Worlds map. Contains logic to generate and store world map,
 * current player locations, printing map player movement
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class WorldMap implements Serializable {

    private static final String MAP_WHITESPACE = "     ";

    private Location[][] map;

    private int mapSize;
    private int countOfMonsters;


    public WorldMap(int mapSize, int countOfMonsters) {
        this.mapSize = mapSize;
        this.map = new Location[mapSize][mapSize];
        this.countOfMonsters = countOfMonsters;
    }

    /**
     * Print current map state to the console
     *
     * @param currentLocation - current player location
     * @param topic           current game topic
     */
    public void printMap(Location currentLocation, String topic) {
        System.out.println("Map of " + topic + "'s world");
        printBorder();
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (currentLocation.getX() == i && currentLocation.getY() == j) {
                    System.out.print(MapMarker.PLAYER.getMarker() + MAP_WHITESPACE);
                } else {
                    System.out.print(map[i][j].getMapMarker().getMarker() + MAP_WHITESPACE);
                }
            }
            System.out.println();
            System.out.println();
        }
        printBorder();
        System.out.println();
        printLegend();
    }

    /**
     * Prints border around the map
     */
    private void printBorder() {
        for (int i = 0; i < map.length + ((map.length - 1) * 5); i++) {
            System.out.print("-");
        }
    }

    /**
     * Generate world's map. Add empty and monster locations in random order
     */
    public void init() {
        System.out.println("Generating the world...");
        int numberOfGeneratedMonsters = 0;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                //skip the first location from random generating loop
                if (i == 0 && j == 0) {
                    map[i][j] = new EmptyLocation("large desert.", i, j);
                    continue;
                }
                int magicNumber = RandomUtil.nextIntInRange(0, 100);
                if (magicNumber > 50 && numberOfGeneratedMonsters <= countOfMonsters) {
                    addMonsterLocation(i, j);
                    numberOfGeneratedMonsters++;
                } else {
                    map[i][j] = new EmptyLocation("desert", i, j);
                }
            }
        }
        //To verify that required number of monsters was generated
        while (numberOfGeneratedMonsters <= countOfMonsters) {
            int randX = RandomUtil.nextIntInRange(0, 9);
            int randY = RandomUtil.nextIntInRange(0, 9);
            if (randX != 0 && randY != 0) {
                addMonsterLocation(randX, randY);
                numberOfGeneratedMonsters++;
            }
        }

        System.out.println();
    }

    /**
     * Generate and set new {@link NPC} monster to the location
     * by given coordinates
     *
     * @param x coordinate of the location on the map
     * @param y coordinate of the location on the map
     */
    private void addMonsterLocation(int x, int y) {
        NPC monster = new NPC("Goblin", 90, 100, 10, 10, 50);
        map[x][y] = new MonsterLocation("This is monster location", x, y, monster);
    }

    /**
     * Check if all locations on the map already opened
     *
     * @return true if all location already opened,
     * false if there are some closed locations
     */
    public boolean isAllLocationsOpened() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                Location location = map[i][j];
                if (!location.isOpened()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Location getLocation(int x, int y) {
        return map[x][y];
    }

    public void updateLocation(Location newLocation) {
        map[newLocation.getX()][newLocation.getY()] = newLocation;
    }

    /**
     * Move player around the world's map
     *
     * @param location  - current player location
     * @param direction where player should be moved
     * @return new player's location
     */
    public Location move(Location location, Direction direction) {
        int x = location.getX();
        int y = location.getY();
        if (Direction.EAST.equals(direction)) {
            if (y == mapSize - 1) {
                printError();
            } else {
                map[x][y].setIsOpened(true);
                map[x][y].setMapMarker(MapMarker.EMPTY);
                y++;
            }
        } else if (Direction.WEST.equals(direction)) {
            if (y == 0) {
                printError();
            } else {
                map[x][y].setIsOpened(true);
                map[x][y].setMapMarker(MapMarker.EMPTY);
                y--;
            }

        } else if (Direction.NORTH.equals(direction)) {
            if (x == 0) {
                printError();
            } else {
                map[x][y].setIsOpened(true);
                map[x][y].setMapMarker(MapMarker.EMPTY);
                x--;
            }
        } else if (Direction.SOUTH.equals(direction)) {
            if (x == mapSize - 1) {
                printError();
            } else {
                map[x][y].setIsOpened(true);
                map[x][y].setMapMarker(MapMarker.EMPTY);
                x++;
            }
        }
        return map[x][y];
    }

    /**
     * Prints to the console map's legend
     */
    private void printLegend() {
        System.out.println("Legend:");
        System.out.println("    0 = Explored empty location");
        System.out.println("    * = Unexplored location");
        System.out.println("    M = Location with monster");
        System.out.println("    P = Current player's location");
        System.out.println();


    }

    /**
     * Prints to the console error message if selected location
     * is not reachable
     */
    private void printError() {
        System.err.println("> WARNING: Dragons live further, please find another way");
    }
}
