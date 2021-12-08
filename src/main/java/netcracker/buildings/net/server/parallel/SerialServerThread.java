package netcracker.buildings.net.server.parallel;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.DwellingFactory;
import netcracker.buildings.factory.impl.HotelFactory;
import netcracker.buildings.factory.impl.OfficeFactory;
import netcracker.buildings.net.server.sequential.BinaryServer;
import netcracker.exceptions.BuildingUnderArrestException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SerialServerThread extends Thread {
    private static final int[] COSTS_OF_AREA_UNIT = new int[]{1000, 1500, 2000};
    private static final BuildingFactory[] FACTORIES = new BuildingFactory[]{new DwellingFactory(), new OfficeFactory(), new HotelFactory()};
    private final int number;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public SerialServerThread(Socket socket, int number) throws IOException {
        this.number = number;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run() {
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
            e.printStackTrace();
        }
    }
}
