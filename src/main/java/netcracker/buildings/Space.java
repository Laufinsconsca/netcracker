package netcracker.buildings;

import netcracker.buildings.dwelling.Flat;
import netcracker.buildings.office.Office;

import java.io.Serializable;

public sealed interface Space extends Serializable, Cloneable, Comparable<Space> permits Flat, Office {
    double getArea();

    void setArea(double area);

    int getNumberOfRooms();

    void setNumberOfRooms(int numberOfRooms);

    Object clone();
}
