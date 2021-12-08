package netcracker.buildings.factory.impl;

import netcracker.buildings.Building;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.Flat;
import netcracker.buildings.dwelling.hotel.Hotel;
import netcracker.buildings.dwelling.hotel.HotelFloor;
import netcracker.buildings.factory.BuildingFactory;

public class HotelFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Flat(area);
    }

    @Override
    public Space createSpace(int roomsCount, double area) {
        return new Flat(roomsCount, area);
    }

    @Override
    public Floor createFloor(int spacesCount) {
        return new HotelFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new HotelFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCounts) {
        return new Hotel(floorsCount, spacesCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Hotel(floors);
    }
}
