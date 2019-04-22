package by.bsu.kulich.game.elements.view;

import by.bsu.kulich.game.Arcanoid;
import by.bsu.kulich.game.elements.entity.Ball;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.Paddle;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class View {

    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 800;

    public final static int REAL_LEFT_WINDOW_BOUND = 0;
    public final static int REAL_RIGHT_WINDOW_BOUND = WINDOW_WIDTH - 15;
    public final static int REAL_TOP_WINDOW_BOUND = 1;
    public final static int REAL_BOTTOM_WINDOW_BOUND = WINDOW_HEIGHT - 61;

    public final static String[] ACTION_COMMANDS = {"new", "change", "hot", "about"};

    private JMenuBar menuBar;
    private JMenu menu;
    @Getter
    private JMenuItem newGame;
    @Getter
    private JMenuItem changeDifficulty;
    @Getter
    private JMenuItem hotKeys;
    @Getter
    private JMenuItem about;

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

    public void updateScore() {
        text = "Score: " + arcanoid.getScore() + "  Lives: " + arcanoid.getLives();
    }

    public void loosed() {
        gameFieldCanvas.drawLoosedScene();
    }

    public void won() {
        gameFieldCanvas.drawWonScene(arcanoid.getGameDifficultyLevel());
    }

    public void drawScene(Ball ball, List<Block> blocks, Paddle paddle) {
        gameFieldCanvas.drawScene(ball, blocks, paddle, this);
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        menu = new JMenu("Меню");
        newGame = new JMenuItem("Начать новую игру");
        newGame.setActionCommand(ACTION_COMMANDS[0]);
        changeDifficulty = new JMenuItem("Выбрать сложность и уровень");
        changeDifficulty.setActionCommand(ACTION_COMMANDS[1]);
        hotKeys = new JMenuItem("Список горячих клавиш");
        hotKeys.setActionCommand(ACTION_COMMANDS[2]);
        about = new JMenuItem("Об игре");
        about.setActionCommand(ACTION_COMMANDS[3]);

        menu.add(newGame);
        menu.add(changeDifficulty);
        menu.add(hotKeys);
        menu.addSeparator();
        menu.add(about);

        menuBar.add(menu);

        arcanoid.setJMenuBar(menuBar);
    }

}
