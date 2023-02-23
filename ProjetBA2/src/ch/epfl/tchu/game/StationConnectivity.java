package ch.epfl.tchu.game;

public interface StationConnectivity {
    /**
     * Returns true if the stations are connected by the player's network.
     * @param s1
     *         first station
     * @param s2
     *         second station
     * @return true
     *           if the stations are connected by the player's network
     */
    public abstract boolean connected(Station s1, Station s2);
}
