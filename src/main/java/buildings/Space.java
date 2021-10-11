package buildings;

public interface Space {
    double getArea();
    void setArea(double area);
    int getNumberOfRooms();
    void setNumberOfRooms(int numberOfRooms);
    Space copy();
}
