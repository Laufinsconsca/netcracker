package netcracker.buildings.factory.impl;

import netcracker.buildings.Building;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.Dwelling;
import netcracker.buildings.dwelling.DwellingFloor;
import netcracker.buildings.dwelling.Flat;
import netcracker.buildings.factory.BuildingFactory;

public class DwellingFactory implements BuildingFactory {
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
        return new DwellingFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new DwellingFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCounts) {
        return new Dwelling(floorsCount, spacesCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Dwelling(floors);
    }
}
