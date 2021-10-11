package buildings.impl.array;

import buildings.Floor;
import buildings.Space;
import exceptions.SpaceIndexOutOfBoundsException;

public class DwellingFloor implements Floor {
    private Space[] spaces;

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
    public double getTotalArea(){
        double totalArea = 0;
        for (Space space : spaces) {
            totalArea += space.getArea();
        }
        return totalArea;
    }

    @Override
    public int getNumberOfRooms(){
        int totalNumberOfRooms = 0;
        for (Space space : spaces) {
            totalNumberOfRooms += space.getNumberOfRooms();
        }
        return totalNumberOfRooms;
    }

    @Override
    public Space[] toArray() {
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
            spaces[number] = space.copy();
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
            temp[number] = space.copy();
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
    public Floor copy() {
        return new DwellingFloor(toArray());
    }
}
