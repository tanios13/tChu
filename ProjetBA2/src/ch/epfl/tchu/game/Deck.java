package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;
import ch.epfl.tchu.SortedBag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A deck of cards
 *
 * @author Tamara Antoun(324875)
 * @author Ronan Tanios(325510)
 */

public final class Deck<C extends Comparable<C>> {
    private final List<C> cards;

    private Deck(List<C> cards) {
        this.cards = List.copyOf(cards);
    }

    /**
     * Returns a shuffled deck of cards.
     *
     * @param cards the cards we want to shuffle
     * @param rng   random number
     * @param <C>   the type of the cards
     * @return the cards shuffled
     */
    public static <C extends Comparable<C>> Deck<C> of(SortedBag<C> cards, Random rng) {
        List<C> shuffledCards = cards.toList();
        Collections.shuffle(shuffledCards, rng);
        return new Deck<C>(shuffledCards);
    }

    /**
     * Returns the size of the deck.
     *
     * @return the size of the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns true if and only if the deck is empty.
     *
     * @return true if and only if the deck is empty
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the card at the top of the deck.
     *
     * @return the card at the top of the deck
     * @throws if the deck is empty
     */
    public C topCard() {
        Preconditions.checkArgument(!this.isEmpty());
        return cards.get(0);
    }

    /**
     * Returns the deck without the card at the top.
     *
     * @return the deck without the card at the top
     * @throws if the deck is empty
     */
    public Deck<C> withoutTopCard() {
        Preconditions.checkArgument(!this.isEmpty());
        List<C> withoutTopCard = new ArrayList<>(cards);
        withoutTopCard.remove(0);
        return new Deck<C>(withoutTopCard);
    }

    /**
     * Returns a multiset with the "count" cards at the top of the deck.
     *
     * @param count the number of cards at the top of the deck
     * @return a multiset with the "count" cards at the top of the deck
     * @throws IllegalArgumentException if count is not between 0 (included) and the heap size (included)
     */
    public SortedBag<C> topCards(int count) {
        Preconditions.checkArgument((count >= 0 && count <= this.size()));
        return SortedBag.of(cards.subList(0, count));
    }

    /**
     * Returns the same deck but without the "count" cards at the top of the deck.
     *
     * @param count the number of cards at the top of the deck
     * @return the same deck but without the "count" cards at the top of the deck
     * @throws IllegalArgumentException if count is not between 0 (included) and the heap size (included)
     */
    public Deck<C> withoutTopCards(int count) {
        Preconditions.checkArgument((count >= 0 && count <= this.size()));
        List<C> newCards = cards.subList(count, this.size());
        return new Deck<C>(newCards);
    }
}