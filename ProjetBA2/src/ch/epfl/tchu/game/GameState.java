package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * The state of a game of tCHu
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */

public final class GameState extends PublicGameState {

    private final Deck<Ticket> tickets;
    private final Map<PlayerId, PlayerState> playerState;
    private final CardState cardState;

    private GameState(int ticketsCount, CardState cardState, PlayerId currentPlayerId, Map<PlayerId, PlayerState> playerState, PlayerId lastPlayer, Deck<Ticket> tickets) {
        super(ticketsCount, cardState, currentPlayerId, Map.copyOf(playerState), lastPlayer);
        this.tickets = tickets;
        this.playerState = Map.copyOf(playerState);
        this.cardState = cardState;
    }

    /**
     * returns the initial state of a game of tCHu
     *
     * @param tickets the deck of tickets
     * @param rng     random generator
     * @return the initial state of a game of tCHu
     */
    public static GameState initial(SortedBag<Ticket> tickets, Random rng) {
        Deck<Card> deck = Deck.of(Constants.ALL_CARDS, rng);

        Map<PlayerId, PlayerState> playerState = new EnumMap<>(PlayerId.class);
        playerState.put(PlayerId.PLAYER_1, PlayerState.initial(deck.topCards(4)));
        playerState.put(PlayerId.PLAYER_2, PlayerState.initial(deck.withoutTopCards(4).topCards(4)));

        CardState cardState = CardState.of(deck.withoutTopCards(8));
        return new GameState(tickets.size(), cardState, PlayerId.ALL.get(rng.nextInt(2)), playerState, null, Deck.of(tickets, rng));
    }

    /**
     * returns the player's complete state of given identity, and not just its public part
     *
     * @param playerId identity of the player
     * @return the player's complete state of given identity, and not just its public part
     */
    @Override
    public PlayerState playerState(PlayerId playerId) {
        return playerState.get(playerId);
    }

    /**
     * returns the complete state of the current player, and not just its public part
     *
     * @return the complete state of the current player, and not just its public part
     */
    @Override
    public PlayerState currentPlayerState() {
        return playerState.get(currentPlayerId());
    }

    /**
     * returns the count of tickets from the top of the deck
     *
     * @param count count of tickets
     * @return the count of tickets from the top of the deck
     * @throws IllegalArgumentException if count is not between 0 and the size of the deck (included)
     */
    public SortedBag<Ticket> topTickets(int count) {
        Preconditions.checkArgument(0 <= count && count <= ticketsCount());
        return tickets.topCards(count);
    }

    /**
     * returns an identical state to the receiver, but without the tickets count of the top of the pile
     *
     * @param count number of tickets we want to remove
     * @return returns an identical state to the receiver, but without the tickets count of the top of the pile
     * @throws IllegalArgumentException if count is not between 0 and the size of the deck (included)
     */
    public GameState withoutTopTickets(int count) {
        Preconditions.checkArgument(0 <= count && count <= ticketsCount());
        return new GameState(ticketsCount() - count, cardState, currentPlayerId(), playerState, lastPlayer(), tickets.withoutTopCards(count));
    }

    /**
     * returns the card to the top of the deck
     *
     * @return the card to the top of the deck
     * @throws IllegalArgumentException if the deck is empty
     */
    public Card topCard() {
        Preconditions.checkArgument(!cardState.isDeckEmpty());
        return cardState.topDeckCard();
    }

    /**
     * returns an identical GameState to receiver without the top deck card
     *
     * @return an identical GameState to receiver without the top deck card
     * @throws IllegalArgumentException if the deck is empty
     */
    public GameState withoutTopCard() {
        Preconditions.checkArgument(!cardState.isDeckEmpty());
        return new GameState(ticketsCount(), cardState.withoutTopDeckCard(), currentPlayerId(), playerState, lastPlayer(), tickets);
    }

    /**
     * returns an identical state to the receiver but with the given cards added to the discard pile
     *
     * @param discardedCards cards to add to the discard pile
     * @return an identical state to the receiver but with the given cards added to the discard pile
     */
    public GameState withMoreDiscardedCards(SortedBag<Card> discardedCards) {
        return new GameState(ticketsCount(), cardState.withMoreDiscardedCards(discardedCards), currentPlayerId(), playerState, lastPlayer(), tickets);
    }

    /**
     * returns an identical state to the receiver unless the deck of cards is empty,
     * in which case it is recreated from the discard pile, shuffled using the given random generator
     *
     * @param rng random generator
     * @return an identical state to the receiver unless the deck of cards is empty,
     * in which case it is recreated from the discard pile, shuffled using the given random generator
     */
    public GameState withCardsDeckRecreatedIfNeeded(Random rng) {
        return (cardState.isDeckEmpty()) ? new GameState(ticketsCount(), cardState.withDeckRecreatedFromDiscards(rng), currentPlayerId(), playerState, lastPlayer(), tickets) : this;
    }

