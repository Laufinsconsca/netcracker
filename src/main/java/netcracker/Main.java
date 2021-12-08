package netcracker;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.Floor;
import netcracker.buildings.Space;
import netcracker.buildings.dwelling.Dwelling;
import netcracker.buildings.dwelling.DwellingFloor;
import netcracker.buildings.dwelling.Flat;
import netcracker.buildings.dwelling.hotel.Hotel;
import netcracker.buildings.dwelling.hotel.HotelFloor;
import netcracker.buildings.office.Office;
import netcracker.buildings.office.OfficeBuilding;
import netcracker.buildings.office.OfficeFloor;
import netcracker.buildings.threads.*;

import java.io.*;
import java.util.Arrays;

public class Main {
    private static final Floor officeFloor1;
    private static final Floor officeFloor2;
    private static final Floor dwellingFloor;
    private static final Floor dwellingFloor2;
    private static final Building hotel;
    private static final Building dwelling;
    private static final Building office;

    static {
        Space office11 = new Office(10);
        Space office21 = new Office(2, 10);
        Space office31 = new Office(3, 10);
        officeFloor1 = new OfficeFloor(office11, office21, office31);

        Space office12 = new Office(3, 20);
        Space office22 = new Office(20);
        Space office32 = new Office(4, 20);
        officeFloor2 = new OfficeFloor(office12, office22, office32);

        Space flat1 = new Flat(3, 30);
        Space flat2 = new Flat(40);
        Space office33 = new Office(2, 30);
        dwellingFloor = new HotelFloor(flat1, flat2, office33);
        ((HotelFloor) dwellingFloor).setNumberOfStars(5);

        Space flat3 = new Flat(3, 20);
        Space flat4 = new Flat(20);
        Space flat5 = new Flat(2, 20);
        dwellingFloor2 = new HotelFloor(flat3, flat4, flat5);
        ((HotelFloor) dwellingFloor2).setNumberOfStars(2);
        hotel = new Hotel(officeFloor1, dwellingFloor, dwellingFloor2);
        office = new OfficeBuilding(officeFloor1, officeFloor2, dwellingFloor2);
        dwelling = new Dwelling(dwellingFloor, officeFloor1, dwellingFloor2);
    }

    public static void main(String[] args) {
        testThirdPractice();
        testFourthPractice();
        testFifthPractice();
        testSixthPractice();
        testSeventhPractice();
        testTenthPractice();
        testEleventhPractice();
    }

    private static void testThirdPractice() {
        System.out.println("Тест третьей практики");
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
        System.out.println("Проверка OfficeBuilding");
        System.out.println("Исходное здание:\n" + hotel);
        hotel.alterFloor(0, officeFloor2);
        System.out.println("Скопируем 1 этаж на место нулевого:\n" + hotel);
        System.out.println("Удалим 5 офис, добавим Office(240,2) на 3 позицию, изменим 0 офис на Office(90,4)");
        hotel.deleteSpace(5);
        hotel.addSpace(3, new Office(2, 240));
        hotel.alterSpace(0, new Office(4, 90));
        System.out.println("Преобразованное здание:\n" + hotel);
        System.out.println("Общая площадь этажа: " + hotel.getTotalArea());
        System.out.println("Общее количество комнат: " + hotel.getNumberOfRooms());
        System.out.println("Офис с наибольшей площадью: " + hotel.getBestSpace());
        System.out.println("Массив офисов по убыванию их площадей: " + Arrays.toString(hotel.getSortedAreaArray()));
        System.out.println();
    }

