package main.java.by.bsu.kulich.game.elements.controller;

import main.java.by.bsu.kulich.game.Arcanoid;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.java.by.bsu.kulich.game.elements.view.View.ACTION_COMMANDS;

public class MenuController implements ActionListener {
    private Arcanoid arcanoid;

    public MenuController(Arcanoid arcanoid, JMenu menu) {
        this.arcanoid = arcanoid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_COMMANDS[0])) {
            arcanoid.restart();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[1])) {
            arcanoid.pause();
            arcanoid.getView().showChangeDifficultyDialog();
            arcanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[2])) {
            arcanoid.pause();
            arcanoid.getView().showChangeLevelDialog();
            arcanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[3])) {
            arcanoid.pause();
            arcanoid.getView().showAllHotKeysDialog();
            arcanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[4])) {
            arcanoid.pause();
            arcanoid.getView().showAboutDialog();
            arcanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[5])) {
            arcanoid.setRunning(false);
        }


    }
}