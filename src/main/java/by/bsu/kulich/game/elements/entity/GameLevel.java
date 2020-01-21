package by.bsu.kulich.game.elements.entity;

public enum GameLevel {
    BEGINNING,
    MEDIUM,
    FINAL;

    public static GameLevel nextGameLevel(GameLevel i) {
        switch (i) {
            case BEGINNING:
                return MEDIUM;
            case MEDIUM:
                return FINAL;
            case FINAL:
                return FINAL;
        }
        return null;
    }

    public static String[] getAllStringNames() {
        String[] strings = new String[3];
        strings[0] = GameLevel.BEGINNING.toString();
        strings[1] = GameLevel.MEDIUM.toString();
        strings[2] = GameLevel.FINAL.toString();

        return strings;
    }
}
