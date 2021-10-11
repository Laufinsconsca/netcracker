package buildings.impl.linked_list;

import buildings.Space;
import exceptions.InvalidRoomsCountException;
import exceptions.InvalidSpaceAreaException;

public class Office implements Space {
    private final static double DEFAULT_SQUARE = 250;
    private final static int DEFAULT_NUMBER_OF_ROOMS = 1;

    private double area;
    private int numberOfRooms;

    public Office(double area, int numberOfRooms) {
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
        this(area, 1);
    }

    public Office (){
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
        return new Office(area, numberOfRooms);
    }

    public String toString(){
        return "{" + area + "," + numberOfRooms + "}";
    }
}
