package ch.epfl.tchu.game;

import ch.epfl.tchu.SortedBag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class PublicGameStateTest {
    private static final List<Card> faceUpCards =
            List.of(
                    Card.BLACK,
                    Card.VIOLET,
                    Card.BLUE,
                    Card.GREEN,
                    Card.LOCOMOTIVE);

    private SortedBag<Card> cards = SortedBag.of(2, Card.BLUE)
            .union(SortedBag.of(2, Card.RED))
            .union(SortedBag.of(2, Card.WHITE))
            .union(SortedBag.of(2, Card.ORANGE))
            .union(SortedBag.of(2, Card.LOCOMOTIVE));
    private Deck<Card> deck = Deck.of(cards, new Random());

    Station LAU = new Station(13, "Lausanne");
    Station BER = new Station(3, "Berne");
    CardState cardState = CardState.of(deck);
    List<Route> list = List.of(new Route("test", LAU, BER, 5, Route.Level.UNDERGROUND, Color.BLACK));
    Map playerState1 = Map.of(PlayerId.PLAYER_1, new PublicPlayerState(5,6, list), PlayerId.PLAYER_2, new PublicPlayerState(4,8, list));
    Map playerState2 = Map.of(PlayerId.PLAYER_1, new PublicPlayerState(5,6, list));


    @Test
    void ConstructorFailsWithInvalidArguments(){

        assertThrows(IllegalArgumentException.class, () -> {
            new PublicGameState(-1, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState2, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new PublicGameState(2, null, PlayerId.PLAYER_1, playerState1, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new PublicGameState(2, cardState, null, playerState1, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new PublicGameState(2, cardState, PlayerId.PLAYER_1, null, null);
        });

    }

    @Test
    void ticketCountsWorks(){
        var gameState1 = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        assertEquals(2, gameState1.ticketsCount());
    }

    @Test
    void canDrawTicketsWorks(){
        var gameState1 = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        var gameState2 = new PublicGameState(0, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);

        assertEquals(true, gameState1.canDrawTickets());
        assertEquals(false, gameState2.canDrawTickets());

    }

    @Test
    void cardStateWorks(){
        var gameState = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        assertEquals(cardState, gameState.cardState());
    }

    @Test
    void canDrawCardsWorks(){
        var state1 = new PublicCardState(faceUpCards, 2, 3);
        var state2 = new PublicCardState(faceUpCards, 1, 2);
        var gameState1 = new PublicGameState(2, state1, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        var gameState2 = new PublicGameState(2, state2, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);

        assertEquals(true, gameState1.canDrawCards());
        assertEquals(false, gameState2.canDrawCards());
    }

    @Test
    void currentPlayerIdWorks(){
        var gameState = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        assertEquals(PlayerId.PLAYER_1, gameState.currentPlayerId());
    }

    @Test
    void playerStateWorks(){
        var gameState = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);

        assertEquals(playerState1.get(PlayerId.PLAYER_1), gameState.playerState(PlayerId.PLAYER_1));
        assertEquals(playerState1.get(PlayerId.PLAYER_2), gameState.playerState(PlayerId.PLAYER_2));
    }

    @Test
    void currentPlayerStateWorks(){
        var gameState = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);

        assertEquals(playerState1.get(PlayerId.PLAYER_1), gameState.currentPlayerState());

    }

    @Test
    void claimedRoutesWorks() {
        var gameState = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        List<Route> routes = gameState.claimedRoutes();
        for (Route route : routes) {
            List<Station> stations = route.stations();
            for (Station station : stations) {
                System.out.print(station + " ");
            }
            System.out.print("\n");

        }
    }

    @Test
    void lastPlayerWorks(){
        var gameState1 = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, PlayerId.PLAYER_2);
        var gameState2 = new PublicGameState(2, cardState, PlayerId.PLAYER_1, playerState1, null);

        assertEquals(PlayerId.PLAYER_2, gameState1.lastPlayer());
        assertEquals(null, gameState2.lastPlayer());
    }

}