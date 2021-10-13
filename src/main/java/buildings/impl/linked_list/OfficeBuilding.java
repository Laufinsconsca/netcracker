package buildings.impl.linked_list;

import buildings.Building;
import buildings.Floor;
import buildings.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

import static sort.Sort.quickSort;

public class OfficeBuilding implements Building {
    private Node head;

    public OfficeBuilding(int numberOfFloors, int... numberOfOffices) {
        if (numberOfFloors != numberOfOffices.length) {
            throw new IllegalArgumentException("Количество этажей не равно размеру массива количества офисов по этажам");
        }
        if (numberOfFloors <= 0) {
            throw new IllegalArgumentException("Отрицательное количество этажей либо здание пусто");
        }
        boolean isValid = false;
        for (int i : numberOfOffices) {
            isValid |= i <= 0;
        }
        if (isValid) {
            throw new IllegalArgumentException("Отрицательное количество офисов либо этаж пуст");
        }
        head = Node.of(new OfficeFloor(numberOfOffices[0]));
        Node temp = head;
        for (int i = 1; i < numberOfFloors; i++) {
            temp.next = Node.of(new OfficeFloor(numberOfOffices[i]));
            temp.next.prev = temp;
            temp = temp.next;
        }
        temp.next = head;
        head.prev = temp;
    }

    public OfficeBuilding(Floor... floors) {
        head = Node.of(floors[0]);
        Node temp = head;
        for (int i = 1; i < floors.length; i++) {
            temp.next = Node.of(floors[i]);
            temp.next.prev = temp;
            temp = temp.next;
        }
        temp.next = head;
        head.prev = temp;
    }

    @Override
    public int getNumberOfFloors() {
        int i = 0;
        if (head == null) {
            return i;
        }
        i++;
        Node temp = head.next;
        while (temp != head) {
            i++;
            temp = temp.next;
        }
        return i;
    }

    @Override
    public int getNumberOfSpaces() {
        int numberOfOffices = 0;
        Floor[] officeFloors = getFloorsArray();
        for (int i = 0; i < getNumberOfFloors(); i++) {
            numberOfOffices += officeFloors[i].getNumberOfSpaces();
        }
        return numberOfOffices;
    }

    @Override
    public double getTotalArea() {
        double totalArea = 0;
        Floor[] officeFloors = getFloorsArray();
        for (int i = 0; i < getNumberOfFloors(); i++) {
            totalArea += officeFloors[i].getTotalArea();
        }
        return totalArea;
    }

    @Override
    public int getNumberOfRooms() {
        int totalNumberOfRooms = 0;
        Floor[] officeFloors = getFloorsArray();
        for (int i = 0; i < getNumberOfFloors(); i++) {
            totalNumberOfRooms += officeFloors[i].getNumberOfRooms();
        }
        return totalNumberOfRooms;
    }

    @Override
    public Floor[] getFloorsArray() {
        if (head == null) {
            return new Floor[]{};
        }
        Floor[] floors = new Floor[getNumberOfFloors()];
        Node temp = head;
        floors[0] = temp.getFloor();
        for (int i = 1; i < getNumberOfFloors(); i++) {
            temp = temp.next;
            floors[i] = temp.getFloor();
        }
        return floors;
    }

    @Override
    public Floor getFloor(int number) {
        return getNode(number).getFloor();
    }

    @Override
    public void alterFloor(int number, Floor floor) {
        if (number < 0) {
            throw new FloorIndexOutOfBoundsException("Отрицательный номер этажа");
        } else if (number > getNumberOfFloors()) {
            throw new FloorIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего этажа более чем на единицу");
        } else if (getNumberOfFloors() == 1) {
            head = Node.of(floor);
            head.next = head;
            head.prev = head;
        } else {
            Node prev = number == 0 ? head.prev : getNode(number - 1);
            Node temp = prev.next;
            Node newNode = Node.of(floor);
            newNode.next = temp.next;
            newNode.prev = prev;
            temp.next.prev = newNode;
            prev.next = newNode;
            temp.next = null;
            temp.prev = null;
            if (number == 0) {
                head = newNode;
            }
        }
    }

