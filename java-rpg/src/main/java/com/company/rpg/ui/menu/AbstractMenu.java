package com.company.rpg.ui.menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Basic  abstract {@link Menu} interface implementation
 * contains common logic for all other implementations
 * Should be extended by each {@link Menu} implementation
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public abstract class AbstractMenu implements Menu {

    private Scanner scanner = new Scanner(System.in);

    private List<String> commands = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectionIndex() {
        int result;
        while (true) {
            result = scanner.nextInt();
            if (result < 0 || result > commands.size()) {
                System.out.println("Sorry, but I don't understand the command. Please try again.");
                System.out.println(">");
            } else {
                System.out.println("");
                return result;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommandByIndex(int index) {
        return commands.get(index - 1);
    }

    /**
     * Parse text file by given absolute path and return {@link Stream} of it's content
     *
     * @param filePath - absolute file path
     * @return {@link Stream} of the file lines
     * @throws IOException if file parsing fails
     */
    protected Stream<String> parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.lines(path);
    }

    /**
     * Prints formatted list of commands to the console
     */
    protected void printCommands() {
        for (int i = 0; i < commands.size(); i++) {
            int printIndex = i + 1;
            System.out.println("    " + printIndex + ". " + commands.get(i));
        }
    }

    /**
     * Display given errors to the player and exit the game
     *
     * @param message - error message to display
     */
    protected void exitWithError(String message) {
        System.err.print(message);
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCommands() {
        return commands;
    }

}
