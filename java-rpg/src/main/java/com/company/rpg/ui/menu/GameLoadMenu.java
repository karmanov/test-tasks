package com.company.rpg.ui.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides logic to loading previously saved game
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class GameLoadMenu extends AbstractMenu {

    private String saveDirectory;

    public GameLoadMenu(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    @Override
    public void showMenu() {
        List<String> saveFiles = loadSaveFileNames();
        saveFiles.forEach(s -> getCommands().add(s));
        if (!getCommands().isEmpty()) {
            System.out.println("Please select saved game to load");
            printCommands();
            System.out.print("> ");
        } else {
            System.out.println("There is not saved games found");
        }
    }

    /**
     * Scans directory with save files and populate list with file paths
     *
     * @return list of file paths
     */
    private List<String> loadSaveFileNames() {
        List<String> saveFiles = new ArrayList<>();
        File dir = new File(saveDirectory);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                saveFiles.add(file.getAbsolutePath());
            }
        }
        return saveFiles;
    }
}
