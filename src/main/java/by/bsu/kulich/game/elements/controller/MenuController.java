package by.bsu.kulich.game.elements.controller;

import by.bsu.kulich.game.Arkanoid;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static by.bsu.kulich.game.elements.view.View.ACTION_COMMANDS;

public class MenuController implements ActionListener {
    private Arkanoid arkanoid;

    public MenuController(Arkanoid arkanoid, JMenu menu) {
        this.arkanoid = arkanoid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_COMMANDS[0])) {
            arkanoid.restart();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[1])) {
            arkanoid.pause();
            arkanoid.getView().showChangeDifficultyDialog();
            arkanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[2])) {
            arkanoid.pause();
            arkanoid.getView().showChangeLevelDialog();
            arkanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[3])) {
            arkanoid.pause();
            arkanoid.getView().showAllHotKeysDialog();
            arkanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[4])) {
            arkanoid.pause();
            arkanoid.getView().showAboutDialog();
            arkanoid.continueGame();
        } else if (e.getActionCommand().equals(ACTION_COMMANDS[5])) {
            arkanoid.setRunning(false);
        }


    }
}