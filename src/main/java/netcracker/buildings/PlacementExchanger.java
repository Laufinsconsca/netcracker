package netcracker.buildings;

import netcracker.exceptions.FloorIndexOutOfBoundsException;
import netcracker.exceptions.InexchangeableFloorsException;
import netcracker.exceptions.InexchangeableSpacesException;
import netcracker.exceptions.SpaceIndexOutOfBoundsException;

public class PlacementExchanger {
    public static boolean areExchangeable(Space first, Space second) {
        return first.getArea() == second.getArea() && first.getNumberOfRooms() == second.getNumberOfRooms();
    }

    public static boolean areExchangeable(Floor first, Floor second) {
        return first.getTotalArea() == second.getTotalArea() && first.getNumberOfRooms() == second.getNumberOfRooms();
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if (index1 < 0 || index2 < 0) {
            throw new SpaceIndexOutOfBoundsException("Отрицательный номер помещения");
        } else if (index1 > floor1.getNumberOfSpaces() || index2 > floor2.getNumberOfSpaces()) {
            throw new SpaceIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего помещения более чем на единицу");
        } else if (!areExchangeable(floor1.getSpace(index1), floor2.getSpace(index2))) {
            throw new InexchangeableSpacesException("Помещения не соответствуют требованиям обмена");
        } else {
            Space tempSpace = floor2.getSpace(index2);
            floor2.alterSpace(index2, floor1.getSpace(index1));
            floor1.alterSpace(index1, tempSpace);
        }
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        if (index1 < 0 || index2 < 0) {
            throw new FloorIndexOutOfBoundsException("Отрицательный номер этажа");
        } else if (index1 > building1.getNumberOfFloors() || index2 > building2.getNumberOfFloors()) {
            throw new FloorIndexOutOfBoundsException("Ожидается номер, не превышающий номер крайнего этажа более чем на единицу");
        } else if (!areExchangeable(building1.getFloor(index1), building2.getFloor(index2))) {
            throw new InexchangeableFloorsException("Этажи не соответствуют требованиям обмена");
        } else {
            Floor tempSpace = building2.getFloor(index2);
            building2.alterFloor(index2, building1.getFloor(index1));
            building1.alterFloor(index1, tempSpace);
        }
    }
}
