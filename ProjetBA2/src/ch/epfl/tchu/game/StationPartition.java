package ch.epfl.tchu.game;

import ch.epfl.tchu.Preconditions;

/**
 * Partition of stations
 *
 * @author Tamra Antoun(324875)
 * @author Ronan Tanios(325510)
 */
public final class StationPartition implements StationConnectivity {

    private final int[] partition;

    private StationPartition(int[] partition) {
        this.partition = partition;
    }

    @Override
    public boolean connected(Station s1, Station s2) {
        if(s1.id() < partition.length && s2.id() < partition.length)
            return partition[s1.id()] == partition[s2.id()];
        return s1.id() == s2.id();
    }


    public final static class Builder {

        private int stationCount;
        private int[] partitionB;

        /**
         * construct a partition builder of a set of stations whose identity is between 0 (included) and stationCount (excluded)
         *
         * @param stationCount number of stations in the map
         * @throws IllegalArgumentException if stationCount is strictly negative
         */
        public Builder(int stationCount) {
            Preconditions.checkArgument(stationCount >= 0);
            this.stationCount = stationCount;
            partitionB = new int[stationCount];
            for (int i = 0; i < stationCount; i++) {
                partitionB[i] = i;
            }
        }

        /**
         * return this Builder with the sub-sets containing the two stations passed in argument joined,
         * and "electing" one of the two representatives as representative of the joined sub-assembly
         *
         * @param s1 first Station
         * @param s2 second Station
         * @return this builder with the sub-sets containing the two stations passed in argument joined,
         * and "electing" one of the two representatives as representative of the joined sub-assembly
         */
        public Builder connect(Station s1, Station s2) {
            if(representative(s1.id()) != representative(s2.id())) {
                if(s2.id() == partitionB[s2.id()])
                    partitionB[s2.id()] = s1.id();
                else if(s1.id() == partitionB[s1.id()])
                    partitionB[s1.id()] = s2.id();
                else
                    partitionB[representative(s2.id())] = s1.id();
            }
            return this;
        }

        /**
         * returns the flattened partition of the stations corresponding to the deep partition under construction by this builder
         *
         * @return the flattened partition of the stations corresponding to the deep partition under construction by this builder
         */
        public StationPartition build() {
            for (int i = 0; i < stationCount; i++)
                partitionB[i] = representative(i);
            return new StationPartition(partitionB);
        }

        /**
         * returns the representative's id of the sub-set containing the station given
         *
         * @param id station id
         * @return the representative's id of the sub-set containing the station given
         */
        private int representative(int id) {
            return (partitionB[id] == id) ? id : representative(partitionB[id]);
        }

    }
}
