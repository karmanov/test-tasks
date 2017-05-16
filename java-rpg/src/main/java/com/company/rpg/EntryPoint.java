package com.company.rpg;

import com.company.rpg.game.GameStartupController;

/**
 * Main entry point of the application
 *
 * @author Dmitriy Karmanov
 * @since 1.0
 */
public class EntryPoint {

    public static void main(String[] args) {
        new GameStartupController().startupGame();
    }
}
