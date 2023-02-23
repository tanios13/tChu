package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Trip
 *
 * @author Ronan Tanios(325510)
 * @author Tamara Antoun(324875)
 */
public final class Trip {
    private final Station from;
    private final Station to;
    private final int points;

    /**
     * Construct a new route between the two given stations
     * and worth the given number of points
     *
     * @param from   departure station
     * @param to     arrival station
     * @param points number of points given
     * @throws IllegalArgumentException if the number of points is not strictly positive
     */
    public Trip(Station from, Station to, int points) {
        if(points <= 0)
            throw new IllegalArgumentException();
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
        this.points = points;

    }

    /**
     * returns the list of all the possible trips
     *
     * @param from   List of the departure station
     * @param to     List of the arrival station
     * @param points number of points given for each trip
     * @return the list of all the possible trips from one of the stations of the first list (from)
     * to one of the stations of the second list (to)
     * each one worth the given number of points
     * @throws IllegalArgumentException if one of the list is empty
     *                                  or if the number of points is not strictly positive
     */
    public static List<Trip> all(List<Station> from, List<Station> to, int points) {
        if(points <= 0 || from.isEmpty() || to.isEmpty())
            throw new IllegalArgumentException();

        ArrayList<Trip> all = new ArrayList<>();
        for (Station departureStation : from) {
            for (Station arrivalStation : to) {
                all.add(new Trip(departureStation, arrivalStation, points));
            }
        }
        return all;
    }

    /**
     * returns the departure station of the trip
     *
     * @return the departure station of the trip
     */
    public Station from() {
        return from;
    }

    /**
     * returns the arrival station of the trip
     *
     * @return the arrival station of the trip
     */
    public Station to() {
        return to;
    }

    /**
     * returns the number of points of the trip
     *
     * @return the number of points of the trip
     */
    public int points() {
        return points;
    }

    /**
     * returns the number of points of the trip for the given connectivity.
     *
     * @param connectivity connectivity of the two stations
     * @return the number of points of the trip if the 2 stations are connected
     * or the negation of the points if the 2 stations of the trip are not connected
     */
    public int points(StationConnectivity connectivity) {
        boolean connected = connectivity.connected(from, to);
        int playerPoints = connected ? this.points : -this.points;
        return playerPoints;
    }

}
