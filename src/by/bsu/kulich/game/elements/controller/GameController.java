package by.bsu.kulich.game.elements.controller;

import by.bsu.kulich.game.Arcanoid;
import by.bsu.kulich.game.elements.entity.GameDifficultyLevel;
import by.bsu.kulich.game.elements.entity.GameLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private Arcanoid arcanoid;

    @Getter
    @Setter
    private boolean isStarted;

    public GameController(Arcanoid arcanoid) {
        this.arcanoid = arcanoid;
        isStarted = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                arcanoid.getPaddle().moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                arcanoid.getPaddle().moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                arcanoid.setRunning(!arcanoid.isRunning());
                break;
            case KeyEvent.VK_SPACE:
                if (!isStarted || arcanoid.getBall().isDied()) {
                    arcanoid.getBall().start();
                    isStarted = true;
                }
                break;
            case KeyEvent.VK_CONTROL:
                if (arcanoid.getBall().isPause()) {
                    arcanoid.getBall().continueGame();
                    arcanoid.getPaddle().continueGame();
                } else {
                    arcanoid.getBall().pause();
                    arcanoid.getPaddle().pause();
                }
                break;

            case KeyEvent.VK_R:
                if (arcanoid.isLoosed())
                    arcanoid.restart();
                break;

            case KeyEvent.VK_S: {
                if (arcanoid.isWon() && arcanoid.getGameDifficultyLevel() != GameDifficultyLevel.YOU_ARE_GOD) {
                    arcanoid.setGameDifficultyLevel(GameDifficultyLevel.nextGameDifficultyLevel(arcanoid.getGameDifficultyLevel()));
                    arcanoid.restart();
                }
                break;
            }
            case KeyEvent.VK_N:
                if (arcanoid.isWon() && arcanoid.getGameLevel() != GameLevel.FINAL) {
                    arcanoid.setGameLevel(GameLevel.nextGameLevel(arcanoid.getGameLevel()));
                }
                break;

            case KeyEvent.VK_M:
                arcanoid.getView().getMenu().doClick(1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            arcanoid.getPaddle().stopMove();
        }
    }
}
