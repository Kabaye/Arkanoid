package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.Arcanoid;
import by.bsu.kulich.game.elements.controller.MenuController;
import by.bsu.kulich.game.elements.entity.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.StringJoiner;


public class View {

    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 800;

    public final static int REAL_LEFT_WINDOW_BOUND = 0;
    public final static int REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 15;
    public final static int REAL_TOP_WINDOW_BOUND = 1;
    public final static int REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 61;

    public final static String[] ACTION_COMMANDS = {"new", "difficulty", "level", "hot", "about"};

    private final String ABOUT_ICON_PATH = "src/by/bsu/kulich/game/resources/A.jpg";
    private final ImageIcon ABOUT_ICON = new ImageIcon(ABOUT_ICON_PATH);
    private final String INFO_ICON_PATH = "src/by/bsu/kulich/game/resources/info.png";
    private final ImageIcon INFO_ICON = new ImageIcon(INFO_ICON_PATH);

    private JMenuBar menuBar;
    private JMenu menu;
    @Getter
    private JMenuItem newGame;
    @Getter
    private JMenuItem changeDifficulty;
    @Getter
    private JMenuItem changeLevel;
    @Getter
    private JMenuItem hotKeys;
    @Getter
    private JMenuItem about;

    private MenuController menuController;

    @Getter
    private String text = "";

    @Getter
    private Arcanoid arcanoid;

    private GameFieldCanvas gameFieldCanvas;

    public View(Arcanoid arcanoid) {
        this.arcanoid = arcanoid;

        arcanoid.setLayout(new BorderLayout());
        JPanel canvasPanel = new JPanel(new BorderLayout());

        gameFieldCanvas = new GameFieldCanvas();
        arcanoid.setVisible(true);
        createMenu();

        canvasPanel.add(gameFieldCanvas);
        canvasPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        canvasPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        arcanoid.add(canvasPanel, BorderLayout.CENTER);
        gameFieldCanvas.initBufferStrategy();

        arcanoid.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        arcanoid.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        arcanoid.setResizable(false);
        arcanoid.setLocationRelativeTo(null);
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
            arcanoid.setGameDifficultyLevel(GameDifficultyLevel.valueOf(s));
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
            arcanoid.setGameLevel(GameLevel.valueOf(s));
        }
    }

    public void showAllHotKeysDialog() {
        final StringJoiner hotKeys = new StringJoiner("\n");
        hotKeys.add("\"ESC\" - выход из программы;")
                .add("\"← / →\" - движение платформы влево / вправо;")
                .add("\"SPACE\" - стартовать игру в начале или в случае смерти шарика;")
                .add("\"CTRL\" - поставить игру на паузу;")
                .add("\"R\" - повторить игру в случае поражения;")
                .add("\"S\" - повторить игру на этом же уровне со следующим уровнем сложности, если вы выиграли (если он доступен);")
                .add("\"N\" - следующий уровень на текущем уровне сложности, если вы выиграли;");
        JOptionPane.showMessageDialog(null, hotKeys.toString(), "Hot keys info", JOptionPane.INFORMATION_MESSAGE, INFO_ICON);
    }

    public void showAboutDialog() {
        final StringJoiner about = new StringJoiner("\n");
        about.add("Arcanoid® версия 2.3.5")
                .add("Copyright (C) 2018 KABAYE INC.")
                .add("ARCANOID® All rights reserved.");
        JOptionPane.showMessageDialog(null, about.toString(), "About", JOptionPane.INFORMATION_MESSAGE, ABOUT_ICON);
    }

    public void updateScore() {
        text = "Score: " + arcanoid.getScore() + "  Lives: " + arcanoid.getLives();
    }

    public void loosed() {
        text = "Вы проиграли! Чтобы переиграть уровень, нажмите \"R\"";
        gameFieldCanvas.drawLoosedScene(this.text);
    }

    public void won() {
        text = "Вы выиграли! Чтобы перейти на следующий уровень, нажмите \"N\".\n Если хотите переиграть этот уровень на более высоком уровне сложности, нажмите \"S\".";
        gameFieldCanvas.drawWonScene(arcanoid.getGameDifficultyLevel(), this.text);
    }

    public void drawScene(Ball ball, List<Block> blocks, Paddle paddle) {
        gameFieldCanvas.drawScene(ball, blocks, paddle, this);
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        menuController = new MenuController(arcanoid);

        menu = new JMenu("Меню");
        newGame = new JMenuItem("Начать новую игру");
        newGame.setActionCommand(ACTION_COMMANDS[0]);
        changeDifficulty = new JMenuItem("Выбрать сложность");
        changeDifficulty.setActionCommand(ACTION_COMMANDS[1]);
        changeLevel = new JMenuItem("Выбрать уровень");
        changeLevel.setActionCommand(ACTION_COMMANDS[2]);
        hotKeys = new JMenuItem("Список горячих клавиш");
        hotKeys.setActionCommand(ACTION_COMMANDS[3]);
        about = new JMenuItem("Об игре");
        about.setActionCommand(ACTION_COMMANDS[4]);

        newGame.addActionListener(menuController);
        changeDifficulty.addActionListener(menuController);
        changeLevel.addActionListener(menuController);
        hotKeys.addActionListener(menuController);
        about.addActionListener(menuController);

        menu.add(newGame);
        menu.add(changeDifficulty);
        menu.add(changeLevel);
        menu.add(hotKeys);
        menu.addSeparator();
        menu.add(about);

        menuBar.add(menu);

        arcanoid.setJMenuBar(menuBar);
    }

}
