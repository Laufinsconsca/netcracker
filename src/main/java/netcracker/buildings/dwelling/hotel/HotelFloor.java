package netcracker.buildings.dwelling.hotel;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.DwellingFloor;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public final class HotelFloor extends DwellingFloor implements Floor {
    private static final int DEFAULT_NUMBER_OF_STARS = 1;
    private int numberOfStars;

    public HotelFloor(int number) {
        super(number);
        numberOfStars = DEFAULT_NUMBER_OF_STARS;
    }

    public HotelFloor(Space... spaces) {
        super(spaces);
        numberOfStars = DEFAULT_NUMBER_OF_STARS;
    }

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(String.valueOf(getNumberOfSpaces()));
        for (Space space : spaces) {
            joiner.add(space.toString());
        }
        return "HotelFloor " + joiner;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return Objects.nonNull(obj)
                && obj instanceof HotelFloor of
                && of.getNumberOfSpaces() == getNumberOfSpaces()
                && of.getNumberOfStars() == getNumberOfStars()
                && IntStream.range(0, getNumberOfSpaces()).mapToObj(i -> spaces[i].equals(of.spaces[i])).reduce(true, (a, b) -> a && b);
    }

    public int hashCode() {
        return getNumberOfSpaces() ^ getNumberOfStars() ^ IntStream.range(0, getNumberOfSpaces()).mapToObj(i -> spaces[i].hashCode()).reduce(spaces.length, (a, b) -> a ^ b);
    }
}