    public static void testFourthPractice() {
        System.out.println("Тест четвёртой практики");
        //проверка записи в байтовый поток
        System.out.println("Запись в байтовый поток...");
        try (OutputStream stream = new FileOutputStream("src/main/resources/binary_test.bin")) {
            Buildings.outputBuilding(hotel, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Тестовый экземпляр класса Building записан в src/main/resources/binary_test.bin");
        //проверка чтения из байтового потока
        System.out.println("Проверка чтения из байтового потока src/main/resources/binary_test.bin");
        try (InputStream stream = new FileInputStream("src/main/resources/binary_test.bin")) {
            Building building = Buildings.inputBuilding(stream);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка записи в символьный поток
        System.out.println("Запись в символьный поток...");
        try (Writer stream = new FileWriter("src/main/resources/character_test.txt")) {
            Buildings.writeBuilding(hotel, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Тестовый экземпляр класса Building записан в src/main/resources/character_test.txt");
        //проверка чтения из символьного потока
        System.out.println("Проверка чтения из символьного потока src/main/resources/character_test.txt");
        try (Reader stream = new FileReader("src/main/resources/character_test.txt")) {
            Building building = Buildings.readBuilding(stream);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка записи в консоль
        System.out.println("Проверка записи в консоль");
        try {
            Buildings.writeBuilding(hotel, new OutputStreamWriter(System.out));
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка чтения из консоли
        System.out.println("Проверка чтения из консоли");
        try {
            Building building = Buildings.readBuilding(new InputStreamReader(System.in));
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка сериализации
        System.out.println("Сериализация тестового класса Building в src/main/resources/serialization_test.bin...");
        try (OutputStream stream = new FileOutputStream("src/main/resources/serialization_test.bin")) {
            Buildings.serializeBuilding(hotel, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка десериализации
        System.out.println("Проверка десериализации тестового класса Building из serialization_test.bin");
        try (InputStream stream = new FileInputStream("src/main/resources/serialization_test.bin")) {
            Building building = Buildings.deserializeBuilding(stream);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка форматированного вывода
        System.out.println("Идёт форматированный вывод...");
        try (Writer stream = new FileWriter("src/main/resources/format_test.txt")) {
            Buildings.writeBuildingFormat(hotel, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Тестовый экземпляр класса Building записан в src/main/resources/format_test.txt");
        //проверка форматированного ввода
        System.out.println("Проверка форматированного ввода src/main/resources/format_test.txt");
        try (Reader stream = new FileReader("src/main/resources/format_test.txt")) {
            Building building = Buildings.readBuildingFormat(stream);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testFifthPractice() {
        System.out.println(hotel.hashCode());
        System.out.println("Клонируем экземпляр OfficeBuilding:");
        Building officeBuilding1 = (Building) hotel.clone();
        System.out.println(officeBuilding1);
        System.out.println(officeBuilding1.equals(hotel));
        hotel.alterSpace(0, new Office(130));
        System.out.println(officeBuilding1);
        System.out.println(hotel);
        System.out.println(officeBuilding1.equals(hotel));
    }

    private static void testSixthPractice() {
        //проверка comparable и comparator
        Buildings.sort(dwellingFloor2.getSpacesArray());
        System.out.println(dwellingFloor2);
        Buildings.sort(dwellingFloor2.getSpacesArray(), Buildings.SPACE_COMPARATOR);
        System.out.println(dwellingFloor2);
        Buildings.sort(hotel.getFloorsArray());
        System.out.println(hotel);
        Buildings.sort(hotel.getFloorsArray(), Buildings.FLOOR_COMPARATOR);
        System.out.println(hotel);
    }

    private static void testSeventhPractice() {
        // <the first task>
        Repairer repairer = new Repairer(dwellingFloor);
        Cleaner cleaner = new Cleaner(dwellingFloor);
        repairer.setPriority(10);
        cleaner.setPriority(1);
        repairer.start();
        cleaner.start();
        repairer.interrupt();
        // </the first task>
        // <the second task>
        RepairCleanSemaphore repairCleanSemaphore = new RepairCleanSemaphore();
        SequentialRepairer sequentialRepairer = new SequentialRepairer(dwellingFloor, repairCleanSemaphore);
        SequentialCleaner sequentialCleaner = new SequentialCleaner(dwellingFloor, repairCleanSemaphore);
        Thread repairThread = new Thread(sequentialRepairer);
        Thread cleanThread = new Thread(sequentialCleaner);
        repairThread.start();
        cleanThread.start();
        // </the second task>
    }

    private static void testTenthPractice() {
        // <first task>
        Space space1 = Buildings.createSpace(Flat.class, 10);
        Space space2 = Buildings.createSpace(Office.class, 2, 50);
        if (space1 instanceof Flat) {
            System.out.println(space1);
        }
        if (space2 instanceof Office) {
            System.out.println(space2);
        }
        Floor floor1 = Buildings.createFloor(HotelFloor.class, 5);
        Floor floor2 = Buildings.createFloor(OfficeFloor.class, space1, space2);
        if (floor1 instanceof HotelFloor) {
            System.out.println(floor1);
        }
        if (floor2 instanceof OfficeFloor) {
            System.out.println(floor2);
        }
        Building building1 = Buildings.createBuilding(Dwelling.class, 5, 1, 2, 3, 4, 5);
        Building building2 = Buildings.createBuilding(Hotel.class, floor1, floor2);
        if (building1 instanceof Dwelling) {
            System.out.println(building1);
        }
        if (building2 instanceof Hotel) {
            System.out.println(building2);
        }
        // </first task>
        // <second task>
        System.out.println("Тест четвёртой практики");
        //проверка записи в байтовый поток
        System.out.println("Запись в байтовый поток...");
        try (OutputStream stream = new FileOutputStream("src/main/resources/binary_test.bin")) {
            Buildings.outputBuilding(hotel, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Тестовый экземпляр класса Building записан в src/main/resources/binary_test.bin");
        //проверка чтения из байтового потока
        System.out.println("Проверка чтения из байтового потока src/main/resources/binary_test.bin");
        try (InputStream stream = new FileInputStream("src/main/resources/binary_test.bin")) {
            Building building = Buildings.inputBuilding(stream, Dwelling.class, DwellingFloor.class, Flat.class);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка чтения из символьного потока
        System.out.println("Проверка чтения из символьного потока src/main/resources/character_test.txt");
        try (Reader stream = new FileReader("src/main/resources/character_test.txt")) {
            Building building = Buildings.readBuilding(stream, OfficeBuilding.class, OfficeFloor.class, Office.class);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //проверка форматированного вывода
        System.out.println("Идёт форматированный вывод...");
        try (Writer stream = new FileWriter("src/main/resources/format_test.txt")) {
            Buildings.writeBuildingFormat(hotel, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Тестовый экземпляр класса Building записан в src/main/resources/format_test.txt");
        //проверка форматированного ввода
        System.out.println("Проверка форматированного ввода src/main/resources/format_test.txt");
        try (Reader stream = new FileReader("src/main/resources/format_test.txt")) {
            Building building = Buildings.readBuildingFormat(stream, Hotel.class, HotelFloor.class, Flat.class);
            System.out.println(building);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // </second task>
    }

    private static void testEleventhPractice() {
        testSixthPractice();
    }
}
