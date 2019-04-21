package by.bsu.kulich.game.keyboard;

import by.bsu.kulich.game.Arcanoid;
import by.bsu.kulich.game.elements.Paddle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaddleController implements KeyListener {
    private Paddle paddle;
    private Arcanoid arcanoid;

    public PaddleController(Paddle paddle, Arcanoid arcanoid) {
        this.paddle = paddle;
        this.arcanoid = arcanoid;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                paddle.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                paddle.moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                arcanoid.setRunning(!arcanoid.isRunning());
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.stopMove();
        }
    }
}
