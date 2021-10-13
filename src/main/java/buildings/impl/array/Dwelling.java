package buildings.impl.array;

import buildings.Building;
import buildings.Floor;
import buildings.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

import static sort.Sort.quickSort;

public class Dwelling implements Building {
    private final Floor[] floors;

    public Dwelling(int numberOfFloors, int... numberOfFlats) {
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
        floors = new DwellingFloor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            floors[i] = new DwellingFloor(numberOfFlats[i]);
        }
    }

    public Dwelling(Floor... floors) {
        this.floors = floors;
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
            floors[number] = floor.copy();
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
                        floor.alterSpace(j, space.copy());
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
                        floor.addSpace(j, space.copy());
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
                array[i++] = space.copy();
            }
        }
        quickSort(array, 0, array.length - 1);
        return array;
    }

    public String toString() {
        Floor[] floors = getFloorsArray();
        if (floors.length == 0) {
            return "Здание пусто";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getNumberOfFloors(); i++) {
            builder.append(floors[i]).append("\n");
        }
        return builder.toString();
    }
}
