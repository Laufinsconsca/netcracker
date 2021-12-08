package netcracker.buildings.dwelling;

import netcracker.buildings.Building;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.hotel.Hotel;
import netcracker.exceptions.FloorIndexOutOfBoundsException;
import netcracker.exceptions.SpaceIndexOutOfBoundsException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import static netcracker.sort.Sort.quickSort;

public sealed class Dwelling implements Building permits Hotel {
    protected Floor[] floors;

    public Dwelling(int numberOfFloors, int... numberOfFlats) {
        areValid(numberOfFloors, numberOfFlats);
        floors = new DwellingFloor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            floors[i] = new DwellingFloor(numberOfFlats[i]);
        }
    }

    public Dwelling(Floor... floors) {
        this.floors = floors;
    }

    protected void areValid(int numberOfFloors, int... numberOfFlats) {
        if (numberOfFloors != numberOfFlats.length) {
            throw new IllegalArgumentException("Количество этажей не равно размеру массива количества квартир по этажам");
        }
        if (numberOfFloors <= 0) {
            throw new IllegalArgumentException("Отрицательное количество этажей либо здание пусто");
        }
        boolean isValid = false;
        for (int i : numberOfFlats) {
            isValid |= i <= 0;
        }
        if (isValid) {
            throw new IllegalArgumentException("Отрицательное количество квартир либо этаж пуст");
        }
    }

    @Override
    public int getNumberOfFloors() {
        return floors.length;
    }

    @Override
    public int getNumberOfSpaces() {
        int sum = 0;
        for (Floor a : floors) {
            sum += a.getNumberOfSpaces();
        }
        return sum;
    }

    @Override
    public double getTotalArea() {
        double sum = 0;
        for (Floor a : this.floors) {
            sum += a.getTotalArea();
        }
        return sum;
    }

    @Override
    public int getNumberOfRooms() {
        int sum = 0;
        for (Floor a : this.floors) {
            sum += a.getNumberOfRooms();
        }
        return sum;
    }

    @Override
    public Floor[] getFloorsArray() {
        return floors;
    }

    @Override
    public Floor getFloor(int index) {
        return floors[index];
    }

    @Override
    public void alterFloor(int number, Floor floor) {
        if (number < 0) {
            throw new FloorIndexOutOfBoundsException("Отрицательный номер этажа");
        } else if (number > floors.length) {
            throw new FloorIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего этажа более чем на единицу");
        } else {
            floors[number] = (Floor) floor.clone();
        }
    }

    @Override
    public Space getSpace(int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер квартиры");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайней квартиры более чем на единицу");
        } else {
            int temp = 0;
            for (Floor floor : floors) {
                for (int j = 0; j < floor.getNumberOfSpaces(); j++) {
                    if (temp == number) {
                        return floor.getSpace(j);
                    } else {
                        temp++;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void alterSpace(int number, Space space) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер квартиры");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайней квартиры более чем на единицу");
        } else {
            int temp = 0;
            for (Floor floor : floors) {
                for (int j = 0; j < floor.getNumberOfSpaces(); j++) {
                    if (temp == number) {
                        floor.alterSpace(j, (Space) space.clone());
                        return;
                    } else {
                        temp++;
                    }
                }
            }
        }
    }

    @Override
    public void addSpace(int number, Space space) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер квартиры");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайней квартиры более чем на единицу");
        } else {
            int temp = 0;
            for (Floor floor : floors) {
                for (int j = 0; j < floor.getNumberOfSpaces(); j++) {
                    if (temp == number) {
                        floor.addSpace(j, (Space) space.clone());
                        return;
                    } else {
                        temp++;
                    }
                }
            }
        }
    }

    @Override
    public void deleteSpace(int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер квартиры");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайней квартиры более чем на единицу");
        } else {
            int temp = 0;
            for (Floor floor : floors) {
                for (int j = 0; j < floor.getNumberOfSpaces(); j++) {
                    if (temp == number) {
                        floor.deleteSpace(j);
                        return;
                    } else {
                        temp++;
                    }
                }
            }
        }
    }

    @Override
    public Space getBestSpace() {
        Space maxAreaSpace = floors[0].getBestSpace();
        for (int i = 1; i < floors.length; i++) {
            Space space = floors[i].getBestSpace();
            if (maxAreaSpace.getArea() < space.getArea()) {
                maxAreaSpace = space;
            }
        }
        return maxAreaSpace;
    }

    @Override
    public Space[] getSortedAreaArray() {
        int i = 0;
        Space[] array = new Space[getNumberOfSpaces()];
        for (Floor floor : floors) {
            for (Space space : floor.getSpacesArray()) {
                array[i++] = (Space) space.clone();
            }
        }
        quickSort(array, 0, array.length - 1);
        return array;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return Objects.nonNull(obj)
                && obj instanceof Dwelling of
                && of.getNumberOfFloors() == getNumberOfFloors()
                && IntStream.range(0, getNumberOfFloors()).mapToObj(i -> floors[i].equals(of.floors[i])).reduce(true, (a, b) -> a && b);
    }

    @Override
    public int hashCode() {
        return getNumberOfFloors() ^ IntStream.range(0, getNumberOfFloors()).mapToObj(i -> floors[i].hashCode()).reduce(floors.length, (a, b) -> a ^ b);
    }

    @Override
    public Object clone() {
        Floor[] floors = new Floor[getNumberOfFloors()];
        for (int i = 0; i < getNumberOfFloors(); i++) {
            floors[i] = (Floor) this.floors[i].clone();
        }
        return new Dwelling(floors);
    }

    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<>() {
            int i = 0;

            public boolean hasNext() {
                return i != getNumberOfFloors();
            }

            public Floor next() {
                if (i == getNumberOfFloors()) {
                    throw new NoSuchElementException();
                }
                return floors[i++];
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(String.valueOf(getNumberOfFloors()));
        for (Floor floor : floors) {
            joiner.add(floor.toString());
        }
        return "Dwelling " + joiner;
    }
}
