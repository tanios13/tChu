package ch.epfl.tchu.game;

import java.util.List;

public enum PlayerId {
    PLAYER_1,
    PLAYER_2;

    /**
     * List of the values of PlayerId
     */
    public static List<PlayerId> ALL = List.of(PlayerId.values());

    /**
     * Contains the number of values of PlayerId
     */
    public static int COUNT = ALL.size();

    /**
     * returns the identity of the player following the one to which the method is applied
     *
     * @return the identity of the player following the one to which the method is applied
     */
    public PlayerId next(){
        return (this == (PLAYER_1))? PLAYER_2 : PLAYER_1;
    }

}