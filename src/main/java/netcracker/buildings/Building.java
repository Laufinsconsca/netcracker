package netcracker.buildings;

import netcracker.buildings.dwelling.Dwelling;
import netcracker.buildings.dwelling.hotel.Hotel;
import netcracker.buildings.office.OfficeBuilding;

import java.io.Serializable;

public sealed interface Building extends Serializable, Cloneable, Iterable<Floor> permits OfficeBuilding, Dwelling, Hotel {
    int getNumberOfFloors();

    int getNumberOfSpaces();

    double getTotalArea();

    int getNumberOfRooms();

    Floor[] getFloorsArray();

    Floor getFloor(int number);

    void alterFloor(int number, Floor floor);

    Space getSpace(int number);

    void alterSpace(int number, Space space);

    void addSpace(int number, Space space);

    void deleteSpace(int number);

    Space getBestSpace();

    Space[] getSortedAreaArray();

    Object clone();
}
