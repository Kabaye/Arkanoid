package main.java.by.bsu.kulich.game.elements.entity;

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

    public static String[] getAllStringNames() {
        String[] strings = new String[5];
        strings[0] = GameDifficultyLevel.LIGHT.toString();
        strings[1] = GameDifficultyLevel.MEDIUM.toString();
        strings[2] = GameDifficultyLevel.HARD.toString();
        strings[3] = GameDifficultyLevel.VERY_HARD.toString();
        strings[4] = GameDifficultyLevel.YOU_ARE_GOD.toString();
        return strings;
    }
}
