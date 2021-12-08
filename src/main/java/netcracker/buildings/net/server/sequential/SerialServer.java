package netcracker.buildings.net.server.sequential;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.DwellingFactory;
import netcracker.buildings.factory.impl.HotelFactory;
import netcracker.buildings.factory.impl.OfficeFactory;
import netcracker.exceptions.BuildingUnderArrestException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    private static final int PORT = 5634;
    private static final int[] COSTS_OF_AREA_UNIT = new int[]{1000, 1500, 2000};
    private static final BuildingFactory[] FACTORIES = new BuildingFactory[]{new DwellingFactory(), new OfficeFactory(), new HotelFactory()};
    private static int number = 1;

    @SuppressWarnings("InfiniteLoopStatement")
    @Deprecated
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = server.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                System.out.println("Клиент " + number + " подключен");
                try {
                    int n = in.readInt();
                    for (int i = 0; i < n; i++) {
                        int type = in.readInt();
                        int costOfAreaUnit = COSTS_OF_AREA_UNIT[type - 1];
                        Buildings.setBuildingFactory(FACTORIES[type - 1]);
                        Building building = Buildings.deserializeBuilding(in);
                        double cost;
                        try {
                            assert building != null;
                            cost = BinaryServer.getCost(building, costOfAreaUnit);
                            System.out.println("Принято здание " + building + ", рассчитанная стоимость - " + cost + "$");
                            out.writeObject(cost);
                        } catch (BuildingUnderArrestException e) {
                            System.out.println("Принято здание " + building + ", здание находится под арестом");
                            out.writeObject(e);
                        }
                        out.flush();
                    }
                    System.out.println("Расчёт для клиента " + number + " окончен");
                } catch (IOException e) {
                    System.out.println("Клиент " + number++ + " завершил работу в связи с ошибкой");
                    socket.close();
                }
            }
        }
    }
}
