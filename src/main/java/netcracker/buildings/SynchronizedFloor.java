package netcracker.buildings;

import java.util.Iterator;

public final class SynchronizedFloor implements Floor {
    Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public synchronized int getNumberOfSpaces() {
        return floor.getNumberOfSpaces();
    }

    @Override
    public synchronized double getTotalArea() {
        return floor.getTotalArea();
    }

    @Override
    public synchronized int getNumberOfRooms() {
        return floor.getNumberOfRooms();
    }

    @Override
    public synchronized Space[] getSpacesArray() {
        return floor.getSpacesArray();
    }

    @Override
    public synchronized Space getSpace(int number) {
        return floor.getSpace(number);
    }

    @Override
    public synchronized void alterSpace(int number, Space space) {
        floor.alterSpace(number, space);
    }

    @Override
    public synchronized void addSpace(int number, Space space) {
        floor.addSpace(number, space);
    }

    @Override
    public synchronized void deleteSpace(int number) {
        floor.deleteSpace(number);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized Object clone() {
        return floor.clone();
    }

    @Override
    public synchronized int compareTo(Floor o) {
        return floor.compareTo(o);
    }

    @Override
    public synchronized Iterator<Space> iterator() {
        return floor.iterator();
    }

    @Override
    public synchronized boolean equals(Object obj) {
        return floor.equals(obj);
    }

    @Override
    public synchronized int hashCode() {
        return floor.hashCode();
    }

    @Override
    public String toString() {
        return floor.toString();
    }
}
