package ch.epfl.tchu.game;

import ch.epfl.tchu.SortedBag;
import org.junit.jupiter.api.Test;

import java.awt.font.GlyphMetrics;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    Station s1 = new Station(0, "0");
    Station s2 = new Station(1, "1");
    Station s3 = new Station(2, "2");
    Station s4 = new Station(3, "3");

    Route route  = new Route("1", s1, s2, 3, Route.Level.OVERGROUND, null);


    Ticket t1 = new Ticket(s1, s2, 6);
    Ticket t2 = new Ticket(s3, s4, 5);
    SortedBag<Ticket> ticketList = SortedBag.of(2, t2).union(SortedBag.of(1, t1));
    GameState game = GameState.initial(ticketList, new Random());


    @Test
    void initial() {
        assertEquals(Constants.ALL_CARDS.size() - 8, game.cardState().totalSize());
        assertEquals(null, game.lastPlayer());
        System.out.println(game.playerState(PlayerId.PLAYER_1).cards());
        System.out.println(game.playerState(PlayerId.PLAYER_2).cards());
        System.out.println(game.currentPlayerId());
    }

    @Test
    void playerState() {
        assertEquals(4, game.playerState(PlayerId.PLAYER_1).cardCount());
        assertEquals(4, game.playerState(PlayerId.PLAYER_2).cardCount());
        assertEquals(0, game.playerState(PlayerId.PLAYER_1).ticketPoints());
        assertEquals(0, game.playerState(PlayerId.PLAYER_2).ticketPoints());
        assertEquals(0, game.playerState(PlayerId.PLAYER_1).tickets().size());
        assertEquals(0, game.playerState(PlayerId.PLAYER_2).tickets().size());
    }

    @Test
    void currentPlayerState() {
        assertTrue(game.currentPlayerId() == PlayerId.PLAYER_1 || game.currentPlayerId() == PlayerId.PLAYER_2);
    }

    @Test
    void topTickets() {
        assertThrows(IllegalArgumentException.class, () -> {
            game.topTickets(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.topTickets(4);
        });

        game.topTickets(3);
        assertEquals(ticketList, game.topTickets(3));
    }

    @Test
    void withoutTopTickets() {
        assertThrows(IllegalArgumentException.class, () -> {
            game.withoutTopTickets(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.withoutTopTickets(4);
        });
        assertEquals(0, game.withoutTopTickets(3).ticketsCount());

        GameState g = game;
        for (int i = 0; i < 3; i++)
            g = g.withoutTopTickets(1);

        final GameState gg = g;
        assertThrows(IllegalArgumentException.class, () -> {
            gg.withoutTopTickets(1);
        });
    }

    @Test
    void topCard() {
        GameState g = game;
        for (int i = 0; i < Constants.ALL_CARDS.size() - 8 - 5; i++)
            g = g.withoutTopCard();
        final GameState gg = g;
        assertThrows(IllegalArgumentException.class, () -> {
            gg.topCard();
        });
    }

    @Test
    void withoutTopCard() {
        GameState g = game.withoutTopCard();
        assertEquals(Constants.ALL_CARDS.size() - 8 - 5, game.cardState().deckSize());
        assertEquals(Constants.ALL_CARDS.size() - 8 - 5 - 1, g.cardState().deckSize());
        assertEquals(5, g.cardState().faceUpCards().size());

        GameState g2 = game;
        for (int i = 0; i < Constants.ALL_CARDS.size() - 8 - 5; i++)
            g2 = g2.withoutTopCard();
        final GameState gg = g2;
        assertThrows(IllegalArgumentException.class, () -> {
            gg.withoutTopCard();
        });
    }

    @Test
    void withMoreDiscardedCards() {
        GameState g = game.withMoreDiscardedCards(SortedBag.of(2, Card.LOCOMOTIVE));
        assertEquals(2, g.cardState().discardsSize());
        assertEquals(0, game.cardState().discardsSize());

    }

    @Test
    void withCardsDeckRecreatedIfNeeded() {
        assertEquals(game.cardState(), game.withCardsDeckRecreatedIfNeeded(new Random()).cardState());
        assertEquals(game.cardState(), game.withCardsDeckRecreatedIfNeeded(new Random()).cardState());
        GameState g = game.withMoreDiscardedCards(SortedBag.of(2, Card.LOCOMOTIVE));

        for (int i = 0; i < Constants.ALL_CARDS.size() - 8 - 5; i++)
            g = g.withoutTopCard();
        final GameState gg = g.withCardsDeckRecreatedIfNeeded(new Random());
        assertEquals(2, gg.cardState().deckSize());
        assertFalse(gg.cardState() == game.cardState());
    }

    @Test
    void withInitiallyChosenTickets() {
        GameState g = game.withInitiallyChosenTickets(PlayerId.PLAYER_1, SortedBag.of(t1));
        assertThrows(IllegalArgumentException.class, () -> {
            g.withInitiallyChosenTickets(PlayerId.PLAYER_1, SortedBag.of(t1));
            ;
        });
        assertEquals(SortedBag.of(t1), g.playerState(PlayerId.PLAYER_1).tickets());
    }

    @Test
    void withChosenAdditionalTickets() {
        GameState g = game.withChosenAdditionalTickets(game.topTickets(2), SortedBag.of(t2));
        assertEquals(3, game.ticketsCount());
        assertEquals(1, g.ticketsCount());
        assertEquals(SortedBag.of(t2), g.currentPlayerState().tickets());
        assertEquals(ticketList.difference(game.topTickets(2)), g.topTickets(1));

        assertThrows(IllegalArgumentException.class, () -> {
            g.withChosenAdditionalTickets(game.topTickets(2), SortedBag.of(new Ticket(s3, s4, 5)));
        });
    }

    @Test
    void withDrawnFaceUpCard() {
        List<Card> c = game.cardState().faceUpCards();
        GameState g = game.withDrawnFaceUpCard(2);
        assertTrue(g.playerState(g.currentPlayerId()).cards().contains(c.get(2)));
        assertEquals(game.cardState().deckSize() - 1, g.cardState().deckSize());

        for (int i = 0; i < Constants.ALL_CARDS.size() - 8 - 8; i++)
            g = g.withoutTopCard();
        final GameState gg = g;
        assertThrows(IllegalArgumentException.class, () -> {
            gg.withDrawnFaceUpCard(3);
        });
    }

    @Test
    void withBlindlyDrawnCard() {
        GameState g = game.withBlindlyDrawnCard();
        assertTrue(game.cardState().faceUpCards().equals(g.cardState().faceUpCards()));
        assertEquals(game.cardState().deckSize() - 1, g.cardState().deckSize());
        assertEquals(g.playerState(g.currentPlayerId().next()).cards().size()+1,g.playerState(g.currentPlayerId()).cards().size());
    }

    @Test
    void withClaimedRoute() {
        assertEquals(0, game.cardState().discardsSize());
        GameState g = game.withClaimedRoute(route, SortedBag.of(3, Card.LOCOMOTIVE));
        assertEquals(3, g.cardState().discardsSize());
        assertTrue(game.playerState(game.currentPlayerId()).cards().difference(SortedBag.of(3, Card.LOCOMOTIVE)).equals(g.playerState(g.currentPlayerId()).cards()));
        assertEquals(List.of(), game.playerState(game.currentPlayerId()).routes());
        assertTrue(List.of(route).equals(g.playerState(g.currentPlayerId()).routes()));

    }

    @Test
    void lastTurnBeginsAndForNextTurn() {
        assertFalse(game.lastTurnBegins());
        List<Route> ALL_ROUTES = List.of(
                new Route("AT1_STG_1", s1, s3, 5, Route.Level.UNDERGROUND, null),
                new Route("AT2_VAD_1", s2, s3, 5, Route.Level.UNDERGROUND, Color.RED),
                new Route("BAD_BAL_1", s2, s3, 5, Route.Level.UNDERGROUND, Color.RED),
                new Route("BAD_OLT_1", s2, s3, 4, Route.Level.OVERGROUND, Color.VIOLET),
                new Route("BAL_DE1_1", s2, s3, 5, Route.Level.UNDERGROUND, Color.BLUE),
                new Route("BAL_DEL_1", s2, s3, 5, Route.Level.UNDERGROUND, Color.YELLOW),
                new Route("BAL_OLT_1", s2, s3, 5, Route.Level.UNDERGROUND, Color.ORANGE),
                new Route("BEL_LOC_1", s2, s3, 5, Route.Level.UNDERGROUND, Color.BLACK));

        GameState g = game;

        for(Route route : ALL_ROUTES){
            g = g.withClaimedRoute(route, SortedBag.of(3, Card.LOCOMOTIVE));
        }
        GameState gg = g;
        assertTrue(gg.lastTurnBegins());
        GameState game2 = gg.forNextTurn();
        assertFalse(game2.lastPlayer() == null);
        assertFalse(game2.lastTurnBegins());
    }

    @Test
    void forNextTurn() {

    }
}