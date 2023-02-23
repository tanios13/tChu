package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Public state of the game
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */
public class PublicGameState {

    private final int ticketsCount;
    private final PublicCardState cardState;
    private final PlayerId currentPlayerId;
    private final Map<PlayerId, PublicPlayerState> playerState;
    private final PlayerId lastPlayer;

    /**
     * build the public part of the state of a game of tCHu
     *
     * @param ticketsCount    size of the bank of tickets
     * @param cardState       the public state of the wagon / locomotive cards
     * @param currentPlayerId Id of the current player
     * @param playerState     the public state of the players
     * @param lastPlayer      last player's identity
     * @throws NullPointerException     if cardState, currentPlayerId or playreState is null
     * @throws IllegalArgumentException if the draw pile size is strictly negative
     *                                  or if playerState does not contain exactly two key / value pairs
     */
    public PublicGameState(int ticketsCount, PublicCardState cardState, PlayerId currentPlayerId, Map<PlayerId, PublicPlayerState> playerState, PlayerId lastPlayer) {
        if(cardState == null || currentPlayerId == null || playerState == null)
            throw new NullPointerException();
        Preconditions.checkArgument(ticketsCount >= 0 && playerState.size() == 2);

        this.ticketsCount = ticketsCount;
        this.cardState = cardState;
        this.currentPlayerId = currentPlayerId;
        this.playerState = Map.copyOf(playerState);
        this.lastPlayer = lastPlayer;
    }

    /**
     * returns the size of the bank of tickets
     *
     * @return the size of the bank of tickets
     */
    public int ticketsCount() {
        return ticketsCount;
    }

    /**
     * returns true if it is possible to draw tickets, i.e. if the draw pile is not empty
     *
     * @return true if it is possible to draw tickets, i.e. if the draw pile is not empty
     */
    public boolean canDrawTickets() {
        return ticketsCount > 0;
    }

    /**
     * returns the public the state of the wagon / locomotive cards
     *
     * @return the public the state of the wagon / locomotive cards
     */
    public PublicCardState cardState() {
        return cardState;
    }

    /**
     * returns true if it is possible to draw cards, i.e. if the draw pile and the discard pile contain at least 5 cards between them
     *
     * @return true if it is possible to draw cards, i.e. if the draw pile and the discard pile contain at least 5 cards between them
     */
    public boolean canDrawCards() {
        return (cardState.deckSize() + cardState.discardsSize()) >= 5;
    }

    /**
     * returns the identity of the current player
     *
     * @return the identity of the current player
     */
    public PlayerId currentPlayerId() {
        return currentPlayerId;
    }

    /**
     * returns the public part of the player's state of given identity
     *
     * @param playerId identity of the player
     * @return the public part of the player's state of given identity
     */
    public PublicPlayerState playerState(PlayerId playerId) {
        return playerState.get(playerId);
    }

    /**
     * returns the public part of the current player's state
     *
     * @return the public part of the current player's state
     */
    public PublicPlayerState currentPlayerState() {
        return playerState.get(currentPlayerId);
    }

    /**
     * returns the totality of the routes which one or the other of the players seized
     *
     * @return the totality of the routes which one or the other of the players seized
     */
    public List<Route> claimedRoutes() {
        List<Route> claimedRoutes = new LinkedList<>();
        playerState.forEach((playerId, publicPlayerState) -> claimedRoutes.addAll(publicPlayerState.routes()));
        return  claimedRoutes;
    }

    /**
     * returns the identity of the last player, or null if it is not yet known because the last round has not started
     *
     * @return the identity of the last player, or null if it is not yet known because the last round has not started
     */
    public PlayerId lastPlayer() {
        return lastPlayer;
    }

}
