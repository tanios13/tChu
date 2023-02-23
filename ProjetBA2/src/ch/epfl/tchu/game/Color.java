package ch.epfl.tchu.game;

import java.util.List;

public enum Color {
    BLACK, VIOLET, BLUE, GREEN, YELLOW, ORANGE, RED, WHITE;
    /**
     * List of the values of Color
     */
    public static List<Color> ALL = List.of(Color.values());
    /**
     * Contains the number of values of Color
     */
    public static int COUNT = ALL.size();
}