    @Override
    public Space getSpace(int number) {
        int[] numbers = decomposeEndToEndNumber(number);
        return getFloorsArray()[numbers[0]].getSpace(numbers[1]);
    }

    @Override
    public void alterSpace(int number, Space space) {
        int[] numbers = decomposeEndToEndNumber(number);
        getFloor(numbers[0]).alterSpace(numbers[1], space);
    }

    @Override
    public void addSpace(int number, Space space) {
        int[] numbers = decomposeEndToEndNumber(number);
        getFloor(numbers[0]).addSpace(numbers[1], space);
    }

    @Override
    public void deleteSpace(int number) {
        int[] numbers = decomposeEndToEndNumber(number);
        getFloor(numbers[0]).deleteSpace(numbers[1]);
        if (getFloor(numbers[0]).getNumberOfSpaces() == 0) {
            deleteNode(numbers[0]);
        }
    }

    @Override
    public Space getBestSpace() {
        Floor[] floors = getFloorsArray();
        double maxArea = 0;
        Space maxAreaOffice = null;
        for (int i = 0; i < getNumberOfFloors(); i++) {
            Space tempOffice = floors[i].getBestSpace();
            if (tempOffice.getArea() > maxArea) {
                maxArea = tempOffice.getArea();
                maxAreaOffice = tempOffice;
            }
        }
        return maxAreaOffice;
    }

    @Override
    public Space[] getSortedAreaArray() {
        Space[] array = new Space[getNumberOfSpaces()];
        int i = 0;
        Floor[] floors = getFloorsArray();
        for (Floor floor : floors) {
            Space[] spaces = floor.getSpacesArray();
            for (Space space : spaces) {
                array[i++] = space.copy();
            }
        }
        quickSort(array, 0, array.length - 1);
        return array;
    }

    private int[] decomposeEndToEndNumber(int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else {
            int j = 0, k;
            int[] numbers = new int[2];
            Floor[] floors = getFloorsArray();
            for (int i = 0; i < floors.length; i++) {
                k = j;
                j += floors[i].getNumberOfSpaces();
                if (number < j) {
                    numbers[0] = i;
                    numbers[1] = number - k;
                    break;
                }
            }
            return numbers;
        }
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

    private void addNode(int number, Node newNode) {
        if (number < 0) {
            throw new FloorIndexOutOfBoundsException("Отрицательный номер этажа");
        } else if (number > getNumberOfFloors()) {
            throw new FloorIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего этажа более чем на единицу");
        } else if (head == null) {
            head = newNode;
            newNode.next = head;
            newNode.prev = head;
        } else {
            Node prev = number == 0 ? head.prev : getNode(number - 1);
            newNode.next = prev.next;
            newNode.prev = prev;
            prev.next.prev = newNode;
            prev.next = newNode;
            if (number == 0) {
                head = newNode;
            }
        }
    }

    private Node getNode(int number) {
        if (number < 0) {
            throw new FloorIndexOutOfBoundsException("Отрицательный номер этажа");
        } else if (number > getNumberOfFloors()) {
            throw new FloorIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего этажа более чем на единицу");
        } else {
            Node temp = head;
            for (int i = 0; i < number; i++) {
                temp = temp.next;
            }
            return temp;
        }
    }

    private void deleteNode(int number) {
        if (number < 0) {
            throw new FloorIndexOutOfBoundsException("Отрицательный номер этажа");
        } else if (number > getNumberOfFloors()) {
            throw new FloorIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего этажа более чем на единицу");
        } else if (head == head.next) {
            head.next = null;
            head.prev = null;
            head = null;
        } else {
            Node prev = number == 0 ? head.prev : getNode(number - 1);
            Node temp = prev.next;
            temp.next.prev = prev;
            prev.next = temp.next;
            temp.next = null;
            temp.prev = null;
            if (number == 0) {
                head = prev.next;
            }
        }
    }

    private static class Node {
        Node next;
        Node prev;
        private Floor floor;

        private Node() {
        }

        public static Node of(Floor floor) {
            Node newNode = new Node();
            newNode.floor = floor.copy();
            return newNode;
        }

        public Floor getFloor() {
            return floor;
        }
    }
}
