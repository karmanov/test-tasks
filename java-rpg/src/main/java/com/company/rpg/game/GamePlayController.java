package com.company.rpg.game;

import com.company.rpg.game.battle.BattleService;
import com.company.rpg.game.battle.BattleServiceImpl;
import com.company.rpg.map.Direction;
import com.company.rpg.map.WorldMap;
import com.company.rpg.map.locations.Location;
import com.company.rpg.game.model.GameContext;
import com.company.rpg.game.model.Player;
import com.company.rpg.repository.GameContextRepository;
import com.company.rpg.ui.CommonCommands;
import com.company.rpg.ui.CommonCommandsExecutor;
import com.company.rpg.ui.menu.Menu;
import com.company.rpg.ui.menu.PlayerMenu;
import com.company.rpg.ui.menu.TopicMenu;
import com.company.rpg.utils.RandomUtil;

import java.time.LocalDateTime;

/**
 * Main game play controller all main logic (navigation, battle, etc)
 * starts from here
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class GamePlayController {

    private static final int MAP_SIZE = 10;

    private static final int NUMBER_OF_MONSTERS = 30;

    private Menu topicMenu;

    private Menu playerMenu;

    private GameContextRepository gameContextRepository;

    private BattleService battleService;

    private WorldMap worldMap;

    private boolean isGameOver;

    public GamePlayController(GameContextRepository gameContextRepository) {
        this.gameContextRepository = gameContextRepository;
        this.topicMenu = new TopicMenu();
        this.playerMenu = new PlayerMenu(gameContextRepository.getGameContext());
        this.battleService = new BattleServiceImpl();
    }

    /**
     * Start new game
     */
    public void startGame() {
        gameContextRepository.getGameContext().setTopic(selectTopic());
        gameContextRepository.getGameContext().setPlayer(createPlayer());
        initMap();
        continueGame();
    }

    /**
     * Continue saved game
     */
    public void continueGame() {
        while (!isGameOver) {
            Location currentLocation = gameContextRepository.getGameContext().getCurrentLocation();
            worldMap = gameContextRepository.getGameContext().getWorldMap();
            currentLocation.printLocationInfo();
            currentLocation.printLocationMenu();
            executeCommand(gameContextRepository.getGameContext().getCurrentLocation());
        }
        System.out.println("Congratulations! You've explored all locations.");
        CommonCommandsExecutor.exit();
    }

    /**
     * Display list of available topics for the game to the user.
     * Handle user input for selection
     *
     * @return selected game topic
     */
    private String selectTopic() {
        topicMenu.showMenu();
        int selectionIndex = topicMenu.getSelectionIndex();
        String topic = topicMenu.getCommandByIndex(selectionIndex);
        System.out.println("You've selected " + topic + " for the game");
        return topic;
    }

    /**
     * Creates player. Get the user's selected character
     * and generate player based on selection and random generated statistics
     *
     * @return create {@link Player}
     */
    private Player createPlayer() {
        playerMenu.showMenu();
        int selectionIndex = playerMenu.getSelectionIndex();
        String playerName = playerMenu.getCommandByIndex(selectionIndex);
        System.out.println("You've selected " + playerName + " for the game");
        Player player = new Player(playerName, 100, 0, RandomUtil.nextIntInRange(10, 20),
                RandomUtil.nextIntInRange(10, 20));
        System.out.println(player);
        System.out.println();
        return player;
    }

    /**
     * Prints to the console world's map
     */
    private void printMap() {
        GameContext gameContext = gameContextRepository.getGameContext();
        worldMap.printMap(gameContext.getCurrentLocation(), gameContext.getTopic());
    }

    /**
     * Generate the game world and map for it
     */
    private void initMap() {
        if (worldMap == null) {
            worldMap = new WorldMap(MAP_SIZE, NUMBER_OF_MONSTERS);
            worldMap.init();
            Location currentLocation = worldMap.getLocation(0, 0);
            gameContextRepository.getGameContext().setCurrentLocation(currentLocation);
            gameContextRepository.getGameContext().setWorldMap(worldMap);
        }
    }

    /**
     * Handle user selected command and execute appropriate command
     *
     * @param location - of the player.
     * @return current location
     */
    private Location executeCommand(Location location) {
        int selectionIndex = location.getLocationMenu().getSelectionIndex();
        String command = location.getLocationMenu().getCommandByIndex(selectionIndex).toUpperCase();
        CommonCommands cmd = CommonCommands.valueOf(command);
        if (CommonCommands.NORTH.equals(cmd)) {
            location = worldMap.move(location, Direction.NORTH);
            gameContextRepository.getGameContext().setCurrentLocation(location);
            gameContextRepository.getGameContext().setWorldMap(worldMap);
        } else if (CommonCommands.SOUTH.equals(cmd)) {
            location = worldMap.move(location, Direction.SOUTH);
            gameContextRepository.getGameContext().setCurrentLocation(location);
            gameContextRepository.getGameContext().setWorldMap(worldMap);
        } else if (CommonCommands.WEST.equals(cmd)) {
            location = worldMap.move(location, Direction.WEST);
            gameContextRepository.getGameContext().setCurrentLocation(location);
            gameContextRepository.getGameContext().setWorldMap(worldMap);
        } else if (CommonCommands.EAST.equals(cmd)) {
            location = worldMap.move(location, Direction.EAST);
            gameContextRepository.getGameContext().setCurrentLocation(location);
            gameContextRepository.getGameContext().setWorldMap(worldMap);
        } else if (CommonCommands.SAVE.equals(cmd)) {
            CommonCommandsExecutor.save(gameContextRepository.getGameContext(), LocalDateTime.now().toString(), "save");
        } else if (CommonCommands.EXIT.equals(cmd)) {
            CommonCommandsExecutor.exit();
        } else if (CommonCommands.MAP.equals(cmd)) {
            printMap();
        } else if (CommonCommands.FIGHT.equals(cmd)) {
            Location monsterLocation = gameContextRepository.getGameContext().getCurrentLocation();
            Player player = gameContextRepository.getGameContext().getPlayer();
            monsterLocation = battleService.battle(player, monsterLocation);
            gameContextRepository.getGameContext().setCurrentLocation(monsterLocation);
            worldMap.updateLocation(monsterLocation);
        } else if (CommonCommands.LOAD.equals(cmd)) {
            CommonCommandsExecutor.load("save");
        } else if (CommonCommands.STATISTICS.equals(cmd)) {
            System.out.println(gameContextRepository.getGameContext().getPlayer().toString() + "\n");
        }
        isGameFinished();
        return location;
    }

    /**
     * Check if game finished (all locations visited and all monsters defeated)
     * and switch of the flag
     */
    private void isGameFinished() {
        isGameOver = worldMap.isAllLocationsOpened();
    }
}
