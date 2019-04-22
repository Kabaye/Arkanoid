package by.bsu.kulich.game.elements.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Block extends Rectangle {

    public static final double BLOCK_WIDTH = 60.0;
    public static final double BLOCK_HEIGHT = 20.0;

    @Setter
    @Getter
    private boolean destroyed = false;
    private GameLevel gameLevel;

    @Getter
    private Color color;

    public Block(double x, double y, @NonNull GameLevel gameLevel) {
        super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.gameLevel = gameLevel;
        switch (this.gameLevel) {
            case BEGINNING:
                color = Color.YELLOW;
                break;
            case MEDIUM:
                color = Color.CYAN;
                break;
            case FINAL:
                color = Color.MAGENTA;
                break;
        }
    }
}
