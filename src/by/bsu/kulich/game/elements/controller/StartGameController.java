package by.bsu.kulich.game.elements.controller;

import by.bsu.kulich.game.elements.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGameController implements ActionListener {
    private JButton button;
    private View view;

    public StartGameController(JButton button, View view) {
        this.button = button;
        this.view = view;
        this.button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // view.closeMenu();
    }
}
