package ch.epfl.tchu.game;
/**
 * State of a player.
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

import java.util.*;

public final class PlayerState extends PublicPlayerState {
    private final SortedBag<Ticket> tickets;
    private final SortedBag<Card> cards;

    /**
     * Returns the complete state of a player.
     *
     * @param tickets the tickets the player has
     * @param cards   the cards the player has
     * @param routes  the roads the player has
     */
    public PlayerState(SortedBag<Ticket> tickets, SortedBag<Card> cards, List<Route> routes) {
        super(tickets.size(), cards.size(), routes);
        this.tickets = tickets;
        this.cards = cards;
    }

    /**
     * Returns the initial state of the player.
     *
     * @param initialCards the initial cards
     * @return the initial state of the player
     * @throws IllegalArgumentException if the number of the initial cards is different than 4
     */
    public static PlayerState initial(SortedBag<Card> initialCards) {
        Preconditions.checkArgument(initialCards.size() == 4);
        return new PlayerState(SortedBag.of(), initialCards, List.of());
    }

    /**
     * Returns the tickets the player has.
     *
     * @return the tickets the player has
     */
    public SortedBag<Ticket> tickets() {
        return tickets;
    }

    /**
     * Returns the same state of the player but with the added tickets.
     *
     * @param newTickets the new tickets
     * @return the same state of the player but with the added tickets
     */
    public PlayerState withAddedTickets(SortedBag<Ticket> newTickets) {
        return new PlayerState(tickets.union(newTickets), cards, routes());
    }

    /**
     * Returns the cards the player has.
     *
     * @return the cards the player has
     */
    public SortedBag<Card> cards() {
        return cards;
    }

    /**
     * returns an identical PlayerState to the receiver with the given card added
     *
     * @param card the card we want to add
     * @return an identical PlayerState to the receiver with the given card added
     */
    public PlayerState withAddedCard(Card card) {
        return new PlayerState(tickets, cards.union(SortedBag.of(card)), routes());
    }

    public PlayerState withAddedCards(SortedBag<Card> additionalCards) {
        return new PlayerState(tickets, cards.union(additionalCards), routes());
    }

    /**
     * Returns true if and only if the player can claim the road.
     *
     * @param route the road the player wants to claim
     * @return true if and only if the player can claim the road
     */
    public boolean canClaimRoute(Route route) {
        return (carCount() >= route.length() && !possibleClaimCards(route).isEmpty());
    }

    /**
     * Returns the list of all the possible cards collections for the player to possess the given road.
     *
     * @param route the road the player wants to posses
     * @return the list of all the possible cards collections for the player to possess the given road
     */
    public List<SortedBag<Card>> possibleClaimCards(Route route) {
        Preconditions.checkArgument(carCount() >= route.length());
        List<SortedBag<Card>> possibleCards = route.possibleClaimCards();
        List<SortedBag<Card>> possibleClaimCards = new LinkedList<>(possibleCards);
        for (SortedBag<Card> card : possibleCards) {
            if(!cards.contains(card))
                possibleClaimCards.remove(card);
        }
        return possibleClaimCards;
    }

    /**
     * Returns the list of all the sets of cards the player could use to take a tunnel.
     *
     * @param additionalCardsCount the number of additional cards
     * @param initialCards         the initial cards of the player
     * @param drawnCards           the drawn cards.
     * @return the list of all the sets of cards the player could use to take a tunnel
     * @throws IllegalArgumentException if the number of additional cards is not between 1 and 3 (included),
     *                                  if the set of initial cards is empty or contains more than 2 different types of cards,
     *                                  or if the set of cards drawn does not contain exactly 3 cards
     */
    public List<SortedBag<Card>> possibleAdditionalCards(int additionalCardsCount, SortedBag<Card> initialCards, SortedBag<Card> drawnCards) {
        Preconditions.checkArgument(additionalCardsCount >= 1 & additionalCardsCount <= 3 & !initialCards.isEmpty() & initialCards.toSet().size() <= 2 & drawnCards.size() == 3);

        List<Card> neededCards = new LinkedList<>(cards.toList());
        Color colorOfPlayedCards = null;
        for (Card unusedCard : initialCards) {
            neededCards.remove(unusedCard);
            if(unusedCard.color() != null) {
                colorOfPlayedCards = unusedCard.color();
            }
        }

        List<Card> finalCards = new LinkedList<>();
        for (Card addedCard : neededCards) {
            if(addedCard.equals(Card.LOCOMOTIVE) || addedCard.color().equals(colorOfPlayedCards))
                finalCards.add(addedCard);
        }

        if(finalCards.isEmpty() || finalCards.size() < additionalCardsCount)
            return List.of();

        List<SortedBag<Card>> myList = new ArrayList<>(SortedBag.of(finalCards).subsetsOfSize(additionalCardsCount));
        myList.sort(Comparator.comparingInt(cs -> cs.countOf(Card.LOCOMOTIVE)));
        return myList;
    }

    /**
     * Returns the state of the player with the road added and the claimCards used for the road removed.
     *
     * @param route      the new road the player possesses
     * @param claimCards the cards used to take over the road
     * @return the state of the player with the road added and the claimCards used for the road removed
     */
    public PlayerState withClaimedRoute(Route route, SortedBag<Card> claimCards) {
        List<Route> routes = new LinkedList<>(routes());
        routes.add(route);
        return new PlayerState(tickets, cards.difference(claimCards), routes);
    }

    /**
     * Returns the number of points obtained with the tickets.
     *
     * @return the number of points obtained with the tickets
     */
    public int ticketPoints() {
        int maxId = 0;
        int ticketPoints = 0;
        for (Route route : routes()) {
            int maxStationsId = Math.max(route.station1().id(), route.station2().id());
            if(maxStationsId > maxId)
                maxId = maxStationsId;
        }

        StationPartition.Builder partition = new StationPartition.Builder(maxId + 1);

        for (Route route : routes()) {
            partition.connect(route.station1(), route.station2());
        }
        StationPartition newPartition = partition.build();
        for (Ticket ticket : tickets) {
            ticketPoints += ticket.points(newPartition);
        }
        return ticketPoints;
    }

    /**
     * Returns the total number of points.
     *
     * @return the total number of points
     */
    public int finalPoints() {
        return claimPoints() + ticketPoints();
    }

}