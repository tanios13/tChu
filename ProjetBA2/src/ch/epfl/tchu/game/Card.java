package ch.epfl.tchu.game;

import java.util.List;

public enum Card {
    BLACK(Color.BLACK),
    VIOLET(Color.VIOLET),
    BLUE(Color.BLUE),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    ORANGE(Color.ORANGE),
    RED(Color.RED),
    WHITE(Color.WHITE),
    LOCOMOTIVE(null);

    private final Color color;

    private Card(Color color){
        this.color = color;
    }

    /**
     * Returns the color of the card's type
     * @return the color of the card's type
     */
    public Color color(){
        return color;
    }

    /**
     * List of the values of Card
     */
    public static List<Card> ALL = List.of(Card.values());

    /**
     * Contains the number of values of Card
     */
    public static int COUNT = ALL.size();

    /**
     * List that contains only the cards "cars"
     */
    public final static List<Card> CARS = ALL.subList(0,8);

    /**
     * Returns the type of the "car" card corresponding to the given color.
     * @param color
     *          the color of the card
     * @return the type of the card corresponding to the given color
     */
    public static Card of(Color color) {
        for (Card card : ALL) {
            if (card.color == color)
                return card;
        }
        throw new NullPointerException();
    }
}