    /**
     * returns an identical state to the receiver but in which the given tickets have been added to the given player's hand
     *
     * @param playerId      identity of the player with more tickets
     * @param chosenTickets the tickets the player chose
     * @return an identical state to the receiver but in which the given tickets have been added to the given player's hand
     * @throws IllegalArgumentException if the player has at least 1 ticket
     */
    public GameState withInitiallyChosenTickets(PlayerId playerId, SortedBag<Ticket> chosenTickets) {
        Preconditions.checkArgument(playerState.get(playerId).ticketCount() == 0);
        EnumMap<PlayerId, PlayerState> map = new EnumMap<PlayerId, PlayerState>(playerState);
        map.replace(playerId, map.get(playerId).withAddedTickets(chosenTickets));
        return new GameState(ticketsCount(), cardState, currentPlayerId(), map, lastPlayer(), tickets);
    }

    /**
     * returns an identical state to the receiver, but in which the current player has drawn the drawnTickets from the top of the deck,
     * and chosen to keep those contained in chosenTicket
     *
     * @param drawnTickets  tickets withdrawn by the player
     * @param chosenTickets tickets kept by the player
     * @return an identical state to the receiver, but in which the current player has drawn the drawnTickets from the top of the deck,
     * and chosen to keep those contained in chosenTicket
     * @throws IllegalArgumentException if all the tickets kept is not included in that of the drawnTickets
     */
    public GameState withChosenAdditionalTickets(SortedBag<Ticket> drawnTickets, SortedBag<Ticket> chosenTickets) {
        Preconditions.checkArgument(drawnTickets.contains(chosenTickets));
        EnumMap<PlayerId, PlayerState> map = new EnumMap<PlayerId, PlayerState>(playerState);
        map.replace(currentPlayerId(), map.get(currentPlayerId()).withAddedTickets(chosenTickets));
        return new GameState(ticketsCount() - drawnTickets.size(), cardState, currentPlayerId(), map, lastPlayer(), tickets.withoutTopCards(drawnTickets.size()));
    }

    /**
     * returns an identical state to the receiver except that the face-up card at the given location has been placed in the current player's hand,
     * and replaced by the one at the top of the draw pile
     *
     * @param slot of the faced up card
     * @return an identical state to the receiver except that the face-up card at the given location has been placed in the current player's hand,
     * and replaced by the one at the top of the draw pile
     * @throws IllegalArgumentException if it is not possible to draw cards
     */
    public GameState withDrawnFaceUpCard(int slot) {
        Preconditions.checkArgument(canDrawCards());
        EnumMap<PlayerId, PlayerState> map = new EnumMap<PlayerId, PlayerState>(playerState);
        map.replace(currentPlayerId(), map.get(currentPlayerId()).withAddedCard(cardState.faceUpCard(slot)));
        return new GameState(ticketsCount(), cardState.withDrawnFaceUpCard(slot), currentPlayerId(), map, lastPlayer(), tickets);
    }

    /**
     * returns an identical state to the receiver except that the top card of the draw pile has been placed in the current player's hand
     *
     * @return an identical state to the receiver except that the top card of the draw pile has been placed in the current player's hand
     * @throws IllegalArgumentException if it is not possible to draw cards
     */
    public GameState withBlindlyDrawnCard() {
        Preconditions.checkArgument(canDrawCards());
        EnumMap<PlayerId, PlayerState> map = new EnumMap<PlayerId, PlayerState>(playerState);
        map.replace(currentPlayerId(), map.get(currentPlayerId()).withAddedCard(cardState.topDeckCard()));
        return new GameState(ticketsCount(), cardState.withoutTopDeckCard(), currentPlayerId(), map, lastPlayer(), tickets);
    }

    /**
     * returns an identical state to the receiver but in which the current player has seized the given route by means of the given cards
     *
     * @param route the route the player seized
     * @param cards the cards used to seize the route
     * @return an identical state to the receiver but in which the current player has seized the given route by means of the given cards
     */
    public GameState withClaimedRoute(Route route, SortedBag<Card> cards) {
        EnumMap<PlayerId, PlayerState> map = new EnumMap<PlayerId, PlayerState>(playerState);
        map.replace(currentPlayerId(), map.get(currentPlayerId()).withClaimedRoute(route, cards));
        return new GameState(ticketsCount(), cardState.withMoreDiscardedCards(cards), currentPlayerId(), map, lastPlayer(), tickets);
    }

    /**
     * returns true iff the last turn begins, ie if the identity of the last player is currently unknown but the current player has only two cars or less left;
     * this method should only be called at the end of a player's turn,
     *
     * @return true iff the last turn begins, ie if the identity of the last player is currently unknown but the current player has only two cars or less left
     */
    public boolean lastTurnBegins() {
        return lastPlayer() == null && playerState.get(currentPlayerId()).carCount() <= 2;
    }

     /**
     * returns an identical state to the receiver except that the current player is the one following the current current player;
     * furthermore, if lastTurnBegins returns true, the current player becomes the last player
     *
     * @return an identical state to the receiver except that the current player is the one following the current current player;
     * furthermore, if lastTurnBegins returns true, the current player becomes the last player
     */
    public GameState forNextTurn() {
        return (lastTurnBegins()) ? new GameState(ticketsCount(), cardState, currentPlayerId().next(), playerState, currentPlayerId(), tickets)
                : new GameState(ticketsCount(), cardState, currentPlayerId().next(), playerState, lastPlayer(), tickets);
    }
}
