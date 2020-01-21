package by.bsu.kulich.game.elements.controller;

import lombok.Getter;
import lombok.Setter;
import by.bsu.kulich.game.Arkanoid;
import by.bsu.kulich.game.elements.entity.GameDifficultyLevel;
import by.bsu.kulich.game.elements.entity.GameLevel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private Arkanoid arkanoid;

    @Getter
    @Setter
    private boolean isStarted;

    @Getter
    @Setter
    private boolean nextLevel;

    public GameController(Arkanoid arkanoid) {
        this.arkanoid = arkanoid;
        isStarted = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                arkanoid.getPaddle().moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                arkanoid.getPaddle().moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                arkanoid.setRunning(!arkanoid.isRunning());
                break;
            case KeyEvent.VK_SPACE:
                if (!isStarted || arkanoid.getBall().isDied()) {
                    arkanoid.getBall().start();
                    isStarted = true;
                }
                break;
            case KeyEvent.VK_CONTROL:
                if (arkanoid.getBall().isPause()) {
                    arkanoid.getBall().continueGame();
                    arkanoid.getPaddle().continueGame();
                } else {
                    arkanoid.getBall().pause();
                    arkanoid.getPaddle().pause();
                }
                break;

            case KeyEvent.VK_R:
                if (arkanoid.isLoosed())
                    arkanoid.restart();
                break;

            case KeyEvent.VK_S: {
                if (arkanoid.isWon() && arkanoid.getGameDifficultyLevel() != GameDifficultyLevel.YOU_ARE_GOD) {
                    arkanoid.setGameDifficultyLevel(GameDifficultyLevel.nextGameDifficultyLevel(arkanoid.getGameDifficultyLevel()));
                    arkanoid.restart();
                }
                break;
            }
            case KeyEvent.VK_N:
                if (arkanoid.isWon() && arkanoid.getGameLevel() != GameLevel.FINAL) {
                    nextLevel = true;
                    arkanoid.setGameLevel(GameLevel.nextGameLevel(arkanoid.getGameLevel()));
                }
                break;

            case KeyEvent.VK_M:
                arkanoid.getView().getMenu().doClick(1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            arkanoid.getPaddle().stopMove();
        }
    }
}
