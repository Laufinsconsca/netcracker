package netcracker.buildings.dwelling;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.hotel.HotelFloor;
import netcracker.exceptions.SpaceIndexOutOfBoundsException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public sealed class DwellingFloor implements Floor permits HotelFloor {
    protected Space[] spaces;

    public DwellingFloor(int number) {
        spaces = new Flat[number];
        for (int i = 0; i < spaces.length; i++) {
            spaces[i] = new Flat();
        }
    }

    public DwellingFloor(Space... spaces) {
        this.spaces = spaces;
    }

    @Override
    public int getNumberOfSpaces() {
        return spaces.length;
    }

    @Override
    public double getTotalArea() {
        double totalArea = 0;
        for (Space space : spaces) {
            totalArea += space.getArea();
        }
        return totalArea;
    }

    @Override
    public int getNumberOfRooms() {
        int totalNumberOfRooms = 0;
        for (Space space : spaces) {
            totalNumberOfRooms += space.getNumberOfRooms();
        }
        return totalNumberOfRooms;
    }

    @Override
    public Space[] getSpacesArray() {
        return spaces;
    }

    @Override
    public Space getSpace(int number) {
        return spaces[number];
    }

    @Override
    public void alterSpace(int number, Space space) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else if (space != null) {
            spaces[number] = (Space) space.clone();
        }
    }

    @Override
    public void addSpace(int number, Space space) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else if (space != null) {
            Space[] temp = new Space[getNumberOfSpaces() + 1];
            System.arraycopy(spaces, 0, temp, 0, number);
            temp[number] = (Space) space.clone();
            System.arraycopy(spaces, number, temp, number + 1, spaces.length - number);
            spaces = temp;
        }
    }

    @Override
    public void deleteSpace(int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else {
            Space[] temp = new Space[spaces.length - 1];
            System.arraycopy(spaces, 0, temp, 0, number);
            System.arraycopy(spaces, number + 1, temp, number, spaces.length - number - 1);
            spaces = temp;
        }
    }

    @Override
    public Space getBestSpace() {
        Space maxAreaSpace = spaces[0];
        for (int i = 1; i < spaces.length; i++) {
            if (maxAreaSpace.getArea() < spaces[i].getArea())
                maxAreaSpace = spaces[i];
        }
        return maxAreaSpace;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return Objects.nonNull(obj)
                && obj instanceof DwellingFloor of
                && of.getNumberOfSpaces() == getNumberOfSpaces()
                && IntStream.range(0, getNumberOfSpaces()).mapToObj(i -> spaces[i].equals(of.spaces[i])).reduce(true, (a, b) -> a && b);
    }

    @Override
    public int hashCode() {
        return getNumberOfSpaces() ^ IntStream.range(0, getNumberOfSpaces()).mapToObj(i -> spaces[i].hashCode()).reduce(spaces.length, (a, b) -> a ^ b);
    }

    @Override
    public Floor clone() {
        Space[] spaces = new Space[getNumberOfSpaces()];
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            spaces[i] = (Space) this.spaces[i].clone();
        }
        return new DwellingFloor(spaces);
    }

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<>() {
            int i = 0;

            public boolean hasNext() {
                return i != getNumberOfSpaces();
            }

            public Space next() {
                if (i == getNumberOfSpaces()) {
                    throw new NoSuchElementException();
                }
                return spaces[i++];
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(String.valueOf(getNumberOfSpaces()));
        for (Space space : spaces) {
            joiner.add(space.toString());
        }
        return "DwellingFloor " + joiner;
    }

    @Override
    public int compareTo(Floor o) {
        return getNumberOfSpaces() - o.getNumberOfSpaces();
    }
}
