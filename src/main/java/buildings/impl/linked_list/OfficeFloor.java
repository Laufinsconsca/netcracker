package buildings.impl.linked_list;

import buildings.Floor;
import buildings.Space;
import buildings.impl.array.Flat;
import exceptions.SpaceIndexOutOfBoundsException;

public class OfficeFloor implements Floor {
    private Node head;

    public OfficeFloor(int numberOfOffices) {
        if (numberOfOffices <= 0) {
            throw new IllegalArgumentException("Отрицательное количество офисов либо этаж пуст");
        }
        head = Node.of(new Office());
        Node temp = head;
        for (int i = 0; i < numberOfOffices - 1; i++) {
            temp.next = Node.of(new Office());
            temp = temp.next;
        }
        temp.next = head;
    }

    public OfficeFloor(Space... offices) {
        head = Node.of(offices[0]);
        Node temp = head;
        for (int i = 1; i < offices.length; i++) {
            temp.next = Node.of(offices[i]);
            temp = temp.next;
        }
        temp.next = head;
    }

    @Override
    public int getNumberOfSpaces() {
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
    public double getTotalArea() {
        double totalArea = 0;
        Space[] offices = toArray();
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            totalArea += offices[i].getArea();
        }
        return totalArea;
    }

    @Override
    public int getNumberOfRooms() {
        int totalNumberOfRooms = 0;
        Space[] offices = toArray();
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            totalNumberOfRooms += offices[i].getNumberOfRooms();
        }
        return totalNumberOfRooms;
    }

    @Override
    public Space[] toArray() {
        if (head == null) {
            return new Office[]{};
        }
        Space[] spaces = new Office[getNumberOfSpaces()];
        Node temp = head;
        spaces[0] = temp.getSpace();
        for (int i = 1; i < getNumberOfSpaces(); i++) {
            temp = temp.next;
            spaces[i] = temp.getSpace();
        }
        return spaces;
    }

    @Override
    public Space getSpace(int number) {
        return getNode(number).getSpace();
    }

    @Override
    public void alterSpace(int number, Space space) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else if (getNumberOfSpaces() == 1) {
            head = Node.of(space);
            head.next = head;
        } else if (number == 0) {
            Node last = getNode(getNumberOfSpaces() - 1);
            Node temp = last.next;
            Node newNode = Node.of(space);
            newNode.next = temp.next;
            last.next = newNode;
            head = newNode;
            temp.next = null;
        } else {
            Node prev = getNode(number - 1);
            Node temp = prev.next;
            Node newNode = Node.of(space);
            newNode.next = temp.next;
            prev.next = newNode;
            temp.next = null;
        }
    }

    @Override
    public void addSpace(int number, Space office) {
        addNode(Node.of(office), number);
    }

    @Override
    public void deleteSpace(int number) {
        deleteNode(number);
    }

    @Override
    public Space getBestSpace() {
        Space[] offices = toArray();
        double maxArea = 0;
        Space maxAreaOffice = null;
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            if (offices[i].getArea() > maxArea) {
                maxArea = offices[i].getArea();
                maxAreaOffice = offices[i];
            }
        }
        return maxAreaOffice;
    }

    @Override
    public Floor copy() {
        return new OfficeFloor(toArray());
    }

    public String toString() {
        Space[] offices = toArray();
        if (offices.length == 0) {
            return "Этаж пуст";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            builder.append(offices[i]).append(" ");
        }
        return builder.toString();
    }

    private static class Node {
        private Space space;
        Node next;

        private Node() {
        }

        public static Node of(Space space) {
            Node newNode = new Node();
            if (space instanceof Flat) {
                newNode.space = new Flat(space.getArea(), space.getNumberOfRooms());
            } else if (space instanceof Office) {
                newNode.space = new Office(space.getArea(), space.getNumberOfRooms());
            }
            return newNode;
        }

        public Space getSpace() {
            return space;
        }
    }

    private void addNode(Node node, int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else if (head == null) {
            head = node;
            head.next = head;
        } else if (number == 0) {
            Node last = getNode(getNumberOfSpaces() - 1);
            node.next = head;
            last.next = node;
            head = node;
        } else {
            Node temp = getNode(number - 1);
            node.next = temp.next;
            temp.next = node;
        }
    }

    private void deleteNode(int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else if (head.next == head) {
            head.next = null;
            head = null;
        } else if (number == 0) {
            Node last = getNode(getNumberOfSpaces() - 1);
            Node temp = last.next;
            last.next = temp.next;
            head = temp.next;
            temp.next = null;
        } else {
            Node prev = getNode(number - 1);
            Node temp = prev.next;
            prev.next = temp.next;
            temp.next = null;
        }
    }

    private Node getNode(int number) {
        if (number < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер офиса");
        } else if (number > getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего офиса более чем на единицу");
        } else {
            Node temp = head;
            for (int i = 0; i < number; i++) {
                temp = temp.next;
            }
            return temp;
        }
    }
}
