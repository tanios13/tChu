package ch.epfl.tchu.gui;

import ch.epfl.tchu.SortedBag;
import ch.epfl.tchu.game.Card;
import ch.epfl.tchu.game.Route;
import ch.epfl.tchu.game.Trail;

import java.util.ArrayList;
import java.util.List;

/**
 * generate messages that describe the progress of the game
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */

public final class Info {
    private final String playerName;

    /**
     * Returns the player's name
     * @param playerName the player's name
     */
    public Info(String playerName){
        this.playerName = playerName;
    }

    /**
     * Returns the name of the given card.
     * @param card the card
     * @param count
     * @return the name of the given card
     */
    public static String cardName(Card card, int count){

        switch (card) {
            case BLACK : return StringsFr.BLACK_CARD + StringsFr.plural(count);
            case BLUE : return StringsFr.BLUE_CARD + StringsFr.plural(count);
            case GREEN : return StringsFr.GREEN_CARD + StringsFr.plural(count);
            case ORANGE : return StringsFr.ORANGE_CARD + StringsFr.plural(count);
            case RED : return StringsFr.RED_CARD + StringsFr.plural(count);
            case VIOLET : return StringsFr.VIOLET_CARD + StringsFr.plural(count);
            case WHITE : return StringsFr.WHITE_CARD + StringsFr.plural(count);
            case YELLOW : return StringsFr.YELLOW_CARD + StringsFr.plural(count);
            case LOCOMOTIVE : return StringsFr.LOCOMOTIVE_CARD + StringsFr.plural(count);
            default: throw new Error();
        }
    }



    /**
     * Returns the message declaring that the given players finished the game equally with the given points.
     * @param playerNames the players' names
     * @param points the winning points
     * @return the message declaring that the given players finished the game equally with the given points
     */
    public static String draw(List<String> playerNames, int points){
        return String.format(StringsFr.DRAW, String.join(StringsFr.AND_SEPARATOR, playerNames), points);
    }

    /**
     * Returns the message declaring that the player will play first.
     * @return the message declaring that the player will play first
     */
    public String willPlayFirst(){
        return String.format(StringsFr.WILL_PLAY_FIRST, playerName);
    }

    /**
     * Returns the message declaring how many tickets the player kept.
     * @param count the number of tickets
     * @return the message declaring how many tickets the player kept.
     */
    public String keptTickets(int count){
        return String.format(StringsFr.KEPT_N_TICKETS, playerName, count, StringsFr.plural(count));
    }

    /**
     * Returns the message declaring that the player can play.
     * @return the message declaring that the player can play
     */
    public String canPlay(){
        return String.format(StringsFr.CAN_PLAY, playerName) ;
    }

    /**
     * Returns the message declaring that the player drew the given number of tickets.
     * @param count the number of tickets
     * @return the message declaring that the player drew the given number of tickets
     */
    public String drewTickets(int count){
        return String.format(StringsFr.DREW_TICKETS, playerName, count, StringsFr.plural(count));
    }

    /**
     * Returns the message declaring that the player drew a card at the top of the deck.
     * @return the message declaring that the player drew a card at the top of the deck"
     */
    public String drewBlindCard(){
        return String.format(StringsFr.DREW_BLIND_CARD, playerName);
    }

    /**
     * Returns the message declaring that the player drew the card face up.
     * @param card the face up card
     * @return the message declaring that the player drew the card face up
     */
    public String drewVisibleCard(Card card){
        return  String.format(StringsFr.DREW_VISIBLE_CARD, playerName, cardName(card, 1) );
    }

    /**
     * Returns the message declaring that the player got possession of the given road using the given cards.
     * @param route the possessed road
     * @param cards the used cards
     * @return the message declaring that the player got possession of the given road using the given cards
     */
    public String claimedRoute(Route route, SortedBag<Card> cards){
        return String.format(StringsFr.CLAIMED_ROUTE, playerName, getRouteName(route), cardSDescription(cards) ) ;

    }

    /**
     * Returns the message declaring that the player wants to get possession of of the tunnel road using the given cards.
     * @param route the rod the player wants to possess
     * @param initialCards the cards he wants to use
     * @return the message declaring that the player wants to get possession of of the tunnel road using the given cards
     */
    public String attemptsTunnelClaim(Route route, SortedBag<Card> initialCards){
        return String.format(StringsFr.ATTEMPTS_TUNNEL_CLAIM, playerName, getRouteName(route), cardSDescription(initialCards));
    }

    /**
     * Returns the message declaring that the player drew the 3 additional cards and implies an additional cost.
     * @param drawnCards the drawn cards
     * @param additionalCost the additional cost
     * @return the message declaring that the player drew the 3 additional cards and implies an additional cost
     */
    public String drewAdditionalCards(SortedBag<Card> drawnCards, int additionalCost){
        if(additionalCost == 0)
            return String.format(StringsFr.ADDITIONAL_CARDS_ARE, cardSDescription(drawnCards)) + StringsFr.NO_ADDITIONAL_COST;
        else return String.format(StringsFr.ADDITIONAL_CARDS_ARE, cardSDescription(drawnCards)) + String.format(StringsFr.SOME_ADDITIONAL_COST, additionalCost, StringsFr.plural(additionalCost));
    }

    /**
     * Returns the message declaring that the player did not claim the road.
     * @param route the given road
     * @return the message declaring that the player did not claim the road
     */
    public String didNotClaimRoute(Route route){
        return String.format(StringsFr.DID_NOT_CLAIM_ROUTE, playerName, getRouteName(route));
    }

    /**
     * Returns the message declaring that the player has only the 2 or less car cards left and that the last turn begins.
     * @param carCount the number of car cards left
     * @return the message declaring that the player has only the 2 or less car cards left and that the last turn begins
     */
    public String lastTurnBegins(int carCount){
        return String.format(StringsFr.LAST_TURN_BEGINS, playerName, carCount, StringsFr.plural(carCount));
    }

    /**
     * Returns the message declaring that the player gets the longest trail bonus.
     * @param longestTrail the longest trail.
     * @return the message declaring that the player gets the longest trail bonus
     */
    public String getsLongestTrailBonus(Trail longestTrail){
        return String.format(StringsFr.GETS_BONUS, playerName, getTrailName(longestTrail));
    }

    /**
     * Returns the message declaring that the player won with his points and the loser's points.
     * @param points the winner's points
     * @param loserPoints the loser's points
     * @return the message declaring that the player won with his points and the loser's points
     */
    public String won(int points, int loserPoints){
        return String.format(StringsFr.WINS, playerName, points, StringsFr.plural(points), loserPoints, StringsFr.plural(loserPoints));
    }

    private static String getTrailName(Trail trail){
        return trail.station1() + StringsFr.EN_DASH_SEPARATOR + trail.station2();
    }

    private static String getRouteName(Route route){
        return route.station1() + StringsFr.EN_DASH_SEPARATOR + route.station2();
    }

    private static String cardSDescription(SortedBag<Card> cards){
        List<String> listCards = new ArrayList<>();
        for(Card c: cards.toSet()){
            int n = cards.countOf(c);
                listCards.add(n + " " + cardName(c, n));
        }
        int listSize = listCards.size();
        return (listSize == 1) ? listCards.get(listSize-1) :
                String.join(", ", listCards.subList(0, listSize-1)) + StringsFr.AND_SEPARATOR + listCards.get(listSize-1);
    }
}