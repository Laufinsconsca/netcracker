package buildings;

public interface Building {
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
}
