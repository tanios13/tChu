package ch.epfl.tchu;

import ch.epfl.tchu.game.*;
import ch.epfl.test.TestRandomizer;

import java.util.List;

public class TestElements {

    public static final Station A = new Station(0,"A");
    public static final Station B = new Station(1,"B");
    public static final Station C = new Station(2,"C");
    public static final Station D = new Station(3,"D");
    public static final Station E = new Station(4,"E");
    public static final Station F = new Station(5,"F");
    public static final Station G = new Station(6,"G");
    public static final Station H = new Station(7,"H");

    public static final Route r1 = new Route("r1", A, B, 3, Route.Level.OVERGROUND, Color.GREEN);
    public static final Route r2 = new Route("r2", C, E, 1, Route.Level.UNDERGROUND, null);
    public static final Route r3 = new Route("r3", E, F, 6, Route.Level.OVERGROUND, null);
    public static final Route r4 = new Route("r4", H, F, 5, Route.Level.UNDERGROUND, Color.GREEN);
    public static final Route r5 = new Route("r5", G, F, 3, Route.Level.UNDERGROUND, null);
    public static final Route r6 = new Route("r6", C, D, 3, Route.Level.UNDERGROUND, null);
    public static final Route r7 = new Route("r7", C, B, 6, Route.Level.UNDERGROUND, null);
    public static final Route r8 = new Route("r8", C, G, 6, Route.Level.UNDERGROUND, null);
    public static final Route r9 = new Route("r9", C, F, 6, Route.Level.UNDERGROUND, null);
    public static final Route r10 = new Route("r10", C, A, 6, Route.Level.UNDERGROUND, null);


    public static final Ticket t1 = new Ticket(A, C, 13);
    public static final Ticket t2 = new Ticket(E, F, 8);
    public static final Ticket t3 = new Ticket(D, G, 10);
    public static final Ticket t4 = new Ticket(H, A, 13);
    public static final Ticket t5 = new Ticket(D, E, 8);
    public static final Ticket t6 = new Ticket(A, G, 10);

    public static final SortedBag<Card> c1 = new SortedBag.Builder<Card>()
            .add(2,Card.BLUE)
            .add(Card.YELLOW)
            .add(4,Card.GREEN)
            .add(3,Card.LOCOMOTIVE)
            .add(Card.BLACK)
            .build();

    public static final SortedBag<Card> c2 = new SortedBag.Builder<Card>()
            .add(2,Card.BLUE)
            .add(Card.YELLOW)
            .add(1,Card.LOCOMOTIVE)
            .build();

    public static final SortedBag<Card> c3 = new SortedBag.Builder<Card>().build();

    public static final SortedBag<Card> c4 = new SortedBag.Builder<Card>()
            .add(4,Card.GREEN)
            .add(Card.BLACK)
            .add(1,Card.LOCOMOTIVE)
            .build();

    public static final SortedBag<Card> c5 = new SortedBag.Builder<Card>()
            .add(2,Card.BLUE)
            .add(Card.YELLOW)
            .add(2,Card.LOCOMOTIVE)
            .add(4,Card.GREEN)
            .add(1,Card.BLACK)
            .build();


    public static final SortedBag<Card> c6 = new SortedBag.Builder<Card>()
            .add(2,Card.GREEN).build();
    public static final SortedBag<Card> c7 = new SortedBag.Builder<Card>()
            .add(2,Card.GREEN)
            .add(1,Card.LOCOMOTIVE).build();

    public static final SortedBag<Card> c8 = new SortedBag.Builder<Card>()
            .add(2,Card.BLUE)
            .add(2,Card.LOCOMOTIVE)
            .add(3,Card.GREEN)
            .build();

    public static final SortedBag<Card> c9 = new SortedBag.Builder<Card>()
            .add(2,Card.BLUE)
            .add(2,Card.LOCOMOTIVE)
            .add(3,Card.GREEN)
            .add(Card.YELLOW)
            .add(Card.BLACK)
            .add(4,Card.WHITE)
            .build();

    public static final SortedBag<Card> c10 = new SortedBag.Builder<Card>()
            .add(3,Card.GREEN)
            .add(Card.LOCOMOTIVE)
            .add(Card.BLACK)
            .build();


    public static final Deck<Card> deck1 = Deck.of(c9, TestRandomizer.newRandom());
    public static final Deck<Card> nonTrivialDeck = Deck.of(c10, TestRandomizer.newRandom());



    public static final PlayerState p1 = new PlayerState(SortedBag.of(1,t1,1,t2), c1, List.of(r1,r2,r3,r4));
    public static final PlayerState p2 = new PlayerState(SortedBag.of(), c3, List.of());
    public static final PlayerState p3 = PlayerState.initial(c2);
    public static final PlayerState p4 = new PlayerState(SortedBag.of(1,t3,1,t2), c1, List.of(r2,r3,r5,r6));


    public static final CardState cardState1 = CardState.of(deck1);
    public static final CardState nonTrivialCardState = CardState.of(nonTrivialDeck);


    public static final SortedBag<Card> drawn1 = new SortedBag.Builder<Card>()
            .add(3,Card.LOCOMOTIVE).build();
    public static final SortedBag<Card> drawn2 = new SortedBag.Builder<Card>()
            .add(Card.BLUE)
            .add(Card.YELLOW)
            .add(Card.GREEN).build();
    public static final SortedBag<Card> drawn3 = new SortedBag.Builder<Card>()
            .add(Card.BLUE)
            .add(Card.YELLOW)
            .add(Card.LOCOMOTIVE).build();
    public static final SortedBag<Card> falseDrawn1 = new SortedBag.Builder<Card>()
            .add(Card.BLUE)
            .add(Card.LOCOMOTIVE).build();
    public static final SortedBag<Card> falseDrawn2 = new SortedBag.Builder<Card>()
            .add(Card.BLUE)
            .add(2,Card.GREEN)
            .add(Card.LOCOMOTIVE).build();

}