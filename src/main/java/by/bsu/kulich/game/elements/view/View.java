package main.java.by.bsu.kulich.game.elements.view;

import lombok.Getter;
import main.java.by.bsu.kulich.game.Arkanoid;
import main.java.by.bsu.kulich.game.elements.controller.MenuController;
import main.java.by.bsu.kulich.game.elements.entity.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.StringJoiner;

import static main.java.by.bsu.kulich.game.elements.loader.ResourceLoader.getImage;
import static main.java.by.bsu.kulich.game.elements.loader.ResourceLoader.getMusicURL;


public class View {

    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 800;

    public final static int REAL_LEFT_WINDOW_BOUND = 0;
    public final static int REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 1;
    public final static int REAL_TOP_WINDOW_BOUND = 1;
    public final static int REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 24;

    public final static String[] ACTION_COMMANDS = {"new", "difficulty", "level", "hot", "about", "close"};

    private final String ABOUT_ICON_PATH = "images/A.jpg";
    private final ImageIcon ABOUT_ICON = new ImageIcon(getImage(ABOUT_ICON_PATH));
    private final String INFO_ICON_PATH = "images/info.png";
    private final ImageIcon INFO_ICON = new ImageIcon(getImage(INFO_ICON_PATH));

    private final String MUSIC_PATH = "music/1.wav";

    private JMenuBar menuBar;
    @Getter
    private JMenu menu;

    private JMenuItem newGame;
    private JMenuItem changeDifficulty;
    private JMenuItem changeLevel;
    private JMenuItem hotKeys;
    private JMenuItem about;
    private JMenuItem close;

    private MenuController menuController;

    @Getter
    private String text = "";

    @Getter
    private Arkanoid arkanoid;

    private GameFieldCanvas gameFieldCanvas;

    public View(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;

        arkanoid.setLayout(new BorderLayout());
        JPanel canvasPanel = new JPanel(new BorderLayout());

        gameFieldCanvas = new GameFieldCanvas();
        arkanoid.setVisible(true);
        createMenu();

        canvasPanel.add(gameFieldCanvas);
        canvasPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        canvasPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        arkanoid.add(canvasPanel, BorderLayout.CENTER);
        gameFieldCanvas.initBufferStrategy();

        arkanoid.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        arkanoid.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        arkanoid.setResizable(false);
        arkanoid.setLocationRelativeTo(null);
    }

    public void showChangeDifficultyDialog() {
        Object[] possibilities = GameDifficultyLevel.getAllStringNames();
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Choose your difficulty:",
                "Choosing new difficulty will restart game!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                possibilities[0]);

        if ((s != null) && (s.length() > 0)) {
            arkanoid.setGameDifficultyLevel(GameDifficultyLevel.valueOf(s));
        }
    }

    public void showChangeLevelDialog() {
        Object[] possibilities = GameLevel.getAllStringNames();
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Choose your level:",
                "Choosing new level will restart game!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                possibilities[0]);

        if ((s != null) && (s.length() > 0)) {
            arkanoid.setGameLevel(GameLevel.valueOf(s));
        }
    }

    public void showAllHotKeysDialog() {
        drawMainImage();

        final StringJoiner hotKeys = new StringJoiner("\n");
        hotKeys.add("\"ESC\" - выход из программы;")
                .add("\"← / →\" - движение платформы влево / вправо;")
                .add("\"SPACE\" - стартовать игру в начале или в случае смерти шарика;")
                .add("\"CTRL\" - поставить игру на паузу;")
                .add("\"R\" - повторить игру в случае поражения;")
                .add("\"S\" - повторить игру на этом же уровне со следующим уровнем сложности, если вы выиграли (если он доступен);")
                .add("\"N\" - следующий уровень на текущем уровне сложности, если вы выиграли;")
                .add("\"М\" - открывает меню (паузу нужно нажать вручную!);");
        JOptionPane.showMessageDialog(null, hotKeys.toString(), "Hot keys info", JOptionPane.INFORMATION_MESSAGE, INFO_ICON);
    }

    public void showAboutDialog() {
        final StringJoiner about = new StringJoiner("\n");
        about.add("Arkanoid® версия 2.3.7")
                .add("Copyright (C) 2018 KABAYE INC.")
                .add("ARKANOID® All rights reserved.");
        JOptionPane.showMessageDialog(null, about.toString(), "About", JOptionPane.INFORMATION_MESSAGE, ABOUT_ICON);
    }

    public void updateScore() {
        text = "Score: " + arkanoid.getScore() + "  Lives: " + arkanoid.getLives();
    }

    public void loosed() {
        text = "Вы проиграли! Чтобы переиграть уровень, нажмите \"R\"";
        gameFieldCanvas.drawLoosedScene(this.text);
    }

    public void won() {
        text = "Вы выиграли! Чтобы перейти на следующий уровень, нажмите \"N\".\n Если хотите переиграть этот уровень на более высоком уровне сложности, нажмите \"S\".";
        gameFieldCanvas.drawWonScene(arkanoid.getGameDifficultyLevel(), arkanoid.getGameLevel(), this.text);
    }

    public void drawScene(Ball ball, List<Block> blocks, Paddle paddle) {
        gameFieldCanvas.drawScene(ball, blocks, paddle, this);
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        menu = new JMenu("Меню");
        newGame = new JMenuItem("Начать новую игру");
        newGame.setActionCommand(ACTION_COMMANDS[0]);
        changeDifficulty = new JMenuItem("Выбрать сложность...");
        changeDifficulty.setActionCommand(ACTION_COMMANDS[1]);
        changeLevel = new JMenuItem("Выбрать уровень...");
        changeLevel.setActionCommand(ACTION_COMMANDS[2]);
        hotKeys = new JMenuItem("Список горячих клавиш...");
        hotKeys.setActionCommand(ACTION_COMMANDS[3]);
        about = new JMenuItem("Об игре...");
        about.setActionCommand(ACTION_COMMANDS[4]);
        close = new JMenuItem("Закрыть игру");
        close.setActionCommand(ACTION_COMMANDS[5]);

        menuController = new MenuController(arkanoid, menu);

        newGame.addActionListener(menuController);
        changeDifficulty.addActionListener(menuController);
        changeLevel.addActionListener(menuController);
        hotKeys.addActionListener(menuController);
        about.addActionListener(menuController);
        close.addActionListener(menuController);

        menu.add(newGame);
        menu.add(changeDifficulty);
        menu.add(changeLevel);
        menu.add(hotKeys);
        menu.addSeparator();
        menu.add(about);
        menu.addSeparator();
        menu.add(close);

        menuBar.add(menu);

        arkanoid.setJMenuBar(menuBar);
    }

    public void playMusic() {
        new Music();
    }

    public void drawMainImage() {
        gameFieldCanvas.drawMainImage();
    }

    private class Music {
        private Music() {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(getMusicURL(MUSIC_PATH));
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception exc) {
                //JOptionPane.showMessageDialog(null, "FILE NOT FOUND\n" + MUSIC_PATH);
            }

        }
    }


}
