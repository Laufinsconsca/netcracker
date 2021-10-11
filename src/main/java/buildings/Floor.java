package buildings;

public interface Floor {
    int getNumberOfSpaces();
    double getTotalArea();
    int getNumberOfRooms();
    Space[] toArray();
    Space getSpace(int number);
    void alterSpace(int number, Space space);
    void addSpace(int number, Space space);
    void deleteSpace(int number);
    Space getBestSpace();
    Floor copy();
}
