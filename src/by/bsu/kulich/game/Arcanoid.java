package by.bsu.kulich.game;

import by.bsu.kulich.game.elements.Block;
import by.bsu.kulich.game.elements.GameDifficultyLevel;
import by.bsu.kulich.game.elements.GameLevel;
import by.bsu.kulich.game.elements.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import static by.bsu.kulich.game.elements.Block.BLOCK_HEIGHT;
import static by.bsu.kulich.game.elements.Block.BLOCK_WIDTH;

public class Arcanoid extends JFrame {
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 700;

    private Paddle paddle = new Paddle(WINDOW_WIDTH / 2.0, WINDOW_HEIGHT - 25.0, WINDOW_WIDTH, GameDifficultyLevel.LIGHT);
    private List<Block> blocks = new ArrayList<>();

    private Arcanoid() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.createBufferStrategy(2);

        initializeBricks();
    }

    public static void main(String[] args) {
        new Arcanoid().run();
    }

    private void initializeBricks() {
        // deallocate old bricks
        blocks.clear();

        for (int iX = 0; iX < 10; ++iX) {
            for (int iY = 0; iY < 5; ++iY) {
                blocks.add(new Block((iX + 1) * (BLOCK_WIDTH + 3) + 22,
                        (iY + 2) * (BLOCK_HEIGHT + 3) + 20, GameLevel.BEGINNING));
            }
        }
    }

    private void drawScene(/*Ball ball,*/ List<Block> blocks/*, ScoreBoard scoreboard*/) {
        // Code for the drawing goes here.
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {

            g = bf.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());

            //ball.draw(g);
            paddle.draw(g);
            for (Block block : blocks) {
                block.draw(g);
            }
            //scoreboard.draw(g);

        } finally {
            g.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();

    }

    private void run() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = bf.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
        bf.show();

        drawScene(blocks);
    }
}
