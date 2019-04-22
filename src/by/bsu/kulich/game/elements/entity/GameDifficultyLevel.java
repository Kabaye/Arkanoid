package by.bsu.kulich.game.elements.entity;

public enum GameDifficultyLevel {
    LIGHT,
    MEDIUM,
    HARD,
    VERY_HARD,
    YOU_ARE_GOD;

    public static GameDifficultyLevel nextGameDifficultyLevel(GameDifficultyLevel i) {
        switch (i) {
            case LIGHT:
                return MEDIUM;
            case MEDIUM:
                return HARD;
            case HARD:
                return VERY_HARD;
            case VERY_HARD:
                return YOU_ARE_GOD;
            case YOU_ARE_GOD:
                return YOU_ARE_GOD;
        }
        return null;
    }
}
