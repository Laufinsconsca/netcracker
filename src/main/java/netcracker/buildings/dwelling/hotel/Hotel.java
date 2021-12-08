package netcracker.buildings.dwelling.hotel;

import netcracker.buildings.Building;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.Dwelling;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public final class Hotel extends Dwelling implements Building {
    private final double[] STAR_COEFFICIENT = new double[]{0.25, 0.5, 1, 1.25, 1.5};

    public Hotel(int numberOfFloors, int... numberOfFlats) {
        areValid(numberOfFloors, numberOfFlats);
        floors = new HotelFloor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            floors[i] = new HotelFloor(numberOfFlats[i]);
        }
    }

    public Hotel(Floor... floors) {
        super(floors);
    }

    public int getTotalNumberOfStars() {
        int numberOfStars = 0;
        for (Floor floor : this) {
            if (floor instanceof HotelFloor hotelFloor && hotelFloor.getNumberOfStars() > numberOfStars) {
                numberOfStars = hotelFloor.getNumberOfStars();
            }
        }
        return numberOfStars;
    }

    @Override
    public Space getBestSpace() {
        Space maxAreaSpace = null;
        int j = 0;
        HotelFloor tempHotelFloor = null;
        for (Floor floor : this) {
            if (floor instanceof HotelFloor hotelFloor) {
                maxAreaSpace = hotelFloor.getBestSpace();
                tempHotelFloor = hotelFloor;
                break;
            }
            j++;
        }
        assert maxAreaSpace != null;
        for (Floor floor : this) {
            if (floor != getFloor(j) && floor instanceof HotelFloor hotelFloor) {
                Space space = floor.getBestSpace();
                if (maxAreaSpace.getArea() * STAR_COEFFICIENT[tempHotelFloor.getNumberOfStars() - 1] < space.getArea() * STAR_COEFFICIENT[hotelFloor.getNumberOfStars() - 1]) {
                    maxAreaSpace = space;
                }
            }
        }
        return maxAreaSpace;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(String.valueOf(getNumberOfFloors()));
        for (Floor floor : floors) {
            joiner.add(floor.toString());
        }
        return "Hotel " + joiner;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return Objects.nonNull(obj)
                && obj instanceof Hotel of
                && of.getNumberOfFloors() == getNumberOfFloors()
                && IntStream.range(0, getNumberOfFloors()).mapToObj(i -> floors[i].equals(of.floors[i])).reduce(true, (a, b) -> a && b);
    }

    @Override
    public int hashCode() {
        return getNumberOfFloors() ^ IntStream.range(0, getNumberOfFloors()).mapToObj(i -> floors[i].hashCode()).reduce(floors.length, (a, b) -> a ^ b);
    }
}
