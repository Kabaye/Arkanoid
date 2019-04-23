package main.java.by.bsu.kulich.game.elements.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Block extends Rectangle {

    public static final double BLOCK_WIDTH = 80.0;
    public static final double BLOCK_HEIGHT = 30.0;

    private final Color MEDIUM_COLOR = new Color(162, 21, 193);
    private final Color BEGINNING_COLOR = new Color(0, 255, 50);
    private final Color FINAL_COLOR = new Color(239, 110, 50);

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
                color = BEGINNING_COLOR;
                break;
            case MEDIUM:
                color = MEDIUM_COLOR;
                break;
            case FINAL:
                color = FINAL_COLOR;
                break;
        }
    }
}
