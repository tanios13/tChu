package ch.epfl.tchu.game;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.TreeSet;

/**
 * A Ticket
 *
 * @author Ronan Tanios(325510)
 * @author Tamra Antoun(324875)
 */
public final class Ticket implements Comparable<Ticket> {
    private final List<Trip> trips;
    private final String representation;


    /**
     * construct a ticket made up of the list of station given
     *
     * @param trips list of the trips
     * @throws IllegalArgumentException if the list of trips is empty
     *                                  or if all the departure stations of the trips do not have the same name
     */
    public Ticket(List<Trip> trips) {
        if(trips.isEmpty())
            throw new IllegalArgumentException();

        Station departureStation = trips.get(0).from();
        for (Trip trip : trips) {
            if(!trip.from().name().equals(departureStation.name()))
                throw new IllegalArgumentException();
        }

        this.trips = trips;
        representation = computeText(departureStation, trips);
    }

    /**
     * construct a ticket consisting of a single trip
     * (call to main constructor)
     *
     * @param from   departure Station
     * @param to     arrival Station
     * @param points number of points given
     */
    public Ticket(Station from, Station to, int points) {
        this(List.of(new Trip(from, to, points)));
    }

    /**
     * returns the textual representation of the ticket
     *
     * @return the textual representation of the ticket
     */
    public String text() {
        return representation;
    }

    /**
     * Calculation of the textual representation
     *
     * @param departureStation departure Station
     * @param trips            list of the trips for the departure Station
     * @return the textual representation of the ticket
     */
    private static String computeText(Station departureStation, List<Trip> trips) {
        if(trips.size() == 1) {
            return departureStation.name() + " - " + trips.get(0).to() + " (" + trips.get(0).points() + ")";
        } else {
            TreeSet<String> destination = new TreeSet<>();
            for (Trip trip : trips) {
                destination.add(trip.to().name()
                        + " (" + trip.points() + ")");
            }
            return departureStation.name() + " - {" + String.join(", ", destination) + "}";
        }
    }

    /**
     * returns the number of points the ticket is worth
     *
     * @param connectivity connectivity of the player
     * @return the number of points the ticket is worth
     */
    public int points(StationConnectivity connectivity) {
        int points = trips.get(0).points(connectivity);
        for (Trip trip : this.trips) {
            if(points < trip.points(connectivity)) {
                points = trip.points(connectivity);
            }
        }
        return points;
    }

    /**
     * return the textual representation
     *
     * @return the textual representation
     */
    @Override
    public String toString() {
        return representation;
    }

    /**
     * compare the ticket to which it is applied (this)
     * to the one passed as an argument (that) in alphabetical order of their textual representation
     *
     * @param that the ticket that we would like to compare to this ticket
     * @return a strictly negative integer if this is strictly less than that
     * a strictly positive integer if this is strictly greater than that
     * and zero if the two are equal
     */
    @Override
    public int compareTo(Ticket that) {
        return this.text().compareTo(that.text());
    }


}
