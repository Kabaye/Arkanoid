package by.bsu.kulich.game.elements.creator;

import lombok.Getter;
import by.bsu.kulich.game.elements.entity.Block;
import by.bsu.kulich.game.elements.entity.GameLevel;

import java.util.List;

public class GameLevelCreator {

    @Getter
    private GameLevel gameLevel;

    @Getter
    private int blockAmount;

    public GameLevelCreator(GameLevel gameLevel) {
        setGameLevel(gameLevel);
    }

    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        switch (gameLevel) {
            case BEGINNING:
                blockAmount = 90;
                break;
            case MEDIUM:
                blockAmount = 63;
                break;
            case FINAL:
                blockAmount = 63;
                break;
        }
    }

    public void createNewMap(List<Block> blocks) {
        switch (gameLevel) {
            case BEGINNING:
                blocks.clear();

                for (int iX = 0; iX < 15; ++iX) {
                    for (int iY = 0; iY < 6; ++iY) {
                        blocks.add(new Block((iX + 1) * (Block.BLOCK_WIDTH) - 10,
                                (iY + 2) * (Block.BLOCK_HEIGHT) + 25, gameLevel));
                    }
                }
                break;

            case MEDIUM:
                blocks.clear();
                int counter = 0;
                for (int iY = 0; iY < 7; ++iY) {
                    for (int iX = counter; iX < 15 - counter; ++iX) {
                        blocks.add(new Block((iX + 1) * (Block.BLOCK_WIDTH) - 10,
                                (iY + 2) * (Block.BLOCK_HEIGHT) + 25, gameLevel));
                    }
                    counter += 1;
                }
                break;
            case FINAL:
                blocks.clear();
                counter = 0;
                for (int iY = 0; iY < 7; ++iY) {
                    for (int iX = 0; iX < 15; ++iX) {
                        if ((iX < 1 + counter) || (iX > 3 + counter && iX < 11 - counter) || (iX > 13 - counter))
                            blocks.add(new Block((iX + 1) * (Block.BLOCK_WIDTH) - 10,
                                    (iY + 2) * (Block.BLOCK_HEIGHT) + 25, gameLevel));
                    }
                    if (iY < 3)
                        counter++;
                    else
                        counter--;
                }
                break;
        }
    }
}

