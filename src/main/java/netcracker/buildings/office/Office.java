package netcracker.buildings.office;

import netcracker.buildings.Space;
import netcracker.exceptions.InvalidRoomsCountException;
import netcracker.exceptions.InvalidSpaceAreaException;

import java.util.Objects;

public final class Office implements Space {
    private final static double DEFAULT_SQUARE = 250;
    private final static int DEFAULT_NUMBER_OF_ROOMS = 1;

    private double area;
    private int numberOfRooms;

    public Office(int numberOfRooms, double area) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException("Некорректная площадь");
        }
        if (numberOfRooms <= 0) {
            throw new InvalidRoomsCountException("Некорректное количество комнат");
        }
        this.area = area;
        this.numberOfRooms = numberOfRooms;
    }

    public Office(double area) {
        this(1, area);
    }

    public Office() {
        this(DEFAULT_NUMBER_OF_ROOMS, DEFAULT_SQUARE);
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    @Override
    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return Objects.nonNull(obj) && obj instanceof Office o && o.numberOfRooms == numberOfRooms && o.area == area;
    }

    @Override
    public int hashCode() {
        return (int) (numberOfRooms ^ Double.doubleToLongBits(area) >>> 32);
    }

    @Override
    public Object clone() {
        return new Office(numberOfRooms, area);
    }

    @Override
    public String toString() {
        return "Office {" + numberOfRooms + ", " + area + "}";
    }

    @Override
    public int compareTo(Space o) {
        return (int) (area - o.getArea());
    }
}
