package netcracker.buildings;

import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.HotelFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Scanner;
import java.util.StringJoiner;

public class Buildings {
    public static Comparator<Space> SPACE_COMPARATOR = (o1, o2) -> o2.getNumberOfRooms() - o1.getNumberOfRooms();
    public static Comparator<Floor> FLOOR_COMPARATOR = (o1, o2) -> (int) (o2.getTotalArea() - o1.getTotalArea());
    private static BuildingFactory buildingFactory = new HotelFactory();

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace(double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(Class<? extends Space> spaceClass, double area) {
        Space space;
        try {
            space = spaceClass.getConstructor(double.class).newInstance(area);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return space;
    }

    public static Space createSpace(int roomsCount, double area) {
        return buildingFactory.createSpace(roomsCount, area);
    }

    public static Space createSpace(Class<? extends Space> spaceClass, int roomsCount, double area) {
        Space space;
        try {
            space = spaceClass.getConstructor(int.class, double.class).newInstance(roomsCount, area);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return space;
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(Class<? extends Floor> floorClass, int spacesCount) {
        Floor floor;
        try {
            floor = floorClass.getConstructor(int.class).newInstance(spacesCount);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return floor;
    }

    public static Floor createFloor(Space... spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Floor createFloor(Class<? extends Floor> floorClass, Space... spaces) {
        Floor floor;
        try {
            floor = floorClass.getConstructor(Space[].class).newInstance((Object) spaces);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return floor;
    }

    public static Building createBuilding(int floorsCount, int... spacesCounts) {
        return buildingFactory.createBuilding(floorsCount, spacesCounts);
    }

    public static Building createBuilding(Class<? extends Building> buildingClass, int floorsCount, int... spacesCounts) {
        Building building;
        try {
            building = buildingClass.getConstructor(int.class, int[].class).newInstance(floorsCount, spacesCounts);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return building;
    }

    public static Building createBuilding(Floor... floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static Building createBuilding(Class<? extends Building> buildingClass, Floor... floors) {
        Building building;
        try {
            building = buildingClass.getConstructor(Floor[].class).newInstance((Object) floors);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return building;
    }

    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeInt(building.getNumberOfFloors());
        for (Floor floor : building.getFloorsArray()) {
            dataOutputStream.writeInt(floor.getNumberOfSpaces());
            for (Space space : floor.getSpacesArray()) {
                dataOutputStream.writeInt(space.getNumberOfRooms());
                dataOutputStream.writeDouble(space.getArea());
            }
        }
        dataOutputStream.flush();
    }

    public static Building inputBuilding(InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int numberOfFloors = dataInputStream.readInt();
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            int numberOfSpaces = dataInputStream.readInt();
            Space[] spaces = new Space[numberOfSpaces];
            for (int j = 0; j < numberOfSpaces; j++) {
                int numberOfRooms = dataInputStream.readInt();
                double area = dataInputStream.readDouble();
                spaces[j] = createSpace(numberOfRooms, area);
            }
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    public static Building inputBuilding(InputStream in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int numberOfFloors = dataInputStream.readInt();
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            int numberOfSpaces = dataInputStream.readInt();
            Space[] spaces = new Space[numberOfSpaces];
            for (int j = 0; j < numberOfSpaces; j++) {
                int numberOfRooms = dataInputStream.readInt();
                double area = dataInputStream.readDouble();
                spaces[j] = createSpace(spaceClass, numberOfRooms, area);
            }
            floors[i] = createFloor(floorClass, spaces);
        }
        return createBuilding(buildingClass, floors);
    }

    public static void writeBuilding(Building building, Writer out) throws IOException {
        StringJoiner joiner = new StringJoiner(" ", "", "\n");
        joiner.add(String.valueOf(building.getNumberOfFloors()));
        for (Floor floor : building.getFloorsArray()) {
            joiner.add(String.valueOf(floor.getNumberOfSpaces()));
            for (Space space : floor.getSpacesArray()) {
                joiner.add(String.valueOf(space.getNumberOfRooms()));
                joiner.add(String.valueOf(space.getArea()));
            }
        }
        out.write(joiner.toString());
        out.flush();
    }

    public static Building readBuilding(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int numberOfFloors = (int) tokenizer.nval;
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            tokenizer.nextToken();
            int numberOfSpaces = (int) tokenizer.nval;
            Space[] spaces = new Space[numberOfSpaces];
            for (int j = 0; j < numberOfSpaces; j++) {
                tokenizer.nextToken();
                int numberOfRooms = (int) tokenizer.nval;
                tokenizer.nextToken();
                double area = tokenizer.nval;
                spaces[j] = createSpace(numberOfRooms, area);
            }
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    public static Building readBuilding(Reader in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int numberOfFloors = (int) tokenizer.nval;
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            tokenizer.nextToken();
            int numberOfSpaces = (int) tokenizer.nval;
            Space[] spaces = new Space[numberOfSpaces];
            for (int j = 0; j < numberOfSpaces; j++) {
                tokenizer.nextToken();
                int numberOfRooms = (int) tokenizer.nval;
                tokenizer.nextToken();
                double area = tokenizer.nval;
                spaces[j] = createSpace(spaceClass, numberOfRooms, area);
            }
            floors[i] = createFloor(floorClass, spaces);
        }
        return createBuilding(buildingClass, floors);
    }

    public static void serializeBuilding(Building building, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(building);
        oos.flush();
    }

    public static Building deserializeBuilding(InputStream in) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(in);
        try {
            Object object = ois.readObject();
            return (Building) object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeBuildingFormat(Building building, Writer out) {
        Formatter formatter = new Formatter(out);
        formatter.format("%d ", building.getNumberOfFloors());
        for (Floor floor : building.getFloorsArray()) {
            formatter.format("%d ", floor.getNumberOfSpaces());
            for (Space space : floor.getSpacesArray()) {
                formatter.format("%d ", space.getNumberOfRooms());
                formatter.format("%g ", space.getArea());
            }
        }
    }

    public static Building readBuildingFormat(Reader in) {
        Scanner scanner = new Scanner(in);
        int numberOfFloors = scanner.nextInt();
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            int numberOfSpaces = scanner.nextInt();
            Space[] spaces = new Space[numberOfSpaces];
            for (int j = 0; j < numberOfSpaces; j++) {
                int numberOfRooms = scanner.nextInt();
                double area = scanner.nextDouble();
                spaces[j] = createSpace(numberOfRooms, area);
            }
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    public static Building readBuildingFormat(Reader in, Class<? extends Building> buildingClass, Class<? extends Floor> floorClass, Class<? extends Space> spaceClass) {
        Scanner scanner = new Scanner(in);
        int numberOfFloors = scanner.nextInt();
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            int numberOfSpaces = scanner.nextInt();
            Space[] spaces = new Space[numberOfSpaces];
            for (int j = 0; j < numberOfSpaces; j++) {
                int numberOfRooms = scanner.nextInt();
                double area = scanner.nextDouble();
                spaces[j] = createSpace(spaceClass, numberOfRooms, area);
            }
            floors[i] = createFloor(floorClass, spaces);
        }
        return createBuilding(buildingClass, floors);
    }

    public static <T extends Comparable<T>> void sort(T[] objects) {
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (objects[j].compareTo(objects[minIndex]) < 0)
                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }

    public static <T> void sort(T[] objects, Comparator<T> comparator) {
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (comparator.compare(objects[j], objects[minIndex]) < 0)
                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }

    public Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }
}
