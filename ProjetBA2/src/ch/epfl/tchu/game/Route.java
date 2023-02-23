package ch.epfl.tchu.game;

import ch.epfl.tchu.SortedBag;

import java.util.ArrayList;
import java.util.List;

import static ch.epfl.tchu.game.Constants.*;

/**
 * @author Tamara Antoun(324875)
 * @author Ronan Tanios(325510)
 */
public final class Route {
    public enum Level {
        OVERGROUND,
        UNDERGROUND,
    }

    private Station station1;
    private Station station2;
    private String id;
    private int length;
    private Color color;
    private Level level;

    /**
     * construct a route with the given identity, stations, length, level and color
     *
     * @param id       identity of the route
     * @param station1 station 1 of the route
     * @param station2 station 2 of the route
     * @param length   length of the route
     * @param level    level of the route
     * @param color    color of the route
     * @throws IllegalArgumentException if the two stations are equal
     *                                  or if the length is not within the acceptable limits
     * @throws NullPointerException     if the identity, one of the two stations or the level are null
     */
    public Route(String id, Station station1, Station station2, int length, Level level, Color color) {
        if(station1.equals(station2) || length < MIN_ROUTE_LENGTH || length > MAX_ROUTE_LENGTH)
            throw new IllegalArgumentException();

        if(id == null || station1 == null || station2 == null || level == null)
            throw new NullPointerException();

        this.station1 = station1;
        this.station2 = station2;
        this.id = id;
        this.length = length;
        this.level = level;
        this.color = color;
    }

    /**
     * returns the identity of the route
     *
     * @return the identity of the route
     */
    public String id() {
        return id;
    }

    /**
     * returns the first station of the route
     *
     * @return the first station of the route
     */
    public Station station1() {
        return station1;
    }

    /**
     * returns the second station of the route
     *
     * @return the second station of the route
     */
    public Station station2() {
        return station2;
    }

    /**
     * returns the length of the route
     *
     * @return the length of the route
     */
    public int length() {
        return length;
    }

    /**
     * returns the level at which the road is
     *
     * @return the level at which the road is
     */
    public Level level() {
        return level;
    }

    /**
     * returns the color of the road
     *
     * @return the color of the road
     */
    public Color color() {
        if(color == null) {
            return null;
        }
        return color;
    }

    /**
     * returns the list of the two stations of the route
     *
     * @return the list of the two stations of the route
     */
    public List<Station> stations() {
        return List.of(station1, station2);
    }

    /**
     * returns the station of the route that is not the one given
     *
     * @param station one of the station of the route
     * @return the station of the route that is not the one given
     * @throws IllegalArgumentException if the given station is neither the first
     *                                  nor the second station of the route
     */
    public Station stationOpposite(Station station) {
        if(station.equals(station1)) {
            return station2;
        } else if(station.equals(station2)) {
            return station1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * returns the list of all the sets of cards that could be played to take over the road,
     * sorted in ascending order of number of locomotive cards, then by color
     *
     * @return the list of all the sets of cards that could be played to take over the road
     */
    public List<SortedBag<Card>> possibleClaimCards() {
        if(level == Level.OVERGROUND)
            return (color == null) ? computeCombinationOverground(Card.CARS) : computeCombinationOverground(List.of(Card.of(color)));
        else
            return (color == null) ? computeCombinationUnderground(Card.CARS) : computeCombinationUnderground(List.of(Card.of(color)));
    }

    /**
     * returns the list of all sets of cards that could be played to (attempt to) grab the UNDERGROUND
     *
     * @param listOfCard list of cards depending on the color of the UNDERGROUND
     * @return the list of all sets of cards that could be played to (attempt to) grab the UNDERGROUND
     */
    private ArrayList<SortedBag<Card>> computeCombinationUnderground(List<Card> listOfCard) {
        ArrayList<SortedBag<Card>> listOfAllTheCards = new ArrayList<SortedBag<Card>>();
        for (int locomotiveCount = 0; locomotiveCount < length; locomotiveCount++) {
            for (Card card : listOfCard) {
                listOfAllTheCards.add(SortedBag.of(length - locomotiveCount, card).union(SortedBag.of(locomotiveCount, Card.LOCOMOTIVE)));
            }
        }
        listOfAllTheCards.add(SortedBag.of(length, Card.LOCOMOTIVE));
        return listOfAllTheCards;
    }

    /**
     * returns the list of all sets of cards that could be played to (attempt to) grab the OVERGROUND
     *
     * @param listOfCard list of cards depending on the color of the OVERGROUND
     * @return the list of all sets of cards that could be played to (attempt to) grab the OVERGROUND
     */
    private ArrayList<SortedBag<Card>> computeCombinationOverground(List<Card> listOfCard) {
        ArrayList<SortedBag<Card>> listOfAllTheCards = new ArrayList<SortedBag<Card>>();
        for (Card card : listOfCard) {
            listOfAllTheCards.add(SortedBag.of(length, card));
        }
        return listOfAllTheCards;
    }

    /**
     * returns the number of additional cards to be played to seize the road (in tunnel)
     *
     * @param claimCards the cards initially played
     * @param drawnCards the three cards drawn from the top of the deck
     * @return the number of additional cards to be played to seize the road (in tunnel)
     * @throws IllegalArgumentException if the road to which it is applied is not a tunnel
     *                                  or if the drawnCards does not contain exactly 3 cards
     */
    public int additionalClaimCardsCount(SortedBag<Card> claimCards, SortedBag<Card> drawnCards) {
        if(level.equals(Level.OVERGROUND) || drawnCards.size() != ADDITIONAL_TUNNEL_CARDS)
            throw new IllegalArgumentException();

        Color colorOfPlayedCards = null;
        for (Card card : claimCards) {
            if(card.color() != null) {
                colorOfPlayedCards = card.color();
            }
        }
        return (colorOfPlayedCards == null) ? drawnCards.countOf(Card.LOCOMOTIVE) :
                drawnCards.countOf(Card.LOCOMOTIVE) + drawnCards.countOf(Card.of(colorOfPlayedCards));
    }

    /**
     * returns the number of construction points a player gets when they grab the road
     *
     * @return the number of construction points a player gets when they grab the road
     */
    public int claimPoints() {
        return ROUTE_CLAIM_POINTS.get(length);
    }
}
