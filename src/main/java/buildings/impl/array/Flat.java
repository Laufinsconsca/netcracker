package buildings.impl.array;

import buildings.Space;
import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;

public class Flat implements Space {
    private final static double DEFAULT_SQUARE = 50;
    private final static int DEFAULT_NUMBER_OF_ROOMS = 2;

    private double area;
    private int numberOfRooms;

    public Flat(double area, int numberOfRooms) {
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
        this(area, 2);
    }

    public Flat() {
        this(DEFAULT_SQUARE, DEFAULT_NUMBER_OF_ROOMS);
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
    public Space copy() {
        return new Flat(area, numberOfRooms);
    }

    public String toString() {
        return "{" + area + "," + numberOfRooms + "}";
    }
}
