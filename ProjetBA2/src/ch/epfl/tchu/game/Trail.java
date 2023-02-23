package ch.epfl.tchu.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tamara Antoun(324875)
 * @author Ronan Tanios(325510)
 */
public final class Trail {
    private final Station station1;
    private final Station station2;
    private final List<Route> routes;

    private Trail(Station station1, Station station2, List<Route> routes) {
        this.station1 = station1;
        this.station2 = station2;
        this.routes = routes;

    }

    /**
     * returns the longest path of the network consisting of the given routes
     *
     * @param routes list of the routes for which we want to determine the longest trail
     * @return the longest path of the network consisting of the given routes
     */
    public static Trail longest(List<Route> routes) {
        Trail longestTrail = new Trail(null, null, null);
        int longestLength = 0;

        ArrayList<Trail> trailList = new ArrayList<Trail>();
        for (Route route : routes) {
            trailList.add(new Trail(route.station1(), route.station2(), List.of(route)));
            trailList.add(new Trail(route.station2(), route.station1(), List.of(route)));
        }

        while (!trailList.isEmpty()) {
            ArrayList<Trail> tempTrailList = new ArrayList<Trail>();
            for (Trail trail : trailList) {
                for (Route route : routes) {
                    if (!trail.routes.contains(route) && (trail.station2().equals(route.station2()) || trail.station2().equals(route.station1()))) {
                        List<Route> newRoute = new ArrayList<Route>(trail.routes);
                        newRoute.add(route);
                        tempTrailList.add(new Trail(trail.station1, route.stationOpposite(trail.station2), newRoute));
                    }
                }
            }

            for (Trail trail : trailList) {
                if (trail.length() > longestLength) {
                    longestLength = trail.length();
                    longestTrail = trail;
                }
            }
            trailList = tempTrailList;
        }
        return longestTrail;
    }

    /**
     * Returns the trail's length
     *
     * @return the trail's length
     */
    public int length() {
        int length = 0;
        if(routes != null){
            for (Route route : routes) {
                length += route.length();
            }
        }
        return length;
    }

    /**
     * Returns the first station of the trail
     *
     * @return the first station of the trail
     */
    public Station station1() {
        if(this.length() == 0) {
            return null;
        }
        return station1;
    }

    /**
     * Returns the last station of the trail
     *
     * @return the last station of the trail
     */
    public Station station2() {
        if(this.length() == 0) {
            return null;
        }
        return station2;
    }

    /**
     * returns the textual representation of the trail
     * @return the textual representation of the trail
     */
    public String toString(){
        if(this.length() == 0){
            return "The trail is empty";
        }
        StringBuilder text = new StringBuilder(station1.toString());
        Station tempStation = station1;
        for(Route route: routes){
            tempStation = route.stationOpposite(tempStation);
            text.append(" - " + tempStation.toString());
        }
        text.append(" (" + this.length() + ")");
        return text.toString();
    }

}
