package com.company.rpg.ui.menu;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Provides text UI for selecting basic game theme.
 * List of available themes loaded from assets/themes.txt file
 *
 * @author dmitriy karmanov
 * @since 1.0
 */
public class TopicMenu extends AbstractMenu {

    private static final String ASSETS_TOPICS_LIST_TXT = "assets/topics_list.txt";

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        try {
            Stream<String> lines = parseFile(ASSETS_TOPICS_LIST_TXT);
            lines.forEach(s -> getCommands().add(s));
            System.out.println("Please select game topic:");
            printCommands();
            System.out.print("> ");
        } catch (IOException ex) {
            exitWithError("Could not load topics.txt file. Please verify that 'assets/topics.txt file exist");
        }
    }
}
