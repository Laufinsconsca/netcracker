package netcracker.buildings.factory;

import netcracker.buildings.Building;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;

import java.io.Serializable;

public interface BuildingFactory extends Serializable {
    Space createSpace(double area);

    Space createSpace(int roomsCount, double area);

    Floor createFloor(int spacesCount);

    Floor createFloor(Space[] spaces);

    Building createBuilding(int floorsCount, int[] spacesCounts);

    Building createBuilding(Floor[] floors);
}
