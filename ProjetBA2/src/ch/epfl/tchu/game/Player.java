package ch.epfl.tchu.game;

import ch.epfl.tchu.SortedBag;

import java.util.List;
import java.util.Map;

/**
 * A player of tCHu
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */

public interface Player {

    /**
     * Represents the 3 actions a player of tCHu can do during his turn.
     */
    public enum TurnKind{
        DRAW_TICKETS,
        DRAW_CARDS,
        CLAIM_ROUTE;

        /**
         * List of the values of TurnKind
         */
        public static List<TurnKind> ALL = List.of(TurnKind.values());
    }

    /**
     * Communicates to the player his own identity and the name of the players(including his).
     * @param ownId the player's identity
     * @param playerNames the names of all the players
     */
    public abstract void initPlayers(PlayerId ownId, Map<PlayerId, String> playerNames);

    /**
     * Communicates information to the player during the game.
     * @param info the information
     */
    public abstract void receiveInfo(String info);

    /**
     * Called everytime the player's state changes to update the player about the new state and his own.
     * @param newState the new state of the game
     * @param ownState the player's state
     */
    public abstract void updateState(PublicGameState newState, PlayerState ownState);

    /**
     * Communicates to the player his five tickets.
     * @param tickets the tickets distributed to the player
     */
    public abstract void setInitialTicketChoice(SortedBag<Ticket> tickets);

    /**
     * Returns the tickets the player wants to keep at the beginning of the game.
     * @return the tickets the player wants to keep at the beginning of the game
     */
    public abstract SortedBag<Ticket> chooseInitialTickets();

    /**
     * Called at the beginning of the game to know which action the player wants to take.
     * @return the action the player wants to take
     */
    public abstract TurnKind nextTurn();

    /**
     * Returns the tickets the player wants to keep.
     * @param options the drawn tickets
     * @return the tickets the player chose
     */
    public abstract SortedBag<Ticket> chooseTickets(SortedBag<Ticket> options);

    /**
     * Returns a value depending from where the player wants to draw the cards.
     * @return a value depending from where the player wants to draw the cards
     */
    public abstract int drawSlot();

    /**
     * Returns the road the player wants to take.
     * @return the road the player wants to take
     */
    public abstract Route claimedRoute();

    /**
     * Returns the cards with which the player wants to take a road.
     * @return the cards with which the player wants to take a road
     */
    public abstract SortedBag<Card> initialClaimCards();

    /**
     * Return the cards used by the player to take a tunnel.
     * @param options the options of cards with which the player can take the tunnel
     * @return the cards used by the player to take a tunnel
     */
    public abstract SortedBag<Card> chooseAdditionalCards(List<SortedBag<Card>> options);
}