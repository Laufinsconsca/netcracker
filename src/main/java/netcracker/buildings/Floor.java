package netcracker.buildings;

import netcracker.buildings.dwelling.DwellingFloor;
import netcracker.buildings.dwelling.hotel.HotelFloor;
import netcracker.buildings.office.OfficeFloor;

import java.io.Serializable;

public sealed interface Floor extends Serializable, Cloneable, Iterable<Space>, Comparable<Floor> permits SynchronizedFloor, DwellingFloor, HotelFloor, OfficeFloor {
    int getNumberOfSpaces();

    double getTotalArea();

    int getNumberOfRooms();

    Space[] getSpacesArray();

    Space getSpace(int number);

    void alterSpace(int number, Space space);

    void addSpace(int number, Space space);

    void deleteSpace(int number);

    Space getBestSpace();

    Object clone();
}
