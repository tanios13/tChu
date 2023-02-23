package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;

import java.util.List;

/**
 * Public state of a player.
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */

public class PublicPlayerState {
    private final int ticketCount;
    private final int cardCount;
    private final List<Route> routes;
    private final int carCount;
    private final int claimPoints;

    /**
     * Returns the public state of the player.
     * @param ticketCount the number of tickets the player has
     * @param cardCount the number of cards the player has
     * @param routes the roads the player got
     */
    public PublicPlayerState(int ticketCount, int cardCount, List<Route> routes){
        Preconditions.checkArgument(ticketCount>=0 & cardCount>=0);
        this.ticketCount = ticketCount;
        this.cardCount= cardCount;
        this.routes = List.copyOf(routes);
        int totalLength = 0;
        int tempClaimPoints = 0;
        for(Route route: routes){
            totalLength += route.length();
            tempClaimPoints += route.claimPoints();
        }
        claimPoints = tempClaimPoints;
        carCount = Constants.INITIAL_CAR_COUNT - totalLength;
    }

    /**
     * Returns the number of tickets.
     * @return the number of tickets
     */
    public int ticketCount(){return ticketCount;}

    /**
     * Returns the number of cards.
     * @return the number of cards
     */
    public int cardCount(){return cardCount;}

    /**
     * Return the roads.
     * @return the roads
     */
    public List<Route> routes(){return routes;}

    /**
     * Returns the number of cars the player has.
     * @return the number of cars the player has
     */
    public int carCount(){return carCount;}

    /**
     * Returns the number of construction points of the player.
     * @return the number of construction points of the player
     */
    public int claimPoints(){return claimPoints;}

}