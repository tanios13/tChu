package ch.epfl.tchu.game;

import java.util.ArrayList;

public final class Printer {

    public static void printDeck(Deck<Card> d){
        System.out.println("Deck Size = " + d.size() + " ");
        ArrayList<Card> cards = new ArrayList<>();
        for (Card c : d.topCards(d.size())){
            cards.add(c);
        }
        System.out.println("    " + cards);
    }

    public static void printCardState(CardState c){
        ArrayList<Card> deckCards = new ArrayList<>();
        int size = c.deckSize();
        for (int i = 0; i < size; ++i){
            deckCards.add(c.topDeckCard());
            c = c.withoutTopDeckCard();
        }
        System.out.println("CardState size = " + size);
        System.out.println("    Deck Cards: " + deckCards);
        System.out.println("    FaceUp Cards: " + c.faceUpCards());
        System.out.println("    Deck Size: " + c.deckSize());
        System.out.println("    Discard Size: " + c.discardsSize());
    }

    public static void printPublicCardState(PublicCardState c){
        System.out.println("PublicCardState size = " + c.totalSize());
        System.out.println("    FaceUp Cards: " + c.faceUpCards());
        System.out.println("    Deck Size: " + c.deckSize());
        System.out.println("    Discard Size: " + c.discardsSize());
    }

    public static String toStringPublicCardState(PublicCardState c){
        String result = "";
        result += "PublicCardState size = " + c.totalSize();
        result += "\n" + "    FaceUp Cards: " + c.faceUpCards();
        result += "\n" + "    Deck Size: " + c.deckSize();
        result += "\n" + "    Discard Size: " + c.discardsSize();
        return result;
    }

    public static void printPlayerState(PlayerState p){
        System.out.println("PlayerState CarCount = " + p.carCount());
        System.out.println("    Cards: " + p.cards());
        System.out.println("    Tickets: " + p.tickets());
        System.out.println("    Routes: " + p.routes());
    }

    public static String toStringPlayerState(PlayerState p){
        String result = "";
        result += "PlayerState CarCount = " + p.carCount();
        result += "\n" + "    Cards: " + p.cards();
        result += "\n" + "    Tickets: " + p.tickets();
        result += "\n" + "    Routes: " + p.routes();
        return result;
    }
}