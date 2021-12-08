package netcracker.buildings.dwelling;

import netcracker.buildings.Space;
import netcracker.exceptions.InvalidRoomsCountException;
import netcracker.exceptions.InvalidSpaceAreaException;

import java.util.Objects;

public final class Flat implements Space {
    private final static double DEFAULT_SQUARE = 50;
    private final static int DEFAULT_NUMBER_OF_ROOMS = 2;

    private double area;
    private int numberOfRooms;

    public Flat(int numberOfRooms, double area) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException("Некорректная площадь");
        }
        if (numberOfRooms <= 0) {
            throw new InvalidRoomsCountException("Некорректное количество комнат");
        }
        this.area = area;
        this.numberOfRooms = numberOfRooms;
    }

    public Flat(double area) {
        this(2, area);
    }

    public Flat() {
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
        return Objects.nonNull(obj) && obj instanceof Flat f && f.numberOfRooms == numberOfRooms && f.area == area;
    }

    @Override
    public int hashCode() {
        return (int) (numberOfRooms ^ Double.doubleToLongBits(area) >>> 32);
    }

    @Override
    public Object clone() {
        return new Flat(numberOfRooms, area);
    }

    @Override
    public String toString() {
        return "Flat {" + numberOfRooms + ", " + area + "}";
    }

    @Override
    public int compareTo(Space o) {
        return (int) (area - o.getArea());
    }
}
