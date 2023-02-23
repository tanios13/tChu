package ch.epfl.tchu.game;

/**
 * Station
 *
 * @author Ronan Tanios(325510)
 * @author Tamra Antoun(324875)
 */
public final class Station {
    private int id;
    private String name;

    /**
     * Builds a station with an identification number and specific name.
     * @param id
     *         the identification number
     * @param name
     *          the name
     */
    public Station(int id, String name){
        if(id<0)
            throw new IllegalArgumentException();
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the station's the number.
     * @return the station's number
     */
    public int id(){
        return id;
    }

    /**
     * Returns the station's name.
     * @return the station's name
     */
    public String name(){
        return name;
    }

    /**
     * Returns the station's name.
     * @return the station's name.
     */
    @Override
    public String toString(){
        return name;
    }
}
