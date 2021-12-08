package netcracker.buildings.net.client;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.DwellingFactory;
import netcracker.buildings.factory.impl.HotelFactory;
import netcracker.buildings.factory.impl.OfficeFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BinaryClient {
    private static final Map<String, Integer> TYPES = Map.of("Dwelling", 1, "OfficeBuilding", 2, "Hotel", 3);
    private static final Map<String, BuildingFactory> FACTORY_MAP = Map.of("Dwelling", new DwellingFactory(), "OfficeBuilding", new OfficeFactory(), "Hotel", new HotelFactory());
    private static Socket clientSocket;
    private static DataInputStream in;
    private static DataOutputStream out;

    public static void main(String[] args) {
        try {
            try {
                System.out.println("Соединение с сервером установлено");
                clientSocket = new Socket("localhost", 5634);
                in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                out = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                List<String> factories = getListOfFactories("src/main/resources/types.txt");
                List<Double> costs = new ArrayList<>();
                try (Reader stream = new FileReader("src/main/resources/buildings.txt")) {
                    out.writeInt(factories.size()); //передаём количество зданий
                    out.flush();
                    int i = 1;
                    for (String factory : factories) {
                        Buildings.setBuildingFactory(FACTORY_MAP.get(factory));
                        Building building = Buildings.readBuilding(stream);
                        out.writeInt(TYPES.get(factory));
                        Buildings.outputBuilding(building, out);
                        out.flush();
                        System.out.println("Передано здание " + building);
                        double cost = in.readDouble();
                        costs.add(cost);
                        if (cost != -1) {
                            System.out.println("Стоимость " + i++ + " здания составляет " + cost + "$");
                        } else {
                            System.out.println("Здание " + i++ + " находится под арестом");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (Writer stream = new FileWriter("src/main/resources/costs.txt")) {
                    for (Double cost : costs) {
                        if (cost != -1) {
                            stream.write(cost + " $\n");
                        } else {
                            stream.write("Здание находится под арестом\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println("Клиент был закрыт");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getListOfFactories(String fileName) {
        BufferedReader reader;
        List<String> factories = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                factories.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factories;
    }
}
