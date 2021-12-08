package netcracker.buildings.factory.impl;

import netcracker.buildings.Building;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.office.Office;
import netcracker.buildings.office.OfficeBuilding;
import netcracker.buildings.office.OfficeFloor;

public class OfficeFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Office(area);
    }

    @Override
    public Space createSpace(int roomsCount, double area) {
        return new Office(roomsCount, area);
    }

    @Override
    public Floor createFloor(int spacesCount) {
        return new OfficeFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new OfficeFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCounts) {
        return new OfficeBuilding(floorsCount, spacesCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new OfficeBuilding(floors);
    }
}
