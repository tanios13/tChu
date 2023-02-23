package ch.epfl.tchu.game;

import java.util.List;

import static java.util.Objects.checkIndex;

/**
 * add private state of the cards to public state
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */
public class PublicCardState {
    private final List<Card> faceUpCards;
    private final int deckSize;
    private final int discardsSize;

    /**
     * construct a public card state in which face up cards are given,
     * the draw pile contains deckSize cards and the discard pile contains discardsSize cards
     *
     * @param faceUpCards face up cards
     * @param deckSize number of cards in the deck
     * @param discardsSize number of cards in the discard pile
     * @throws IllegalArgumentException if faceUpCards does not contain the correct number of items (5)
     *                                  or if the size of the draw pile or discard pile is negative
     */
    public PublicCardState(List<Card> faceUpCards, int deckSize, int discardsSize){
        if(faceUpCards.size() != 5 || deckSize < 0 || discardsSize < 0)
            throw new IllegalArgumentException();

        this.faceUpCards = List.copyOf(faceUpCards);
        this.deckSize = deckSize;
        this.discardsSize = discardsSize;
    }

    /**
     * returns the total number of cards that are not in the players' hand, namely
     * the 5 whose face is up, those from the draw pile and those from the discard pile
     * @return the total number of cards that are not in the players' hand
     */
    public int totalSize(){
        return faceUpCards.size() + deckSize + discardsSize;
    }

    /**
     * returns the 5 cards face up
     * @return the 5 cards face up
     */
    public List<Card> faceUpCards(){
        return faceUpCards;
    }

    /**
     * returns the card face up at the given index
     * @param slot index of the faceUpCard we want
     * @return the card face up at the given index
     * @throws IndexOutOfBoundsException if the index is not between 0 (included) and 5 (excluded)
     */
    public Card faceUpCard(int slot){
        return faceUpCards.get(checkIndex(slot, 5));
    }

    /**
     * returns the size of the deck
     * @return the size of the deck
     */
    public int deckSize(){
        return deckSize;
    }

    /**
     * returns true if the deck is empty
     * @return true if the deck is empty
     */
    public boolean isDeckEmpty(){
        return (deckSize == 0) ? true : false;
    }

    /**
     * returns the size of the discard pile
     * @return the size of the discard pile.
     */
    public int discardsSize(){
        return discardsSize;
    }

}
