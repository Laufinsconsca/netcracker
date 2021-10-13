import buildings.Building;
import buildings.Floor;
import buildings.Space;
import buildings.impl.array.DwellingFloor;
import buildings.impl.array.Flat;
import buildings.impl.linked_list.Office;
import buildings.impl.linked_list.OfficeBuilding;
import buildings.impl.linked_list.OfficeFloor;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Space office11 = new Office(270);
        Space office21 = new Office(100, 2);
        Space office31 = new Office(150, 3);
        Floor officeFloor1 = new OfficeFloor(office11, office21, office31);

        Space office12 = new Office(300, 3);
        Space office22 = new Office(80);
        Space office32 = new Office(170, 4);
        Floor officeFloor2 = new OfficeFloor(office12, office22, office32);

        Space flat1 = new Flat(120, 3);
        Space flat2 = new Flat(90);
        Space office33 = new Office(60, 2);
        Floor dwellingFloor = new DwellingFloor(flat1, flat2, office33);

        //проверка OfficeFloor
        System.out.println("Проверка OfficeFloor");
        System.out.println("Исходный этаж: " + officeFloor1);
        System.out.println("Удалим 0 офис, добавим Office(140) на 1 позицию, изменим 0 офис на Office(130)");
        officeFloor1.deleteSpace(0);
        officeFloor1.addSpace(1, new Office(140));
        officeFloor1.alterSpace(0, new Office(130));
        System.out.println("Преобразованный этаж: " + officeFloor1);
        System.out.println("Общая площадь этажа: " + officeFloor1.getTotalArea());
        System.out.println("Общее количество комнат на этаже: " + officeFloor1.getNumberOfRooms());
        System.out.println("Офис с наибольшей площадью на этаже: " + officeFloor1.getBestSpace());
        System.out.println();
        //проверка OfficeFloor
        //проверка OfficeBuilding
        Building officeBuilding = new OfficeBuilding(officeFloor1, officeFloor2, dwellingFloor);
        System.out.println("Проверка OfficeBuilding");
        System.out.println("Исходное здание:\n" + officeBuilding);
        officeBuilding.alterFloor(0, officeFloor2);
        System.out.println("Скопируем 1 этаж на место нулевого:\n" + officeBuilding);
        System.out.println("Удалим 5 офис, добавим Office(240,2) на 3 позицию, изменим 0 офис на Office(90,4)");
        officeBuilding.deleteSpace(5);
        officeBuilding.addSpace(3, new Office(240, 2));
        officeBuilding.alterSpace(0, new Office(90, 4));
        System.out.println("Преобразованное здание:\n" + officeBuilding);
        System.out.println("Общая площадь этажа: " + officeBuilding.getTotalArea());
        System.out.println("Общее количество комнат: " + officeBuilding.getNumberOfRooms());
        System.out.println("Офис с наибольшей площадью: " + officeBuilding.getBestSpace());
        System.out.println("Массив офисов по убыванию их площадей: " + Arrays.toString(officeBuilding.getSortedAreaArray()));
    }
}
