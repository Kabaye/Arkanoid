package by.bsu.kulich.game.elements.controller;

import by.bsu.kulich.game.elements.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static by.bsu.kulich.game.elements.view.View.ACTION_COMMANDS;

public class MenuController implements ActionListener {
    private View view;

    public MenuController(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == ACTION_COMMANDS[0]) {

        }
    }
}
