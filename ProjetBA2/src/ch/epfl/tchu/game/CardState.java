package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ch.epfl.tchu.game.Constants.*;
import static java.util.Objects.checkIndex;

/**
 * add private state of the cards to public state
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */
public final class CardState extends PublicCardState {

    private final Deck<Card> deck;
    private final SortedBag<Card> discardCards;

    private CardState(List<Card> faceUpCards, int deckSize, int discardsSize, Deck<Card> deck, SortedBag<Card> discardCards) {
        super(faceUpCards, deckSize, discardsSize);
        this.deck = deck;
        this.discardCards = discardCards;
    }

    /**
     * returns a state in which the 5 cards faced up are the first 5 of the given pile
     * the draw pile consists of the remaining cards of the pile and the discard pile is empty
     *
     * @param deck deck of cards
     * @return a state in which the 5 cards faced up are the first 5 of the given pile
     * the draw pile consists of the remaining cards of the pile and the discard pile is empty
     * @throws IllegalArgumentException if the given pile contains less than 5 cards
     */
    public static CardState of(Deck<Card> deck) {
        Preconditions.checkArgument(!(deck.size() < 5));
        return new CardState(deck.topCards(FACE_UP_CARDS_COUNT).toList(), deck.size() - FACE_UP_CARD_SLOTS.size(),
                0, deck.withoutTopCards(FACE_UP_CARDS_COUNT), SortedBag.of());
    }

    /**
     * returns a set of cards identical to the receiver (this)
     * except that the face-up index slot card has been replaced by the one at the top of the draw pile which is removed at the same time
     *
     * @param slot of the faced up card
     * @return a set of cards identical to the receiver (this)
     * except that the face-up index slot card has been replaced by the one at the top of the draw pile which is removed at the same time
     * @throws IndexOutOfBoundsException if the given index is not between 0 (included) and 5 (excluded)
     * @throws IllegalArgumentException  if the deck is empty
     */
    public CardState withDrawnFaceUpCard(int slot) {
        Preconditions.checkArgument(!isDeckEmpty());
        List<Card> newFaceUpCards = new ArrayList<>();
        for (int i : FACE_UP_CARD_SLOTS) {
            if(i == checkIndex(slot, FACE_UP_CARDS_COUNT))
                newFaceUpCards.add(topDeckCard());
            else
                newFaceUpCards.add(faceUpCards().get(i));
        }
        return new CardState(newFaceUpCards, deckSize() - 1, discardsSize(), deck.withoutTopCards(1), discardCards);
    }

    /**
     * returns the card at the top of the deck
     *
     * @return the card at the top of the deck
     * @throws IllegalArgumentException if the deck is empty
     */
    public Card topDeckCard() {
        Preconditions.checkArgument(!isDeckEmpty());
        return deck.topCard();
    }

    /**
     * returns a set of cards identical to the receiver (this) but without the card at the top of the deck
     *
     * @return a set of cards identical to the receiver (this) but without the card at the top of the deck
     * @throws IllegalArgumentException if the deck is empty
     */
    public CardState withoutTopDeckCard() {
        Preconditions.checkArgument(!isDeckEmpty());
        return new CardState(faceUpCards(), deckSize() - 1, discardsSize(), deck.withoutTopCard(), discardCards);
    }

    /**
     * returns a set of cards identical to the receiver (this)
     * except that the cards from the discard pile have been shuffled using the given random generator to form the new draw pile
     *
     * @param rng random generator
     * @return a set of cards identical to the receiver (this)
     * except that the cards from the discard pile have been shuffled using the given random generator to form the new draw pile
     * @throws IllegalArgumentException if the receiver(this)'s deck is not empty
     */
    public CardState withDeckRecreatedFromDiscards(Random rng) {
        Preconditions.checkArgument(isDeckEmpty());
        Deck<Card> shuffledPile = Deck.of(discardCards, rng);
        return new CardState(faceUpCards(), discardsSize(), 0, shuffledPile, SortedBag.of());
    }

    /**
     * returns a set of cards identical to the receiver (this)
     * but with the given cards added to the discard pile.
     *
     * @param additionalDiscards card added to the discard pile
     * @return a set of cards identical to the receiver (this)
     * but with the given cards added to the discard pile.
     */
    public CardState withMoreDiscardedCards(SortedBag<Card> additionalDiscards) {
        return new CardState(faceUpCards(), deckSize(),
                discardsSize() + additionalDiscards.size(), deck, discardCards.union(additionalDiscards));
    }

}
