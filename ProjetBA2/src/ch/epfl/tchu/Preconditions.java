package ch.epfl.tchu;

/**
 * @author Ronan Tanios(325510)
 * @author Tamra Antoun(324875)
 */
public final class Preconditions {
    private Preconditions() {
    }

    /**
     * Checks if the argument is true or false.
     *
     * @param shouldBeTrue the argument
     * @throws IllegalArgumentException if the argument is false
     */
    public static void checkArgument(boolean shouldBeTrue) {
        if(shouldBeTrue == false)
            throw new IllegalArgumentException();
    }
}
