package netcracker.buildings.office;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public final class OfficeFloor implements Floor {
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

    public OfficeFloor(Space... spaces) {
        head = Node.of(spaces[0]);
        Node temp = head;
        for (int i = 1; i < spaces.length; i++) {
            temp.next = Node.of(spaces[i]);
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
        Space[] offices = getSpacesArray();
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            totalArea += offices[i].getArea();
        }
        return totalArea;
    }

    @Override
    public int getNumberOfRooms() {
        int totalNumberOfRooms = 0;
        Space[] offices = getSpacesArray();
        for (int i = 0; i < getNumberOfSpaces(); i++) {
            totalNumberOfRooms += offices[i].getNumberOfRooms();
        }
        return totalNumberOfRooms;
    }

    @Override
    public Space[] getSpacesArray() {
        if (head == null) {
            return new Space[]{};
        }
        Space[] spaces = new Space[getNumberOfSpaces()];
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
        Space[] offices = getSpacesArray();
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (Objects.nonNull(obj) && obj instanceof OfficeFloor of && of.getNumberOfSpaces() == getNumberOfSpaces()) {
            boolean areEqual = true;
            Node node = head;
            Node objNode = of.head;
            do {
                areEqual &= node.space.equals(objNode.space);
                node = node.next;
                objNode = objNode.next;
            } while (node != head);
            return areEqual;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int numberOfSpaces;
        Node node = head;
        int hashCode = node.space.hashCode();
        node = node.next;
        for (numberOfSpaces = 0; node != head; numberOfSpaces++) {
            hashCode ^= node.space.hashCode();
            node = node.next;
        }
        hashCode ^= numberOfSpaces;
        return hashCode;
    }

    @Override
    public Object clone() {
        return new OfficeFloor(getSpacesArray());
    }

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<>() {
            boolean isFirst = true;
            private Node node = head;

            @Override
            public boolean hasNext() {
                return isFirst || node != head;
            }

            @Override
            public Space next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Space space = node.space;
                node = node.next;
                if (node == head) isFirst = false;
                return space;
            }
        };
    }

    @Override
    public String toString() {
        Node node = head;
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(String.valueOf(getNumberOfSpaces()));
        do {
            joiner.add(node.getSpace().toString());
            node = node.next;
        } while (node != head);
        return "OfficeFloor " + joiner;
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

    @Override
    public int compareTo(Floor o) {
        return getNumberOfSpaces() - o.getNumberOfSpaces();
    }

    private static class Node implements Serializable {
        Node next;
        private Space space;

        private Node() {
        }

        public static Node of(Space space) {
            Node newNode = new Node();
            newNode.space = (Space) space.clone();
            return newNode;
        }

        public Space getSpace() {
            return space;
        }
    }
}
