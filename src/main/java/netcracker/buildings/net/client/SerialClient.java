package netcracker.buildings.net.client;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.DwellingFactory;
import netcracker.buildings.factory.impl.HotelFactory;
import netcracker.buildings.factory.impl.OfficeFactory;
import netcracker.exceptions.BuildingUnderArrestException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerialClient {
    private static final Map<String, Integer> TYPES = Map.of("Dwelling", 1, "OfficeBuilding", 2, "Hotel", 3);
    private static final Map<String, BuildingFactory> FACTORY_MAP = Map.of("Dwelling", new DwellingFactory(), "OfficeBuilding", new OfficeFactory(), "Hotel", new HotelFactory());
    private static Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {
        try {
            try {
                System.out.println("Соединение с сервером установлено");
                clientSocket = new Socket("localhost", 5634);
                in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                List<String> factories = BinaryClient.getListOfFactories("src/main/resources/types.txt");
                List<Double> costs = new ArrayList<>();
                try (Reader stream = new FileReader("src/main/resources/buildings.txt")) {
                    out.writeInt(factories.size()); //передаём количество зданий
                    out.flush();
                    int i = 1;
                    for (String factory : factories) {
                        Buildings.setBuildingFactory(FACTORY_MAP.get(factory));
                        Building building = Buildings.readBuilding(stream);
                        out.writeInt(TYPES.get(factory));  //передали фабрику
                        Buildings.serializeBuilding(building, out);  //передали здание
                        out.flush();
                        System.out.println("Передано здание " + building);
                        Object obj = in.readObject();
                        if (obj instanceof BuildingUnderArrestException) {
                            costs.add(-1.);
                            System.out.println("Здание " + i++ + " находится под арестом");
                        } else if (obj instanceof Double cost) {
                            costs.add(cost);
                            System.out.println("Стоимость " + i++ + " здания составляет " + cost + "$");
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
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
}
